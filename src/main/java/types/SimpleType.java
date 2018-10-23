package types;

public class SimpleType implements Type {

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
}
