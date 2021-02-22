package Model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class FileManager implements Iterable<Product> {

    public static final String FILE_NAME = "products.txt";
    private final File file;
    public boolean fileExists;
    private RandomAccessFile raf;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //TODO

    String getTime() { //TODO
        return LocalDateTime.now().format(formatter);
    }

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
            System.out.println("object length: " + data.length);
            raf.write(data);
        } catch (IOException e) {
            //
        }
    }

    public Product get(String serialNum) {
        for (Product p : this) {
            if (p.getSerialNum().equals(serialNum))
                return p;
        }
        return null;
    }

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
                return true; // small optimization since we know serialNum only appears once
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public void loadMapFromFile(Map<String, Product> map) {
        System.out.println("~~~loadMapFromFile~~~");
        forEach(product -> {
            System.out.println(product); // TODO: for debugging, REMOVE LATER!
            map.put(product.getSerialNum(), product);
        });
    }

    /*
        O(n) - always access first in the file and delete it.
     */
    public void clear() {
        System.out.println("~~~clear~~~");
        for (Product p : this) {
            System.out.println(p);
            remove(p.getSerialNum());
            resetPointer(); // after remove reset cursor since old length is irrelevant
        }
    }

    private void resetPointer() {
        try {
            raf.seek(0);
        } catch (IOException e) {
            /**/
        }
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
                System.out.println(getTime() + " hasNext: " + (raf.getFilePointer() < raf.length()) +
                        " | getFilePointer: " + raf.getFilePointer() + " | length: " + raf.length());
                return raf.getFilePointer() < raf.length();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public Product next() {
            try {
                System.out.println("raf.getFilePointer() before read: " + raf.getFilePointer());
                byte[] data = readByteArray();
                System.out.println("raf.getFilePointer() after read: " + raf.getFilePointer());
                return (Product) deserialize(data);
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        }
    }
}
