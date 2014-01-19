public abstract class AbstractDoublyLinkedList<T extends AbstractDoublyLinkedList> {

    private T previous;
    private T next;

    public T getPrevious() {
        return previous;
    }

    public T getNext() {
        return next;
    }

    public void replacePrevious(T newPrevious) {
        if (newPrevious.hasNext()) throw new IllegalArgumentException( "Element to add must be a tail" );
        this.previous = newPrevious;
        newPrevious.setNextIsolated(this);
    }

    public void replaceNext(T newNext) {
        if (newNext.hasPrevious()) throw new IllegalArgumentException( "Element to add must be a head" );
        this.next = newNext;
        newNext.setPreviousIsolated( this );
    }
     public void setPreviousIsolated(T previous) {
        this.previous = previous;
    }

    public void setNextIsolated(T next) {
        this.next = next;
    }

    public void removeNext() {
        if (hasNext()) {
            getNext().extricate();
        }
    }

    public void removeNextIsolated() {
        next = null;
    }

    public void removePrevious() {
        if (hasPrevious()) {
            getPrevious().extricate();
        }
    }

    public void removePreviousIsolated() {
        previous = null;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public void add( T nextNode ) {
        AbstractDoublyLinkedList<T> node = this;
        while (node.hasNext()) node = node.getNext();
        node.replaceNext(nextNode);
    }

    public void extricate() {
        AbstractDoublyLinkedList before = this.previous;
        AbstractDoublyLinkedList after = this.next;

        // Remove it from the list
        if (before != null) {
            before.next = after;
        }
        if (after != null) {
            after.previous = before;
        }

        this.next = null;
        this.previous = null;
    }

    public int size() {
        return 1 + sizeBefore() + sizeAfter();
    }

    public int sizeBefore() {
        int size = 0;
        AbstractDoublyLinkedList parent = this.previous;
        while (parent != null) {
            parent = parent.previous;
            size++;
        }
        return size;
    }

    public int sizeAfter() {
        int size = 0;
        AbstractDoublyLinkedList child = this.next;
        while (child != null) {
            child = child.next;
            size++;
        }
        return size;
    }
}