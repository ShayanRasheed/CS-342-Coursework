import java.util.Iterator;

public class GLLIterator<T> implements Iterator<T> {
    private GenericList.Node<T> curr;

    public GLLIterator(GenericList<T> list) {
        curr = list.getHead();
    }

    public boolean hasNext() {
        return curr != null;
    }

    public T next() {
        if(hasNext()) {
            T val = curr.data;
            curr = curr.next;
            return val;
        }
        return null;
    }
}
