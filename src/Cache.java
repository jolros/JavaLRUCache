import java.util.HashMap;
import java.util.Map;

public class Cache<I, T> {

    public static interface Retriever<I,T> {
        public T retrieve( I id );
    }

    private int maxSize;
    private Retriever<I,T> retriever;

    private Map<I,DoublyLinkedList<I,T>> cache;
    DoublyLinkedList<I,T> policyHead;
    DoublyLinkedList<I,T> policyTail;

    public Cache(int maxSize, Retriever<I, T> retriever) {
        this.maxSize = maxSize;
        this.retriever = retriever;
        this.cache = new HashMap<I,DoublyLinkedList<I,T>>(maxSize);
    }

    public T retrieve( I id ) {
        DoublyLinkedList<I,T> element = cache.get(id);

        if (element != null) {

            if (element.equals(policyHead)) {
                policyHead = element.getNext();
            }
            if (element.equals(policyTail)) {
                policyTail = element.getPrevious();
            }

            element.extricate();

        } else {

            // Expensive lookup
            T item = retriever.retrieve(id);

            element = new DoublyLinkedList<I,T>( id, item );

            if (cache.size() == this.maxSize) {
                cache.remove(policyHead.getId());
                if (policyHead.hasNext()) {
                    DoublyLinkedList<I,T> newHead = policyHead.getNext();
                    newHead.removePrevious();
                    policyHead = newHead;
                } else {
                    // We're now empty, abandon the nodes
                    policyHead = null;
                    policyTail = null;
                }
            }

            cache.put( id, element );
        }

        if (policyTail == null) {
            policyTail = element;
            policyHead = element; // We know we this isn't set either
        } else {
            policyTail.setNext(element);
            policyTail = element;
        }

        return element.getItem();
    }

}