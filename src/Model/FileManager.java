package Model;

import java.io.*;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.function.Consumer;

public class FileManager implements Iterable<Product> {

    public static final String FILE_NAME = "products.txt";
    private final File file;
    public boolean fileExists;
    private RandomAccessFile raf;

    public FileManager() {
        file = new File(FILE_NAME);
        fileExists = file.exists();
        resetRAF();
    }

    private void resetRAF() {
        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (IOException e) {/**/}
    }

    public void add(Product product) {
        try {
            raf.seek(raf.length());
            byte[] data = serialize(product);
            raf.writeInt(data.length);
            raf.write(data);
        } catch (IOException e) {
            //
        }
    }

    public void remove(String serialNum) {
        Iterator<Product> iterator = iterator();
        long lastPosition;
        while (iterator.hasNext()) try {
            lastPosition = raf.getFilePointer();
            if (iterator.next().serialNum.equals(serialNum)) {

                byte[] temp = new byte[(int) (raf.length() - raf.getFilePointer())];
                raf.read(temp);
                raf.setLength(lastPosition);
                raf.write(temp);
            }
        } catch (IOException e) {
            /**/
        }
    }

    public TreeMap<String, Product> getMapFromFile() {
        TreeMap<String, Product> map = new TreeMap<>();
        forEach(product -> map.put(product.serialNum, product));
        return map;
    }

    public void clear() {

    }

    private byte[] serialize(Object object) throws IOException {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos)
        ) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream in = new ObjectInputStream(bis)
        ) {
            return in.readObject();
        }
    }

    private byte[] readByteArray() throws IOException {
        int size = raf.readInt();
        byte[] data = new byte[size];
        raf.read(data);
        return data;
    }

    @Override
    public Iterator<Product> iterator() {
        return new FileIterator();
    }

    @Override
    public void forEach(Consumer<? super Product> action) {
        for (Product p : this) {
            action.accept(p);
        }
    }

    class FileIterator implements Iterator<Product> {

        FileIterator() {
            resetRAF();
        }

        @Override
        public boolean hasNext() {
            try {
                return raf.getFilePointer() < raf.length();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public Product next() {
            try {
                byte[] data = readByteArray();
                return (Product) deserialize(data);
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        }
    }
}
