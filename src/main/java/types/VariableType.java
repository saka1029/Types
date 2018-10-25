package types;

public class VariableType implements Type, Variable {

    public final String name;

    public VariableType(String name) {
        this.name = name;
    }

    private boolean matchVariable(VariableType v, Bind b) {
        ConcreteType t = b.get(v);
        if (t == null) {
            VariableGroup g = new VariableGroup(this, v);
            b.put(this, g);
            b.put(v, g);
            return true;
        } else
            return t.unify(this, b);
    }

    private boolean bind(ConcreteType c, Bind b) {
        b.put(this, c);
        return true;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        ConcreteType c = b.get(this);
        if (c != null)
            return c.unify(t, b);
        else if (t instanceof Variable)
            return matchVariable((VariableType)t, b);
        else
            return bind((ConcreteType)t, b);
    }

    @Override
    public String toString() {
        return name;
    }
}
