package Model.Memento;

import Model.Product;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapMemento {

    private Map<String, Product> mapMemento;

    public MapMemento(Map<String, Product> map) {
        setMemento(map);
    }

    public Map<String, Product> getMemento() {
        return mapMemento;
    }

    public void setMemento(Map<String, Product> map) {
        if (map instanceof TreeMap) {
            mapMemento = new TreeMap<>((TreeMap<String, Product>) map);
        } else {
            mapMemento = new LinkedHashMap<>(map);
        }
    }
}