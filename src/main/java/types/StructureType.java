package types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class StructureType implements ConcreteType {

    public final List<Type> types;

    protected StructureType(Type... types) {
        List<Type> temp = new ArrayList<>();
        for (Type type : types)
            temp.add(type);
        this.types = Collections.unmodifiableList(temp);
    }

    protected boolean match(StructureType f, Bind b) {
        int size = types.size();
        if (size != f.types.size())
            return false;
        for (int i = 0; i < size; ++i)
            if (!types.get(i).unify(f.types.get(i), b))
                return false;
        return true;
    }
}
