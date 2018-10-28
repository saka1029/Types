package test.types;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import types.Bind;
import types.FunctionType;
import types.SimpleType;
import types.TemplateType;
import types.VariableGroup;
import types.VariableType;

public class TestUnify {

    static SimpleType INT = new SimpleType("int");
    static SimpleType STRING = new SimpleType("string");
    static SimpleType DOUBLE = new SimpleType("double");

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
    public void testSimpleTypeVariableType2() {
        Bind b = new Bind();
        VariableType x = new VariableType("x");
        VariableType y = new VariableType("y");
        assertTrue(INT.unify(x, b));
        assertEquals(INT, b.get(x));
        assertTrue(y.unify(x, b));
        assertEquals(INT, b.get(x));
        assertEquals(INT, b.get(y));
    }

    @Test
    void testFunctionType() {
        FunctionType f = new FunctionType(INT, INT, INT);
        assertEquals("fn[int, int, int]", f.toString());
    }

    @Test
    void testFunctionTypeSimpleType() {
        Bind b = new Bind();
        FunctionType f = new FunctionType(INT, INT, INT);
        assertFalse(f.unify(INT, b));
    }

    @Test
    void testFunctionTypeFunctionType() {
        Bind b = new Bind();
        FunctionType f1 = new FunctionType(INT, INT, INT);
        FunctionType f2 = new FunctionType(INT, INT, INT);
        assertTrue(f1.unify(f2, b));
    }

    @Test
    void testFunctionTypeFunctionTypeFail() {
        Bind b = new Bind();
        FunctionType f1 = new FunctionType(INT, INT, INT);
        FunctionType f2 = new FunctionType(INT, INT);
        assertFalse(f1.unify(f2, b));
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
    public void testFunctionType5() {
        Bind b = new Bind();
        VariableType T = new VariableType("T");
        VariableType X = new VariableType("X");
        VariableType Y = new VariableType("Y");
        VariableType Z = new VariableType("Z");
        assertTrue(new FunctionType(X, Y, Z).unify(new FunctionType(T, T, T), b));
        assertTrue(b.get(T) instanceof VariableGroup);
        assertEquals(4, ((VariableGroup)b.get(T)).size());
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
        assertTrue(b.get(v1) instanceof VariableGroup);
        assertEquals(2, ((VariableGroup)b.get(v1)).size());
        assertTrue(((VariableGroup)b.get(v1)).contains(v1));
        assertTrue(((VariableGroup)b.get(v1)).contains(v2));
        assertTrue(v3.unify(v4, b));
        System.out.println(b);
        assertTrue(b.get(v3) instanceof VariableGroup);
        assertTrue(((VariableGroup)b.get(v3)).contains(v3));
        assertTrue(((VariableGroup)b.get(v3)).contains(v4));
        assertTrue(v1.unify(v3, b));
        System.out.println(b);
        assertTrue(b.get(v1) instanceof VariableGroup);
        assertTrue(((VariableGroup)b.get(v1)).contains(v1));
        assertTrue(((VariableGroup)b.get(v1)).contains(v2));
        assertTrue(((VariableGroup)b.get(v1)).contains(v3));
        assertTrue(((VariableGroup)b.get(v1)).contains(v4));
        assertTrue(v1.unify(INT, b));
        System.out.println(b);
        assertEquals(INT, b.get(v1));
        assertEquals(INT, b.get(v2));
        assertEquals(INT, b.get(v3));
        assertEquals(INT, b.get(v4));
    }

    @Test
    public void testTemplateType() {
        Bind b = new Bind();
        VariableType v0 = new VariableType("v0");
        TemplateType array = new TemplateType(v0);
        FunctionType newArray = new FunctionType(array, INT);
        FunctionType set = new FunctionType(array, array, INT, v0);
        FunctionType get = new FunctionType(v0, array, INT);
        VariableType v1 = new VariableType("v1");
        VariableType v2 = new VariableType("v2");
        VariableType v3 = new VariableType("v3");
        FunctionType newArrayInt = new FunctionType(v1, INT);
        FunctionType setArrayIntInt = new FunctionType(v2, v1, INT, INT);
        FunctionType getArrayInt = new FunctionType(v3, v2, INT);
        assertTrue(newArrayInt.unify(newArray, b));
        assertTrue(setArrayIntInt.unify(set, b));
        assertTrue(getArrayInt.unify(get, b));
        System.out.println(b);
    }

    /**
     * 2.1 型定義
     * (define :T :T) :T
     * (+ :int :int) :int
     * (+ :double :double) :double
     * 2.2 プログラム
     * (define x (+ 2 1))
     * 2.3 型参照
     * (define :X :Y) :Z
     * (+ :int :int) :Y
     * defineの先頭から見て行って各引数に順次型変数を割り当てる。
     */
    @Test
    public void testDefine() {
        Bind b = new Bind();
        VariableType T = new VariableType("T");
        FunctionType define = new FunctionType(T, T, T);
        FunctionType plusInt = new FunctionType(INT, INT, INT);
        FunctionType plusDouble = new FunctionType(DOUBLE, DOUBLE, DOUBLE);
        VariableType X = new VariableType("X");
        VariableType Y = new VariableType("Y");
        VariableType Z = new VariableType("Z");
        FunctionType program = new FunctionType(Z, X, Y);
        FunctionType add = new FunctionType(Y, INT, INT);
        assertTrue(program.unify(define, b));
        System.out.println(b);
        assertTrue(add.unify(plusInt, b));
        System.out.println(b);
        assertEquals(INT, b.get(T));
        assertEquals(INT, b.get(X));
        assertEquals(INT, b.get(Y));
        assertEquals(INT, b.get(Z));
    }

    @Test
    public void testGenericArray() {
        Bind b = new Bind();
        VariableType T = new VariableType("T");
        TemplateType Array = new TemplateType(T);
        FunctionType newArray = new FunctionType(Array, INT);
        FunctionType set = new FunctionType(Array, Array, INT, T);
        FunctionType get = new FunctionType(T, Array, INT);
        VariableType X = new VariableType("X");
        VariableType Y = new VariableType("Y");
        VariableType Z = new VariableType("Z");
        VariableType U = new VariableType("U");
        assertTrue(new FunctionType(X, INT).unify(newArray, b));
        assertTrue(new FunctionType(Y, Z, INT, INT).unify(set, b));
        assertTrue(new FunctionType(U, Y, INT).unify(get, b));
        System.out.println(b);
    }

    @Test
    public void testRecursiveType() {
        Bind b = new Bind();
        VariableType X = new VariableType("X");
        TemplateType t = new TemplateType(X);
        assertTrue(t.unify(X, b));
        assertTrue(b.get(X) instanceof TemplateType);
        assertEquals(t, b.get(X));
    }

}