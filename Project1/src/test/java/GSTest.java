import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class GSTest {
    GenericStack<Integer> s1;
    GenericStack<String> s2;
    ArrayList<String> dumpedStack;
    private static int iter2;

    // Setup iterator
    @BeforeAll
    static void setup() {
        iter2 = 0;
    }

    // Initialize stacks and dumped array to be used in tests
    @BeforeEach
    void init() {
        s1 = new GenericStack<>(2);
        s1.push(3);
        s1.push(4);
        s1.push(5);

        s2 = new GenericStack<>("first");
        s2.push("second");
        s2.push("third");
        s2.push("fourth");

        dumpedStack = s2.dumpList();
    }

    // Test constructor
    @Test
    void construct() {
        GenericStack<Double> s3 = new GenericStack<Double>(2.55);

        assertEquals(1, s3.getLength());

        assertEquals(2.55, s3.get(0));
    }

    // Test push as well as getLength and get
    @Test
    void addStack() {
        GenericStack<Integer> s2 = new GenericStack<>(2);

        s2.push(3);
        assertEquals(2, s2.getLength());
        assertEquals(3, s2.get(0));

        s2.push(4);
        assertEquals(3, s2.getLength());
        assertEquals(4, s2.get(0));

        s2.push(5);
        assertEquals(4, s2.getLength());
        assertEquals(5, s2.get(0));
    }

    // Test pop
    @Test
    void removeStack() {
        assertEquals(4, s1.getLength());
        assertEquals(5, s1.pop());

        assertEquals(3, s1.getLength());
        assertEquals(4, s1.pop());

        assertEquals(2, s1.getLength());
        assertEquals(3, s1.pop());

        assertEquals(1, s1.getLength());
        assertEquals(2, s1.pop());

        // List should now be empty -- Pop should return null
        assertEquals(0, s1.getLength());
        assertNull(s1.pop());
    }

    // Test set
    @Test
    void setStack() {
        assertEquals(5, s1.set(0, 10));
        assertEquals(4, s1.set(1, 11));
        assertEquals(3, s1.set(2, 12));
        assertEquals(2, s1.set(3, 13));

        int tester = 10;

        // Test for each loop works properly
        for(Integer i : s1) {
            assertEquals(tester, i);
            tester++;
        }
    }

    // Test remove tail
    @Test
    void removeTailStack() {
        assertEquals(4, s1.getLength());
        assertEquals(2, s1.removeTail());

        assertEquals(3, s1.getLength());
        assertEquals(3, s1.removeTail());

        assertEquals(2, s1.getLength());
        assertEquals(4, s1.removeTail());

        assertEquals(1, s1.getLength());
        assertEquals(5, s1.removeTail());

        assertEquals(0, s1.getLength());
        assertNull(s1.removeTail());
        // Test getHead
        assertNull(s1.getHead());
    }

    // Test Iterator
    @Test
    void stackIteratorTest() {

        Iterator<Integer> iter = s1.iterator();

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());

        assertFalse(iter.hasNext());
        assertNull(iter.next());
    }

    // Test descending iterator
    @Test
    void stackReverseTest() {

        Iterator<Integer> iter = s1.descendingIterator();

        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());

        assertFalse(iter.hasNext());
        assertNull(iter.next());

        assertEquals(5, s1.get(0));
    }

    // Test list iterator
    @Test
    void stackListIteratorTest() {
        s1.push(6);

        ListIterator<Integer> iter = s1.listIterator(2);

        assertEquals(2, iter.nextIndex());
        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());

        assertEquals(3, iter.nextIndex());
        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());

        assertEquals(4, iter.nextIndex());
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());

        assertFalse(iter.hasNext());
        assertTrue(iter.hasPrevious());
        assertEquals(5, iter.nextIndex());
        assertEquals(4, iter.previousIndex());
        assertEquals(2, iter.previous());

        assertEquals(3, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(3, iter.previous());

        assertEquals(2, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(4, iter.previous());

        assertEquals(1, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(5, iter.previous());

        assertEquals(0, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(6, iter.previous());

        assertFalse(iter.hasPrevious());
    }

    // Test with characters
    @Test
    void charTest() {
        GenericStack<Character> s = new GenericStack<>('a');

        assertEquals('a', s.pop());
        assertEquals(0, s.getLength());

        s.add('b');
        s.add('c');
        s.add('d');

        s.print();

        assertEquals('d', s.set(0, 'x'));
        assertEquals('c', s.set(1, 'y'));
        assertEquals('b', s.set(2, 'z'));
        assertEquals(3, s.getLength());

        assertEquals('x', s.delete());
        assertEquals('z', s.removeTail());

        assertEquals(1, s.getLength());
        assertEquals('y', s.get(0));
    }

    // Test dumpArray
    @ParameterizedTest
    @ValueSource(strings = {"fourth", "third", "second", "first"})
    void stackParamTest(String str) {
        assertEquals(str, dumpedStack.get(iter2));
        iter2++;
    }
}
