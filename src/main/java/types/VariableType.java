package types;

public class VariableType implements Type, Variable {

    public final String name;

    public VariableType(String name) {
        this.name = name;
    }

    private boolean matchVariable(VariableType v, Bind b) {
        ConcreteType t = b.get(v);
        if (t == null)
            return VariableGroup.bind(b, this, v);
        else
            return t.unify(this, b);
    }

    private boolean bind(ConcreteType c, Bind b) {
        b.put(this, c);
        return true;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        System.out.println("VariableType unify " + this + ", " + t);
        ConcreteType c = b.get(this);
        if (c != null)
            return c.unify(t, b);
        else if (t instanceof VariableType)
            return matchVariable((VariableType)t, b);
        else
            return bind((ConcreteType)t, b);
    }

    @Override
    public String toString() {
        return name;
    }
}
