package types0;

import java.util.Objects;

public class FunctionType extends StructuredType {

    private FunctionType(Type... types) {
        Objects.requireNonNull(types, "types");
        if (types.length < 1)
            throw new IllegalArgumentException("types.size() == 0");
        for (Type type : types)
            this.types.add(type);
    }

    public static FunctionType of(Type... types) {
        return new FunctionType(types);
    }

    @Override
    public String toString() {
        return "fn" + types.toString();
    }

    @Override
    public boolean unify(Type right, Binding binding) {
        if (right instanceof FunctionType)
            return match((FunctionType)right, binding);
        if (right instanceof VariableType)
            return right.unify(this, binding);
        return false;
    }

    @Override
    public Type resolve(Binding binding) {
        int size = types.size();
        Type[] n = new Type[size];
        for (int i = 0; i < size; ++i)
            n[i] = types.get(i).resolve(binding);
        return new FunctionType(n);
    }
}
