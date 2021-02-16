package Model;

import java.io.*;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.function.Consumer;

public class FileManager {

    private File file;
    public boolean isAppendable;
    private RandomAccessFile raf;

    public FileManager() {
        file = new File("products.txt");
        isAppendable = file.exists();
        resetRAF();
    }

    private void resetRAF() {
        try {
            raf = new RandomAccessFile(file, "rw");
        } catch (IOException e) {/**/}
    }

    public void add(Product product) {

    }

    public void remove(String serialNum) {

    }

    public TreeMap<String, Product> getMapFromFile() {

        return null;
    }

    public void clear() {

    }

    class FileContents implements Iterable<Pair<String, Product>> {

        @Override
        public Iterator<Pair<String, Product>> iterator() {
            return new FileIterator();
        }

        @Override
        public void forEach(Consumer<? super Pair<String, Product>> action) {
            for (Pair<String, Product> p : this) {
                action.accept(p);
            }
        }
    }

    class FileIterator implements Iterator<Pair<String, Product>> {

        FileIterator() {
            resetRAF();
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Pair<String, Product> next() {
            return null;
        }
    }
}
