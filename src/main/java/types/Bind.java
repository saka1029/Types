package types;

import java.util.HashMap;
import java.util.Map;

public class Bind {

    private final Map<VariableType, ConcreteType> bind = new HashMap<>();

    public ConcreteType get(VariableType v) {
        return bind.get(v);
    }

    public void put(VariableType v, ConcreteType c) {
        bind.put(v, c);
    }

    @Override
    public String toString() {
        return "bind" + bind;
    }


}
