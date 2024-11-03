package businessLogic;


import java.util.List;
import java.util.ListIterator;

public class ExtendedIterator2<T> implements ExtendedIterator<T> {
    private final ListIterator<T> iterator;

    public ExtendedIterator2(List<T> list) {
        this.iterator = list.listIterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public T previous() {
        return iterator.previous();
    }

    @Override
    public void goFirst() {
        while (iterator.hasPrevious()) {
            iterator.previous();
        }
    }

    @Override
    public void goLast() {
        while (iterator.hasNext()) {
            iterator.next();
        }
    }
}
