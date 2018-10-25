package test.types1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import types1.Bind;
import types1.FunctionType;
import types1.SimpleType;
import types1.VariableType;

class TestUnify {

    static SimpleType INT = new SimpleType("int");
    static SimpleType STRING = new SimpleType("string");

    @Test
    void testSimpleTypeSimpleType() {
        Bind b = new Bind();
        assertTrue(INT.unify(INT, b));
        assertFalse(INT.unify(STRING, b));
    }

    @Test
    void testSimpleTypeVariableType() {
        Bind b = new Bind();
        VariableType x = new VariableType("x");
        assertTrue(INT.unify(x, b));
        assertEquals(INT, b.get(x));
    }

    @Test
    void testFunctionTypeFunctionType() {
        Bind b = new Bind();
        FunctionType f1 = new FunctionType(INT, INT, INT);
        FunctionType f2 = new FunctionType(INT, INT, INT);
        assertTrue(f1.unify(f2, b));
    }

    @Test
    void testFunctionTypeVariableType() {
        Bind b = new Bind();
        FunctionType f = new FunctionType(INT, INT, INT);
        VariableType x = new VariableType("x");
        assertTrue(f.unify(x, b));
        assertEquals(f, b.get(x));
    }

    @Test
    void testFunctionTypeVariableType2() {
        Bind b = new Bind();
        FunctionType f1 = new FunctionType(INT, INT, STRING);
        VariableType x = new VariableType("x");
        VariableType y = new VariableType("y");
        FunctionType f2 = new FunctionType(INT, x, y);
        assertTrue(f1.unify(f2, b));
        assertEquals(INT, b.get(x));
        assertEquals(STRING, b.get(y));
    }

    @Test
    void testFunctionTypeVariableType3() {
        Bind b = new Bind();
        FunctionType f1 = new FunctionType(INT, INT, INT);
        VariableType x = new VariableType("x");
        FunctionType f2 = new FunctionType(INT, x, x);
        assertTrue(f1.unify(f2, b));
        assertEquals(INT, b.get(x));
    }

    @Test
    void testFunctionTypeVariableType4() {
        Bind b = new Bind();
        FunctionType f1 = new FunctionType(INT, STRING, INT);
        VariableType x = new VariableType("x");
        FunctionType f2 = new FunctionType(INT, x, x);
        assertFalse(f1.unify(f2, b));
    }

    @Test
    void testVariables() {
        Bind b = new Bind();
        VariableType v1 = new VariableType("v1");
        VariableType v2 = new VariableType("v2");
        VariableType v3 = new VariableType("v3");
        VariableType v4 = new VariableType("v4");
        assertTrue(v1.unify(v2, b));
        System.out.println(b);
        assertTrue(v3.unify(v4, b));
        System.out.println(b);
        assertTrue(v1.unify(v3, b));
        System.out.println(b);
        assertTrue(v1.unify(INT, b));
        System.out.println(b);
        assertEquals(INT, b.get(v1));
        assertEquals(INT, b.get(v2));
        assertEquals(INT, b.get(v3));
        assertEquals(INT, b.get(v4));
    }

}
