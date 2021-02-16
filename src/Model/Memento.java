package Model;

import java.util.Map;
import java.util.TreeMap;

public class Memento {

    private Map<String, Product> mapMemento;

    public Memento(Map<String, Product> map) {
        setMemento(map);
    }

    public Map<String, Product> getMemento() {
        return mapMemento;
    }

    public void setMemento(Map<String, Product> map) {
        mapMemento = new TreeMap<>(map);
    }
}