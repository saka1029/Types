package types;

public interface Type {

    boolean unify(Type t, Bind b);

}
