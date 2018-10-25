package types0;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemplateType extends StructuredType {

    private final List<Type> types = new ArrayList<Type>();

    private TemplateType(Type... types) {
        Objects.requireNonNull(types, "types");
        if (types.length < 1)
            throw new IllegalArgumentException("types.size() == 0");
        for (Type type : types)
            this.types.add(type);
    }

    public static TemplateType of(Type... types) {
        return new TemplateType(types);
    }

    @Override
    public String toString() {
        return "tp" + types.toString();
    }

    @Override
    public boolean unify(Type right, Binding binding) {
        if (right instanceof TemplateType)
            return match((TemplateType)right, binding);
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
        return new TemplateType(n);
    }

}
