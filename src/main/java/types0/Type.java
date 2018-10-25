package types0;

public interface Type {

    boolean unify(Type right, Binding binding);
    Type resolve(Binding binding);

}
