package Model.Iterator;

import Model.Product;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class FileManager implements Iterable<Product> {

    public static final String FILE_NAME = "products.txt";
    private final File file;
    private RandomAccessFile raf;

    public FileManager() {
        file = new File(FILE_NAME);
        resetRAF();
    }

    private void resetRAF() {
        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void resetPointer() {
        try {
            raf.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(Product product) {
        /*
            first, check that the product serial num is not in the file:
            we can remove and append the new one at the end since order in the file
            does not matter, all that matters in terms of order is the comparator
            of the outside map.
         */
        if (get(product.getSerialNum()) != null) {
            remove(product.getSerialNum());
        }
        try {
            raf.seek(raf.length());
            byte[] data = serialize(product);
            raf.writeInt(data.length);
            raf.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Product get(String serialNum) {
        for (Product p : this) {
            if (p.getSerialNum().equals(serialNum))
                return p;
        }
        return null;
    }

    /*
        Pretty much same way we did in the lecture/practice:
            [obj]-[obj]-[obj]-[DELETE]-[obj]-[obj]-[obj]
            |--do not touch-|          |-save to temp--|
            set length just before the desired delete obj then add the temp at the end
     */
    public boolean remove(String serialNum) {
        Iterator<Product> iterator = iterator();
        long lastPosition;
        while (iterator.hasNext()) try {
            lastPosition = raf.getFilePointer();
            if (iterator.next().getSerialNum().equals(serialNum)) {
                byte[] temp = new byte[(int) (raf.length() - raf.getFilePointer())];
                raf.read(temp);
                raf.setLength(lastPosition);
                raf.write(temp);
                resetPointer(); // after remove reset cursor since old length is irrelevant
                return true; // small optimization since we know serialNum only appears once
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void loadMapFromFile(Map<String, Product> map) {
        forEach(product -> map.put(product.getSerialNum(), product));
    }

    public void clear() {
        for (Product p : this) {
            remove(p.getSerialNum());
        }
    }

    // an easy and straightforward way to convert a serializable object into byte array
    private byte[] serialize(Object object) throws IOException {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos)
        ) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    // an easy and straightforward way to convert a byte array back into its original serializable object
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

    public boolean isEmpty() {
        try {
            return !file.exists() || raf.length() == 0;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return false;   // for compilation
        }
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
                e.printStackTrace();
                System.exit(1);
                return false;   // for compilation
            }
        }

        @Override
        public Product next() {
            try {
                byte[] data = readByteArray();
                return (Product) deserialize(data);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
                return null;    // for compilation
            }
        }
    }
}
