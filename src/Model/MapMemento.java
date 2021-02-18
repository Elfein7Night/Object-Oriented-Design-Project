package Model;

import java.util.Map;
import java.util.TreeMap;

public class MapMemento {

    private TreeMap<String, Product> mapMemento;

    public MapMemento(TreeMap<String, Product> map) {
        setMemento(map);
    }

    public TreeMap<String, Product> getMemento() {
        return mapMemento;
    }

    public void setMemento(TreeMap<String, Product> map) {
        mapMemento = new TreeMap<>(map);
    }
}