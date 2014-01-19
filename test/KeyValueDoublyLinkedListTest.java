import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyValueDoublyLinkedListTest {

    @Test
    public void testValues() {
        KeyValueDoublyLinkedList<Integer,String> list = makeList( 5, 6, 19, 50 );

        // Values
        assertArrayEquals( new int[]{5, 6, 19, 50}, getKeys( list ) );
        assertEquals( 5, list.getKey().intValue() );
        assertEquals( 6, list.getNext().getKey().intValue() );
        assertEquals( 19, list.getNext().getNext().getKey().intValue() );
        assertEquals( 50, list.getNext().getNext().getNext().getKey().intValue() );

    }

    @Test
    public void testSizes() {
        KeyValueDoublyLinkedList<Integer,String> list = makeList( 5, 6, 19, 50 );

        // Sizes
        assertEquals( 4, list.size() );
        assertEquals( 0, list.sizeBefore() );
        assertEquals( 3, list.sizeAfter() );

        assertEquals( 4, list.getNext().size() );
        assertEquals( 1, list.getNext().sizeBefore() );
        assertEquals( 2, list.getNext().sizeAfter() );

        assertEquals( 4, list.getNext().getNext().size() );
        assertEquals( 2, list.getNext().getNext().sizeBefore() );
        assertEquals( 1, list.getNext().getNext().sizeAfter() );

        assertEquals( 4, list.getNext().getNext().getNext().size() );
        assertEquals( 3, list.getNext().getNext().getNext().sizeBefore() );
        assertEquals( 0, list.getNext().getNext().getNext().sizeAfter() );

    }

    @Test
    public void testBoundaries() {
        KeyValueDoublyLinkedList<Integer,String> list = makeList( 5, 6, 19, 50 );

        // Boundaries
        assertNull( list.getPrevious() );
        assertNull( list.getNext().getPrevious().getPrevious() );
        assertNull(list.getNext().getNext().getNext().getNext());
        assertTrue(list.hasNext());
        assertTrue(list.getNext().hasNext());
        assertTrue(list.getNext().getNext().hasNext());
        assertFalse(list.getNext().getNext().getNext().hasNext());
        assertFalse(list.hasPrevious());
        assertTrue(list.getNext().hasPrevious());
        assertTrue(list.getNext().getNext().hasPrevious());
        assertTrue(list.getNext().getNext().getNext().hasPrevious());

    }

    @Test
    public void testEquality() {
        KeyValueDoublyLinkedList<Integer,String> list = makeList( 5, 6, 19, 50 );

        // Equality
        assertEquals( list, new KeyValueDoublyLinkedList<Integer,String>( 5, "Number5" ));
        assertEquals( list.hashCode(), new KeyValueDoublyLinkedList<Integer,String>( 5, "Number5" ).hashCode());
        assertNotEquals(list, new KeyValueDoublyLinkedList<Integer, String>(4, "Number4"));
        assertNotEquals(list.hashCode(), new KeyValueDoublyLinkedList<Integer, String>(4, "Number4").hashCode());
        // We only care about keys
        assertEquals( list, new KeyValueDoublyLinkedList<Integer,String>( 5, "Cheese" ));
        assertEquals( list.hashCode(), new KeyValueDoublyLinkedList<Integer,String>( 5, "Cheese" ).hashCode());
        assertNotEquals(list, new KeyValueDoublyLinkedList<Integer, String>(4, "Number5"));
        assertNotEquals(list.hashCode(), new KeyValueDoublyLinkedList<Integer, String>(4, "Number5").hashCode());

    }

    @Test
    public void testManipulation() {
        KeyValueDoublyLinkedList<Integer,String> list = makeList( 5, 6, 19, 50 );

        // Manipulation

        list.add(new KeyValueDoublyLinkedList<Integer, String>(27, "Number27"));
        assertEquals(5, list.size());
        assertArrayEquals( new int[]{5, 6, 19, 50, 27}, getKeys( list ) );

        KeyValueDoublyLinkedList<Integer,String> middle = list.getNext().getNext();
        middle.extricate();
        assertEquals( 4, list.size() );
        assertArrayEquals(new int[]{5, 6, 50, 27}, getKeys(list));

        list.getNext().getNext().removePrevious();
        assertEquals( 3, list.size() );
        assertArrayEquals(new int[]{5, 50, 27}, getKeys(list));

        list.removeNext();
        assertEquals( 2, list.size() );
        assertArrayEquals(new int[]{5, 27}, getKeys(list));

        list.replaceNext( new KeyValueDoublyLinkedList<Integer, String>(88, "Number88"));
        assertEquals( 2, list.size() );
        assertArrayEquals(new int[]{5, 88}, getKeys(list));

        list.replacePrevious( new KeyValueDoublyLinkedList<Integer, String>(99, "Number99"));
        list = list.getPrevious();
        assertEquals( 3, list.size() );
        assertArrayEquals(new int[]{99, 5, 88}, getKeys(list));
    }

    private static KeyValueDoublyLinkedList<Integer,String> makeList( Number... numbers ) {
        KeyValueDoublyLinkedList<Integer,String> head = null;
        KeyValueDoublyLinkedList<Integer,String> last = null;
        for (Number number : numbers) {
            Integer key = number.intValue();
            String value = "Number" + key.toString();
            KeyValueDoublyLinkedList<Integer,String> node = new KeyValueDoublyLinkedList<Integer,String>( key, value );

            if (last != null) last.add(node);
            if (head == null) head = node;
            last = node;
        }
        return head;
    }

    private static void dumpKeys( KeyValueDoublyLinkedList<Integer,String> list ) {
        System.out.println(StringUtils.join(getKeys(list), ',' ));
    }

    private static int[] getKeys( KeyValueDoublyLinkedList<Integer,String> list ) {
        KeyValueDoublyLinkedList<Integer,String> node = list;
        while (node.hasPrevious()) node = node.getPrevious();

        int size = node.size();
        int[] values = new int[ size ];

        for (int i = 0; i < size; i++) {
            values[i] = node.getKey();
            node = node.getNext();
        }

        return values;
    }
}