public class GenericQueue<T> extends GenericList<T> {
    // Variable for tail of the linked list
    private Node<T> tail;

    // Constructor for Queue
    // One parameter - value for first node
    public GenericQueue(T data) {
        Node<T> newNode = new Node<>(data);
        this.setHead(newNode);
        tail = this.getHead();
        this.setLength(1);
    }

    // RemoveTail Function
    // Removes the last node in the linked list
    public T removeTail() {
        if(tail == null) {
            return null;
        }

        Node<T> removed = tail;
        if(tail.prev != null) {
            tail = tail.prev;
        } else {
            setHead(null);
            tail = null;
        }
        this.setLength(getLength() - 1);
        return removed.data;
    }

    // add Function
    // Adds a new node to the end of the list
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        // Update next and prev variables if tail isn't null
        if(tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
        }
        if(getLength() == 0) {
            this.setHead(newNode);
        }
        tail = newNode;
        this.setLength(getLength() + 1);
    }

    // Enqueue function
    // Calls the add function
    public void enqueue(T data) {
        this.add(data);
    }

    // Dequeue function
    // Calls the delete function
    public T dequeue() {
        if(getLength() == 1) {
            tail = null;
        }
        return this.delete();
    }
}
