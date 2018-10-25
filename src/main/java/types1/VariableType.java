package types1;

public class VariableType implements Type {

    public final String name;

    public VariableType(String name) {
        this.name = name;
    }

    @Override
    public boolean unify(Type t, Bind b) {
        return b.put(this, t);
    }

    @Override
    public String toString() {
        return name;
    }
}
