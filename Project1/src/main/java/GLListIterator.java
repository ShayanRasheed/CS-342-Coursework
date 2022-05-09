import java.util.ArrayList;
import java.util.ListIterator;

public class GLListIterator<T> implements ListIterator<T> {
    private GenericList.Node<T> curr;
    private ArrayList<T> list;
    private int index;

    public GLListIterator(GenericList<T> list, int ind) {
        this.list = new ArrayList<>();
        for(T val : list) {
            this.list.add(val);
        }

        if(ind >= 0 && ind < list.getLength()) {
            this.index = ind;
        } else {
            this.index = 0;
        }
    }

    public boolean hasNext() {
        return index < list.size();
    }

    public boolean hasPrevious() {
        if(list.isEmpty()) {
            return false;
        }
        else return index != 0;
    }

    public T next() {
        if(hasNext()) {
            T val = list.get(index);
            index++;
            return val;
        }
        return null;
    }

    public T previous() {
        if(hasPrevious()) {
            T val = list.get(index-1);
            index--;
            return val;
        }
        return null;
    }

    public int nextIndex() {
        return index;
    }

    public int previousIndex() {
        return index - 1;
    }

    public void add(T val) {

    }

    public void remove() {

    }

    public void set(T val) {

    }
}
