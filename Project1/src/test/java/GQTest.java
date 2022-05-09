import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class GQTest {
    GenericQueue<Integer> q1;
    GenericQueue<String> q2;
    ArrayList<String> dumpedQueue;
    private static int iter1;

    // Setup iterator
    @BeforeAll
    static void setup() {
        iter1 = 0;
    }

    // Initialize queues and dumped array to be used in tests
    @BeforeEach
    void init() {
        q1 = new GenericQueue<>(3);
        q1.enqueue(4);
        q1.enqueue(5);
        q1.enqueue(6);

        q2 = new GenericQueue<>("first");
        q2.enqueue("second");
        q2.enqueue("third");
        q2.enqueue("fourth");

        dumpedQueue = q2.dumpList();
    }

    // Test queue constructor
    @Test
    void construct() {
        GenericQueue<Double> q3 = new GenericQueue<>(1.55);
        assertEquals(1, q3.getLength());
        assertEquals(1.55, q3.get(0));
    }

    // Test enqueue method as well as getLength and get
    @Test
    void addQueue() {
        GenericQueue<Integer> q2 = new GenericQueue<>(3);
        q2.enqueue(10);
        assertEquals(2, q2.getLength());
        assertEquals(3, q2.get(0));

        q2.enqueue(11);
        assertEquals(3, q2.getLength());
        assertEquals(3, q2.get(0));

        q2.enqueue(12);
        assertEquals(4, q2.getLength());
        assertEquals(3, q2.get(0));
    }

    // Tests dequeue
    @Test
    void removeQueue() {
        assertEquals(4, q1.getLength());
        assertEquals(3, q1.dequeue());

        assertEquals(3, q1.getLength());
        assertEquals(4, q1.dequeue());

        assertEquals(2, q1.getLength());
        assertEquals(5, q1.dequeue());

        assertEquals(1, q1.getLength());
        assertEquals(6, q1.dequeue());

        assertEquals(0, q1.getLength());
        // List should be empty -- dequeue should return null
        assertNull(q1.dequeue());
    }

    // Test set method
    @Test
    void setQueue() {
        assertEquals(3, q1.set(0, 10));
        assertEquals(4, q1.set(1, 11));
        assertEquals(5, q1.set(2, 12));
        assertEquals(6, q1.set(3, 13));

        AtomicInteger tester = new AtomicInteger(10);

        // Testing that foreach works properly
        q1.forEach(e->assertEquals(tester.getAndIncrement(), e));
    }

    // Tests removeTail
    @Test
    void removeTailQueue() {

        assertEquals(4, q1.getLength());
        assertEquals(6, q1.removeTail());

        assertEquals(3, q1.getLength());
        assertEquals(5, q1.removeTail());

        assertEquals(2, q1.getLength());
        assertEquals(4, q1.removeTail());

        assertEquals(1, q1.getLength());
        assertEquals(3, q1.removeTail());

        assertEquals(0, q1.getLength());
        assertNull(q1.removeTail());
        // Testing getHead
        assertNull(q1.getHead());
    }

    // Testing iterator
    @Test
    void queueIteratorTest() {
        Iterator<Integer> iter = q1.iterator();

        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(6, iter.next());

        assertFalse(iter.hasNext());
        assertNull(iter.next());
    }

    // Testing descendingIterator
    @Test
    void queueReverseTest() {

        Iterator<Integer> iter = q1.descendingIterator();

        assertTrue(iter.hasNext());
        assertEquals(6, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(4, iter.next());

        assertTrue(iter.hasNext());
        assertEquals(3, iter.next());

        assertFalse(iter.hasNext());
        assertNull(iter.next());
    }

    // Testing List Iterator
    @Test
    void queueListIteratorTest() {
        q1.enqueue(7);

        ListIterator<Integer> iter = q1.listIterator(2);

        assertEquals(2, iter.nextIndex());
        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());

        assertEquals(3, iter.nextIndex());
        assertTrue(iter.hasNext());
        assertEquals(6, iter.next());

        assertEquals(4, iter.nextIndex());
        assertTrue(iter.hasNext());
        assertEquals(7, iter.next());

        assertFalse(iter.hasNext());
        assertTrue(iter.hasPrevious());
        assertEquals(5, iter.nextIndex());
        assertEquals(4, iter.previousIndex());
        assertEquals(7, iter.previous());

        assertEquals(3, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(6, iter.previous());

        assertEquals(2, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(5, iter.previous());

        assertEquals(1, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(4, iter.previous());

        assertEquals(0, iter.previousIndex());
        assertTrue(iter.hasPrevious());
        assertEquals(3, iter.previous());

        assertFalse(iter.hasPrevious());
    }

    // Testing with characters
    @Test
    void charTest() {
        GenericQueue<Character> q = new GenericQueue<>('a');

        assertEquals('a', q.dequeue());
        assertEquals(0, q.getLength());

        q.add('b');
        q.add('c');
        q.add('d');

        q.print();

        assertEquals('b', q.set(0, 'x'));
        assertEquals('c', q.set(1, 'y'));
        assertEquals('d', q.set(2, 'z'));
        assertEquals(3, q.getLength());

        assertEquals('x', q.delete());
        assertEquals('z', q.removeTail());

        assertEquals(1, q.getLength());
        assertEquals('y', q.get(0));
    }

    // Testing dumpArray
    @ParameterizedTest
    @ValueSource(strings = {"first", "second", "third", "fourth"})
    void queueParamTest(String str) {
        assertEquals(str, dumpedQueue.get(iter1));
        iter1++;
    }
}
