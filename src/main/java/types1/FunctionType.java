package types1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionType implements ConcreteType {

    public final List<Type> types;

    public FunctionType(Type... types) {
        List<Type> temp = new ArrayList<>();
        for (Type type : types)
            temp.add(type);
        this.types = Collections.unmodifiableList(temp);
    }

    @Override
    public boolean unify(Type t, Bind b) {
        if (t instanceof FunctionType) {
            FunctionType f = (FunctionType)t;
            int size = types.size();
            if (size != f.types.size())
                return false;
            for (int i = 0; i < size; ++i)
                if (!types.get(i).unify(f.types.get(i), b))
                    return false;
            return true;
        } else if (t instanceof VariableType)
            return t.unify(this, b);
        else
            return false;
    }

    @Override
    public String toString() {
        return "fn" + types;
    }
}
