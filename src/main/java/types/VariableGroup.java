package types;

import java.util.HashSet;
import java.util.Set;

public class VariableGroup implements ConcreteType, Variable {

    private final Set<VariableType> vars = new HashSet<>();

    public VariableGroup(VariableType... vs) {
        for (VariableType v : vs)
            vars.add(v);
    }

    public int size() {
        return vars.size();
    }

    public boolean contains(VariableType v) {
        return vars.contains(v);
    }

    private boolean mergeGroup(VariableGroup g, Bind b) {
        vars.addAll(g.vars);
        for (VariableType v : g.vars)
            b.put(v, this);
        return true;
    }

    private boolean bindAll(ConcreteType c, Bind b) {
        for (VariableType v : vars)
            b.put(v, c);
        return true;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        if (t instanceof VariableType)
            return t.unify(this, b);
        else if (t instanceof VariableGroup)
            return mergeGroup((VariableGroup)t, b);
        else /* t instanceof ConcreteType */
            return bindAll((ConcreteType)t, b);
    }

    @Override
    public String toString() {
        return "group" + vars;
    }

}
