import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public abstract class GenericList<T> implements Iterable<T>{
    // Two member variables: one to store head node and one to keep track of length of list
    private Node<T> head;
    private int length;

    // NODE CLASS
    // Represents one single node in the linked list
    static class Node<T> {
        // One node contains a value as well as a variable for the next and previous nodes
        T data;
        Node<T> next;
        Node<T> prev;

        // Node constructor: assigns a value to data
        public Node(T val) {
            data = val;
        }
    }

    GenericList() {
        length = 0;
    }

    // Abstract method add - to be implemented in subclasses
    abstract public void add(T data);

    // Delete function
    // Removes the head node and returns its value
    public T delete() {
        // Return null if list's head is null
        if(head == null) {
            return null;
        }
        T val = head.data;
        head = head.next;
        // Decrement length
        length--;
        return val;
    }

    // Print function
    // Prints out all values in the list
    public void print() {
        Node<T> curr = head;
        while(curr != null) {
            System.out.println(curr.data);
            curr = curr.next;
        }
    }

    // DumpList function
    // Adds all values from the linked list into an ArrayList
    // Also empties the list
    public ArrayList<T> dumpList() {
        ArrayList<T> list = new ArrayList<>();
        while(head != null) {
            list.add(this.delete());
        }
        length = 0;
        return list;
    }

    // Get Function
    // Retrieves the value from the list at a certain index
    // Returns null if index is not valid
    public T get(int index) {
        int i = 0;
        Node<T> curr = head;
        while(curr != null) {
            if(i == index) {
                return curr.data;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }

    // Set Function
    // Changes the value at a certain index in the list
    // Returns null if index is not valid
    public T set(int index, T element) {
        int i = 0;
        Node<T> curr = head;
        while(curr != null) {
            if(i == index) {
                T val = curr.data;
                curr.data = element;
                return val;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }

    // GETTERS AND SETTERS for head and length

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> node) {  head = node; }

    public int getLength() {
        return length;
    }

    protected void setLength(int length) {
        this.length = length;
    }

    // ITERATORS

    public Iterator<T> iterator() {
        return new GLLIterator<>(this);
    }

    public Iterator<T> descendingIterator() {
        return new ReverseGLLIterator<>(this);
    }

    public ListIterator<T> listIterator(int index) {
        return new GLListIterator<>(this, index);
    }


}
