package types;

public class TemplateType extends StructureType {

    public TemplateType(Type... types) {
        super(types);
    }

    @Override
    public boolean unify(Type t, Bind b) {
        if (t instanceof Variable)
            return t.unify(this, b);
        else if (t instanceof TemplateType)
            return match((TemplateType)t, b);
        else
            return false;
    }

    @Override
    public String toString() {
        return "tp" + types;
    }
}
