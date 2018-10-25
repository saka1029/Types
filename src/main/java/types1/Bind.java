package types1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bind {

    private final Map<VariableType, ConcreteType> bind = new HashMap<>();

    public ConcreteType get(VariableType v) {
        return bind.get(v);
    }

    @Override
    public String toString() {
        return "bind" + bind;
    }

    public static class Group implements ConcreteType {

        private final List<VariableType> vars = new ArrayList<>();

        private Group(VariableType... vars) {
            for (VariableType var : vars)
                this.vars.add(var);
        }

        @Override
        public String toString() {
            return "gr" + vars;
        }

        @Override
        public boolean unify(Type r, Bind bind) {
            throw new UnsupportedOperationException("unify");
        }
    }

    private boolean addGroup(Group g, VariableType v) {
        g.vars.add(v);
        bind.put(v, g);
        return true;
    }

    private boolean resolveGroup(Group g, ConcreteType c) {
        for (VariableType v : g.vars)
            bind.put(v, c);
        return true;
    }

    private boolean mergeGroup(Group g1, Group g2) {
        g1.vars.addAll(g2.vars);
        for (VariableType v : g2.vars)
            bind.put(v, g1);
        return true;
    }

    private boolean newGroup(VariableType v1, VariableType v2) {
        Group g = new Group(v1, v2);
        bind.put(v1, g);
        bind.put(v2, g);
        return true;
    }

    private boolean bind(VariableType v, ConcreteType c) {
        bind.put(v, c);
        return true;
    }

    private boolean putVariable(VariableType v1, VariableType v2) {
        ConcreteType c1 = bind.get(v1);
        ConcreteType c2 = bind.get(v2);
        if (c1 == null)
            if (c2 == null)
                return newGroup(v1, v2);
            else if (c2 instanceof Group)
                return addGroup((Group)c2, v1);
            else /* if (c2 instanceof ConcreteType except Group) */
                return bind(v1, c2);
        else if (c1 instanceof Group)
            if (c2 == null)
                return addGroup((Group)c1, v2);
            else if (c2 instanceof Group)
                return mergeGroup((Group)c1, (Group)c2);
            else /* if (c2 instanceof ConcreteType except Group) */
                return resolveGroup((Group)c1, c2);
        else /* if (c2 instanceof ConcreteType except Group) */
            if (c2 == null)
                return bind(v2, c1);
            else if (c2 instanceof Group)
                return resolveGroup((Group)c2, c1);
            else /* if (c2 instanceof ConcreteType except Group) */
                return c1.unify(c2, this);
    }

    private boolean putConcrete(VariableType v, ConcreteType c) {
        ConcreteType c0 = bind.get(v);
        if (c0 == null) {
            bind.put(v, c);
            return true;
        } else if (c0 instanceof Group) {
            for (VariableType e : ((Group)c0).vars)
                bind.put(e, c);
            return true;
        } else /* if (c0 instanceof ConcreteType except Group) */
            return c.unify(c0, this);
    }

    public boolean put(VariableType v, Type c) {
        if (c instanceof VariableType)
            return putVariable(v, (VariableType)c);
        else if (c instanceof ConcreteType)
            return putConcrete(v, (ConcreteType)c);
        else
            throw new IllegalArgumentException("invalid type: " + c);
    }

}
