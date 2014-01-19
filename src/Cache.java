import java.util.HashMap;
import java.util.Map;

public class Cache<I, T> implements CacheRetriever<I,T> {

    private int maxSize;
    private CacheRetriever<I,T> retriever;

    private Map<I,KeyValueDoublyLinkedList<I,T>> cache;
    KeyValueDoublyLinkedList<I,T> policyHead;
    KeyValueDoublyLinkedList<I,T> policyTail;

    public Cache(int maxSize, CacheRetriever<I, T> retriever) {
        this.maxSize = maxSize;
        this.retriever = retriever;
        this.cache = new HashMap<I,KeyValueDoublyLinkedList<I,T>>(maxSize);
    }

    public T retrieve( I id ) {
        KeyValueDoublyLinkedList<I,T> element = cache.get(id);

        if (element != null) {

            if (!element.equals(policyTail)) {
                if (element.equals(policyHead)) {
                    policyHead = element.getNext();
                }

                element.extricate();
                policyTail.add(element);
                policyTail = element;
            }

        } else {

            // Expensive lookup
            T item = retriever.retrieve(id);

            element = new KeyValueDoublyLinkedList<I,T>( id, item );

            if (cache.size() == this.maxSize) {
                cache.remove(policyHead.getKey());
                if (policyHead.hasNext()) {
                    KeyValueDoublyLinkedList<I,T> newHead = policyHead.getNext();
                    newHead.removePrevious();
                    policyHead = newHead;
                } else {
                    // We're now empty, abandon the nodes
                    policyHead = null;
                    policyTail = null;
                }
            }

            cache.put( id, element );

            if (policyTail == null) {
                policyTail = element;
                policyHead = element; // We know we this isn't set either
            } else {
                policyTail.add(element);
                policyTail = element;
            }
        }

        return element.getValue();
    }

}