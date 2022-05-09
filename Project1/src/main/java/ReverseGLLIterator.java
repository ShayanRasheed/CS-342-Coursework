import java.util.ArrayList;
import java.util.Iterator;

public class ReverseGLLIterator<T> implements Iterator<T> {
    private ArrayList<T> list;
    private int index;

    public ReverseGLLIterator(GenericList<T> list) {
        this.list = new ArrayList<>();
        for(T val : list) {
            this.list.add(val);
        }
        index = this.list.toArray().length-1;
    }

    public boolean hasNext() {
        return index != -1;
    }

    public T next() {
        if(hasNext()) {
            T val = list.get(index);
            index -= 1;
            return val;
        }
        return null;
    }
}
