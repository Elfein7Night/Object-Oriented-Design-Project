package Model;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F _first, S _second) {
        first = _first;
        second = _second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }
}
