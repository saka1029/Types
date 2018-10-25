package types0;

public class VariableType implements Type {

    private static int seed = 0;
    private final int sequence;
    private final String name;

    private VariableType(String name) {
        this.name = name;
        this.sequence = seed++;
    }

    public static VariableType of(String name) {
        return new VariableType(name);
    }

    @Override
    public String toString() {
        return name + "%" + sequence;
    }

    @Override
    public boolean unify(Type right, Binding binding) {
        return binding.put(this, right);
    }

    @Override
    public Type resolve(Binding binding) {
        Type c = binding.get(this);
        return c == null ? this : c.resolve(binding);
    }
}
