public class KeyValueDoublyLinkedList<I,T> extends AbstractDoublyLinkedList<KeyValueDoublyLinkedList<I,T>> {

    private I key;
    private T value;

    public KeyValueDoublyLinkedList(I key, T value) {
        super();
        this.key = key;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public I getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return key.equals(((KeyValueDoublyLinkedList) o).key);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        return result;
    }
}