package types0;

public class Types {

    public static VariableType var(String name) { return VariableType.of(name); }
    public static SimpleType simple(String name) { return SimpleType.of(name); }
    public static SimpleType INT = simple("int");
    public static SimpleType STRING = simple("string");

}
