package test.types0;

import static org.junit.jupiter.api.Assertions.*;
import static types0.Types.*;

import org.junit.jupiter.api.Test;

import types0.Binding;
import types0.FunctionType;
import types0.TemplateType;
import types0.VariableType;

public class TestUnify {

    @Test
    public void testSimpleTypeMatch() {
        Binding b = new Binding();
        assertTrue(INT.unify(INT, b));
        assertEquals(0, b.size());
    }

    @Test
    public void testSimpleTypeUnmatch() {
        Binding b = new Binding();
        assertFalse(INT.unify(STRING, b));
    }

    @Test
    public void testSimpleTypeFunctionType() {
        Binding b = new Binding();
        FunctionType f = FunctionType.of(INT);
        assertFalse(INT.unify(f, b));
    }

    @Test
    public void testVariableTypeSimpleType() {
        Binding b = new Binding();
        VariableType v = VariableType.of("v");
        assertTrue(v.unify(INT, b));
        assertEquals(1, b.size());
        assertEquals(INT, b.get(v));
    }

    @Test
    public void testSimpleTypeVariableType() {
        Binding b = new Binding();
        VariableType v = VariableType.of("v");
        assertTrue(INT.unify(v, b));
        assertEquals(1, b.size());
        assertEquals(INT, b.get(v));
    }

    @Test
    public void testVariableTypeVariableType() {
        Binding b = new Binding();
        VariableType v0 = VariableType.of("v0");
        VariableType v1 = VariableType.of("v1");
        VariableType v2 = VariableType.of("v2");
        VariableType v3 = VariableType.of("v3");
        VariableType v4 = VariableType.of("v4");
        assertTrue(v0.unify(v1, b));
        System.out.println(b);
        assertTrue(v0.unify(v2, b));
        System.out.println(b);
        assertTrue(v0.unify(v1, b));
        System.out.println(b);
        assertTrue(v3.unify(v4, b));
        System.out.println(b);
        assertTrue(v0.unify(v4, b));
        System.out.println(b);
        assertEquals(0, b.size());
        assertTrue(v3.unify(INT, b));
        System.out.println(b);
        assertEquals(5, b.size());
    }

    @Test
    public void testFunctionTypeSimpleType() {
        Binding b = new Binding();
        FunctionType f = FunctionType.of(INT);
        assertFalse(f.unify(INT, b));
    }

    @Test
    public void testFunctionTypeFunctionType() {
        Binding b = new Binding();
        FunctionType f = FunctionType.of(INT, INT, INT);
        VariableType v = VariableType.of("v");
        VariableType w = VariableType.of("w");
        FunctionType g = FunctionType.of(INT, v, w);
        assertTrue(g.unify(f, b));
        System.out.println(b);
        assertEquals(INT, b.get(v));
        assertEquals(INT, b.get(w));
    }

    @Test
    public void testTemplateType() {
        Binding b = new Binding();
        VariableType v0 = VariableType.of("v0");
        TemplateType array = TemplateType.of(v0);
        FunctionType newArray = FunctionType.of(array, INT);
        FunctionType set = FunctionType.of(array, array, INT, v0);
        FunctionType get = FunctionType.of(v0, array, INT);
        VariableType v1 = VariableType.of("v1");
        VariableType v2 = VariableType.of("v2");
        VariableType v3 = VariableType.of("v3");
        FunctionType newArrayInt = FunctionType.of(v1, INT);
        FunctionType setArrayIntInt = FunctionType.of(v2, v1, INT, INT);
        FunctionType getArrayInt = FunctionType.of(v3, v2, INT);
        assertTrue(newArrayInt.unify(newArray, b));
        assertTrue(setArrayIntInt.unify(set, b));
        assertTrue(getArrayInt.unify(get, b));
        System.out.println(b);
        System.out.println(newArrayInt.resolve(b));
        System.out.println(setArrayIntInt.resolve(b));
        System.out.println(getArrayInt.resolve(b));
    }

}
