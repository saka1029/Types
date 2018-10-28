package types;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Bind implements Iterable<Entry<VariableType, ConcreteType>>{

    private final Map<VariableType, ConcreteType> bind = new HashMap<>();

    public int size() {
        return bind.size();
    }

    public ConcreteType get(VariableType v) {
        return bind.get(v);
    }

    public void put(VariableType v, ConcreteType c) {
        bind.put(v, c);
    }

    @Override
    public Iterator<Entry<VariableType, ConcreteType>> iterator() {
        return bind.entrySet().iterator();
    }

    @Override
    public String toString() {
        return "bind" + bind;
    }
}
