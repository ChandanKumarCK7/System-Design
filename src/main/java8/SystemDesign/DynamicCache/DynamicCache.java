package SystemDesign.DynamicCache;




// basically tis just focuses on switching between multiple eviction policies based on the convience of admin to ensure flexibility
// starting off with LRU and LFU





class CacheNode{
    int v;

    public CacheNode(int v) {
        this.v = v;
    }
}
abstract class EvictionPolicy{
    public abstract CacheNode get();
    public abstract void put();

    public abstract boolean contains();
}

class LFU extends EvictionPolicy{

    @Override
    public CacheNode get() {
        return null;
    }

    @Override
    public void put() {

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

class LRU extends EvictionPolicy{

    @Override
    public CacheNode get() {
        return null;
    }

    @Override
    public void put() {

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

class CacheManager{
    static EvictionPolicy evictionPolicy;
    static Object cache;
    static Object helperCache;
    static CacheManager cacheManager;
    static StorageTransfer storageTransfer;

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
}
public class DynamicCache {
    public static void main(String[] args){

        CacheManager cacheManger = CacheManager.getInstance();
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
