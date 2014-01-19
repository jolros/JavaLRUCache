# LRU Cache

Some code I did while studying for tech interviews. It *should* be able to do all cache operations in *O(1)* time.

Some of this depends on the efficiency of the underlying `HashMap` data structure, which makes up half of the magic.

Usage:

```java
public static class UserRetriever implements Cache.Retriever<Long, User> {
        
    public User retrieve( Long id ) {
        // Expensive operation
    }
}
    
public void viewUser( long userId  ) {
    UserRetriever retriever = new UserRetriever();
    Cache<Long,String> cache = new Cache<Long,String>( 50, retriever);
    User user = cache.retrieve( userId );
    render(user);
}
```

This isn't intended to replace or augment a production data retrieval layer, it's just a thought exercise.
