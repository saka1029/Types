package types;

import java.util.HashSet;
import java.util.Set;

public class VariableGroup implements ConcreteType, Variable {

    private final Set<VariableType> vars = new HashSet<>();

    private VariableGroup() {
    }

    public static boolean bind(Bind b, VariableType... vs) {
        VariableGroup g = new VariableGroup();
        for (VariableType v : vs) {
            g.vars.add(v);
            b.put(v, g);
        }
        return true;
    }

    public static boolean bind(Bind b, VariableGroup... gs) {
        VariableGroup n = new VariableGroup();
        for (VariableGroup g : gs)
            for (VariableType v : g.vars) {
                n.vars.add(v);
                b.put(v, n);
            }
        return true;
    }

    public int size() {
        return vars.size();
    }

    public boolean contains(VariableType v) {
        return vars.contains(v);
    }

    private boolean bindAll(ConcreteType c, Bind b) {
        for (VariableType v : vars)
            b.put(v, c);
        return true;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        System.out.println("VariableGroup unify " + this + ", " + t);
        if (t instanceof VariableType) {
            VariableType v = (VariableType)t;
            if (b.get(v) == null) {
                vars.add(v);
                b.put(v, this);
                return true;
            } else
                return t.unify(this, b);
        } else if (t instanceof VariableGroup)
            return bind(b, this, (VariableGroup)t);
        else /* t instanceof ConcreteType */
            return bindAll((ConcreteType)t, b);
    }

    @Override
    public String toString() {
        return "group" + vars;
    }

}
