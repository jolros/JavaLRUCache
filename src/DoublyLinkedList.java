public class DoublyLinkedList<I,T> {

    private DoublyLinkedList<I,T> previous;
    private DoublyLinkedList<I,T> next;
    private T item;
    private I id;

    public DoublyLinkedList( I id, T item ) {
        this.id = id;
        this.item = item;
    }

    public DoublyLinkedList<I, T> getPrevious() {
        return previous;
    }

    public DoublyLinkedList<I, T> getNext() {
        return next;
    }

    public T getItem() {
        return item;
    }

    public I getId() {
        return id;
    }

    public void setPrevious(DoublyLinkedList<I, T> previous) {
        if (next.hasNext()) throw new IllegalArgumentException( "Element to add must be a tail" );
        this.previous = previous;
        previous.next = this;
    }

    public void setNext(DoublyLinkedList<I, T> next) {
        if (next.hasPrevious()) throw new IllegalArgumentException( "Element to add must be a head" );
        this.next = next;
        next.previous = this;
    }

    public void removeNext() {
        next = null;
    }

    public void removePrevious() {
        previous = null;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public void extricate() {
        DoublyLinkedList<I,T> before = this.previous;
        DoublyLinkedList<I,T> after = this.next;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((DoublyLinkedList) o).id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }
}