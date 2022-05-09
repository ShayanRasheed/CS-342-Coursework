public class GenericStack<T> extends GenericList<T> {
    // Variable for tail of the linked list
    private Node<T> tail;

    // Constructor for Stack
    // One parameter - value for first node
    public GenericStack(T data) {
        Node<T> newNode = new Node<>(data);
        this.setHead(newNode);
        tail = this.getHead();
        this.setLength(1);
    }

    // RemoveTail Function
    // Removes the last node in the linked list
    public T removeTail() {
        // Return null if the tail is null
        if(tail == null) {
            return null;
        }

        Node<T> removed = tail;
        if(tail.prev != null) {
            tail = tail.prev;
        } else {
            // If there's only one node in the list, set head and tail to null
            setHead(null);
            tail = null;
        }
        // Decrement length
        this.setLength(getLength() - 1);
        return removed.data;
    }

    // add Function
    // Adds a new node to the front of the list
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        // Update next and prev variables if head isn't null
        if(this.getHead() != null) {
            newNode.next = this.getHead();
            this.getHead().prev = newNode;
        }
        if(getLength() == 0) {
            tail = newNode;
        }
        this.setHead(newNode);
        this.setLength(getLength() + 1);
    }

    // Push function
    // Calls the add function
    public void push(T data) {
        this.add(data);
    }

    // Pop function
    // Calls the delete function
    public T pop() {
        if(getLength() == 1) {
            tail = null;
        }
        return this.delete();
    }
}
