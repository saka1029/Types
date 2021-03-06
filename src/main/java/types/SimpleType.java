package types;

public class SimpleType implements ConcreteType {

    public final String name;

    public SimpleType(String name) {
        this.name = name;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        if (t instanceof Variable)
            return t.unify(this, b);
        else
            return t.equals(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
