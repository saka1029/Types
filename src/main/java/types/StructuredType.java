package types;

import java.util.ArrayList;
import java.util.List;

public abstract class StructuredType implements ConcreteType {

    protected final List<Type> types = new ArrayList<>();

    protected boolean match(StructuredType right, Binding binding) {
        int size = types.size();
        if (right.types.size() != size)
            return false;
        for (int i = 0; i < size; ++i)
            if (!types.get(i).unify(right.types.get(i), binding))
                return false;
        return true;
    }

}
