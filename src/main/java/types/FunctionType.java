package types;

public class FunctionType extends StructureType {

    public FunctionType(Type... types) {
        super(types);
    }

    @Override
    public boolean unify(Type t, Bind b) {
        if (t instanceof Variable)
            return t.unify(this, b);
        else if (t instanceof FunctionType)
            return match((FunctionType)t, b);
        else
            return false;
    }

    @Override
    public String toString() {
        return "fn" + types;
    }
}
