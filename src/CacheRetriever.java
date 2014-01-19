public interface CacheRetriever<I,T> {
    public T retrieve( I id );
}
