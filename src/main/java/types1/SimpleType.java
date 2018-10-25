package types1;

public class SimpleType implements ConcreteType {

    public final String name;

    public SimpleType(String name) {
        this.name = name;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        if (t == this)
            return true;
        else if (t instanceof VariableType)
            return t.unify(this, b);
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

}
