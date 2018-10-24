package types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Binding implements Iterable<Entry<VariableType, ConcreteType>> {

    private final Map<VariableType, ConcreteType> bind = new HashMap<>();
    List<Set<VariableType>> equiv = new ArrayList<>();


    public int size() {
        return bind.size();
    }

    public Type get(VariableType variable) {
        return bind.get(variable);
    }

    private boolean unify(ConcreteType c1, ConcreteType c2) {
        return c1.unify(c2, this);
    }

    private boolean bind(ConcreteType c, int i) {
        for (VariableType v : equiv.get(i))
            bind.put(v, c);
        return true;
    }

    private boolean bind(VariableType v, ConcreteType c) {
        bind.put(v, c);
        return true;
    }

    private boolean merge(int i1, int i2) {
        if (i1 == i2) return true;
        equiv.get(i1).addAll(equiv.get(i2));
        equiv.remove(i2);
        return true;
    }

    private boolean equiv(int i, VariableType v) {
        equiv.get(i).add(v);
        return true;
    }

    private boolean equiv(VariableType v1, VariableType v2) {
        Set<VariableType> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        equiv.add(set);
        return true;
    }

    private int findEquiv(VariableType v) {
        for (int i = 0, size = equiv.size(); i < size; ++i)
            if (equiv.get(i).contains(v))
                return i;
        return -1;
    }

    private boolean putVariable(VariableType v1, VariableType v2) {
        ConcreteType c1 = bind.get(v1);
        int i1 = -1;
        if (c1 == null) i1 = findEquiv(v1);
        ConcreteType c2 = bind.get(v2);
        int i2 = -1;
        if (c2 == null) i2 = findEquiv(v2);
        if (c1 != null)
            if (c2 != null)
                return unify(c1, c2);
            else if (i2 != -1)
                return bind(c1, i2);
            else
                return bind(v2, c1);
        else if (i1 != -1)
            if (c2 != null)
                return bind(c2, i1);
            else if (i2 != -1)
                return merge(i1, i2);
            else
                return equiv(i1, v2);
        else
            if (c2 != null)
                return bind(v1, c2);
            else if (i2 != -1)
                return equiv(i2, v1);
            else
                return equiv(v1, v2);
    }

    private boolean putConcrete(VariableType v, ConcreteType c) {
        ConcreteType origin = bind.get(v);
        if (origin != null)
            return c.unify(origin, this);
        int i = findEquiv(v);
        if (i == -1) {
            bind.put(v, c);
            return true;
        }
        for (VariableType e : equiv.get(i))
            bind.put(e, c);
        equiv.remove(i);
        return true;
    }

    public boolean put(VariableType variable, Type type) {
        if (type instanceof VariableType)
            return putVariable(variable, (VariableType)type);
        else if (type instanceof ConcreteType)
            return putConcrete(variable, (ConcreteType)type);
        else
            throw new IllegalArgumentException("unknown type (" + type + ")");
    }

    @Override
    public Iterator<Entry<VariableType, ConcreteType>> iterator() {
        return bind.entrySet().iterator();
    }

    @Override
    public String toString() {
        return bind.toString() + equiv.toString();
    }

}
