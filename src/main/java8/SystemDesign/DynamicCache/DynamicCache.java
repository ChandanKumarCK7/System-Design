package SystemDesign.DynamicCache;




// basically tis just focuses on switching between multiple eviction policies based on the convience of admin to ensure flexibility
// starting off with LRU and LFU


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

class CacheNode<T>{
    T v;

    public CacheNode(T v) {
        this.v = v;
    }
}
abstract class EvictionPolicy<T>{
    public abstract CacheNode<T> get();
    public abstract void put(CacheNode<T> cacheNode);

    public abstract boolean contains();
}

class LFU<T> extends EvictionPolicy<T{

    @Override
    public CacheNode get() {
        return null;
    }

    @Override
    public void put(CacheNode<T> cacheNode) {

    }

    @Override
    public boolean contains() {
        return false;
    }


    public Object constructCacheBasedOnEvictionPolicy(){

    }

    public Object constructHelperForCache(){

    }
}

class LRU<T> extends EvictionPolicy<T>{

    @Override
    public CacheNode get() {
        return null;
    }

    @Override
    public void put(CacheNode<T> cacheNode) {

    }

    @Override
    public boolean contains() {
        return false;
    }

    public Object constructCacheBasedOnEvictionPolicy(){

    }

    public Object constructHelperForCache(){

    }
}

abstract class StorageTransfer{
    public abstract Object convertDSBasedOnPolicy();
}

class LFU2LRU extends StorageTransfer{
    public Object convertDSBasedOnPolicy(){
        return new Object();
    }
}

class LRU2LFU extends StorageTransfer{
    public Object convertDSBasedOnPolicy(){
        return new Object();
    }
}

class CacheManager<T>{
    private EvictionPolicy<T> evictionPolicy;
    static Object helperCache;
    static CacheManager cacheManager;
    static StorageTransfer storageTransfer;
    private CacheNode<T> cacheNode;


    private CacheManager(){

    }


    public static CacheManager getInstance(){
        if (cacheManager == null){
            return new CacheManager();
        }
        return cacheManager;
    }

    public void setEvictionPolicy(EvictionPolicy evictionPolicy){
        this.evictionPolicy = evictionPolicy;
        this.cache = evictionPolicy.constructCacheBasedOnEvictionPolicy();
        this.helperCache = evictionPolicy.constructHelperForCache();
    }

    public void updateEvictionPolicy(EvictionPolicy evictionPolicy){
        if (this.evictionPolicy instanceof LRU && evictionPolicy instanceof LFU){
            StorageTransfer storageTransfer = new LRU2LFU();
            storageTransfer.convertDSBasedOnPolicy();
            this.setEvictionPolicy(evictionPolicy);
        }
        else if (this.evictionPolicy instanceof LFU && evictionPolicy instanceof LRU){
            StorageTransfer storageTransfer = new LFU2LRU();
            storageTransfer.convertDSBasedOnPolicy();
            this.setEvictionPolicy(evictionPolicy);
        }
    }

    public void put(CacheNode<T> cacheNode){
        evictionPolicy.put(cacheNode);
    }
}

class GlobalCache<T>{
    static Object cache;

    public GlobalCache(Deque<CacheNode<T>> cache) {
        this.cache = cache;
    }

    public GlobalCache(HashMap<>){
        
    }
}

public class DynamicCache {
    public static void main(String[] args){

        CacheManager cacheManger = CacheManager.getInstance();
        GlobalCache globalCache = new GlobalCache(new LinkedList<>());
        cacheManger.setEvictionPolicy(new LFU());

        cacheManger.setLimit(5);
        cacheManger.put(new CacheNode(5));
        cacheManger.put(new CacheNode(1));
        cacheManger.put(new CacheNode(9));
        cacheManger.put(new CacheNode(8));
        cacheManger.put(new CacheNode(6));

        cacheManger.put(new CacheNode(7));
        cacheManger.put(new CacheNode(8));

        cacheManger.printCache();

        cacheManger.updateEvictionPolicy(new LRU());

        cacheManger.printCache();

        cacheManger.put(new CacheNode(7));
        cacheManger.put(new CacheNode(8));





    }
}
