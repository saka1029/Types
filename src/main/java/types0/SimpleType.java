package types0;

public class SimpleType implements ConcreteType {

    private final String name;
    public String name() { return name; }

    private SimpleType(String name) {
        this.name = name;
    }

    public static SimpleType of(String name) {
        return new SimpleType(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean unify(Type right, Binding binding) {
        if (right.equals(this))
            return true;
        if (right instanceof VariableType)
            return right.unify(this, binding);
        return false;
    }

    @Override
    public Type resolve(Binding binding) {
        return this;
    }
}
