import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CacheTest {

    public static class RetrieveInspector implements Cache.Retriever<Long,String> {
        List<Long> log = new ArrayList<Long>();
        public String retrieve( Long id ) {
            log.add(id);
            return "Number"+ id;
        }
        public int getNumberOfRetrieves() {
            return log.size();
        }
        public void reset() { log.clear(); }
    }

    @Test
    public void testCache() {

        Integer[] plan = new Integer[] {
                5, 15, 5, 25, 10, 11, 95, 95, 95, 10
        };
        Set<Integer> uniques = new HashSet<Integer>(Arrays.asList(plan));
        RetrieveInspector inspector = new RetrieveInspector();

        Cache<Long,String> cache;

        cache = new Cache<Long,String>( 5, inspector);
        for (Integer num : plan) cache.retrieve( new Long( num ) );
        assertEquals( 6, inspector.getNumberOfRetrieves() );
        inspector.reset();

        cache = new Cache<Long,String>( 1, inspector);
        for (Integer num : plan) cache.retrieve( new Long( num ) );
        assertEquals( 8, inspector.getNumberOfRetrieves() );
        inspector.reset();
    }
}