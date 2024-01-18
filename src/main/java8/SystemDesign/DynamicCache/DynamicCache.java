package SystemDesign.DynamicCache;




// following program just demonstrates how to switch between eviction policies such as LFU and LRU using the same cache data
// pros - following approach will be helpful when required to monitor how system performs when cache is set to a particular policy to determine what is more suitable






    // without removing any elements from cache we will be able to just transfer to different policy allowing flexibility.
//  // tc of both of approaches will be
//    - LRU get - O(1), put - O(1)
//    - LFU get - O(log n), put - O(1)
//
//    // Space Complexity -
//    - LRU O(n)
//    - LFU O(n) + O(k)     k - priority queue;

    // following approach follows design patterns and SOLID principles hence allowing developers to implement newer algorithms and provide additional policies
    // helpful for large datasets with minimal switching between policies

// cons - get operation of lru takes logarithmic time because removing element in priorityqueue
// though the below approach focuses on consistency it will be impacting system performance if millions of records will be in cache and policy will be switched every few minutes













import java.util.*;

class CacheNode{

    int k;
    int frequency;

    public CacheNode( int k) {
        this.k = k;
        this.frequency = 1;
    }

    public CacheNode(int k, int frequency){
        this.k = k;
        this.frequency = frequency;
    }
    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


}
abstract class EvictionPolicy{
    public abstract CacheNode get(Object k, GlobalCache globalCache, int defaultCapacity);
    public abstract void put(CacheNode cacheNode, GlobalCache globalCache, int defaultCapacity);

    public abstract boolean contains();
    public abstract void printCache(GlobalCache cache);
    public abstract String getPolicyname();
}

class LFU extends EvictionPolicy{

    @Override
    public CacheNode get(Object k, GlobalCache globalCache, int defaultCapacity) {
        return get(k, (LinkedHashMap<Object, CacheNode>) globalCache.getCache(), (PriorityQueue<CacheNode>) globalCache.getHelperCache());
    }

    public CacheNode get(Object k, LinkedHashMap<Object, CacheNode> cache, PriorityQueue<CacheNode> heap){
        if (cache.containsKey(k) == false){
            return null;
        }
        CacheNode fetchedNode = cache.get(k);
        cache.remove(k);

        heap.remove(fetchedNode);
        fetchedNode.setFrequency(fetchedNode.getFrequency()+1);
        heap.offer(fetchedNode);
        cache.put(k, fetchedNode);

//        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();
        return fetchedNode;
    }

    @Override
    public void put(CacheNode cacheNode, GlobalCache globalCache, int defaultCapacity){
        put(cacheNode, (LinkedHashMap<Object, CacheNode>) globalCache.getCache(), (PriorityQueue<CacheNode>) globalCache.getHelperCache(), defaultCapacity);
    }

    public void put(CacheNode node, LinkedHashMap<Object, CacheNode> cache, PriorityQueue<CacheNode> heap, int defaultCapacity) {

        if (cache.size() >= defaultCapacity){
            CacheNode removableNode = heap.poll();
            cache.remove(removableNode.getK());
        }
        cache.put(node.getK(), node);
        heap.offer(node);
//        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();
    }

    @Override
    public boolean contains() {
        return false;
    }


    @Override
    public void printCache(GlobalCache cache){
        printCache((LinkedHashMap<Object, CacheNode>) cache.getCache());
    }


    public void printCache(LinkedHashMap<Object, CacheNode> cache){
//        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();
    }

    public String getPolicyname(){
        return  " LFU policy";
    }


}


class LRU extends EvictionPolicy{

    @Override
    public CacheNode get(Object k, GlobalCache globalCache, int defaultCapacity){
        return get(k, (LinkedHashMap<Object, CacheNode>) globalCache.getCache(), defaultCapacity);
    }


    public CacheNode get(Object k, LinkedHashMap<Object, CacheNode> cache, int defaultCapacity) {
        CacheNode node = cache.get(k);
        cache.remove(k);
        node.setFrequency(node.getFrequency()+1);        // incrementing the frequency so that if there will be change in evictionPolicy to LFUCache then it helps consistency.
        put(node, cache, defaultCapacity);
//        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
//        System.out.println();

        return node;
    }

    @Override
    public void put(CacheNode cacheNode, GlobalCache globalCache, int defaultCapacity) {
        put(cacheNode, (LinkedHashMap<Object, CacheNode>)globalCache.getCache(), defaultCapacity);
    }

    public void put(CacheNode node, LinkedHashMap<Object, CacheNode> cache, int defaultCapacity){
        cache.put(node.getK(), node);
        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));

        System.out.println();
    }

    @Override
    public boolean contains() {
        return false;
    }

    @Override
    public void printCache(GlobalCache globalCache){
        printCache((LinkedHashMap<Object, CacheNode>) globalCache.getCache());
    }

    void printCache(LinkedHashMap<Object, CacheNode> cache){
        cache.forEach((k, v) -> System.out.println(k));
    }

    public String getPolicyname(){
        return  " LRU policy";
    }


}

abstract class StorageTransfer{
    public abstract Object convertDSBasedOnPolicy(int defaultCapacity, GlobalCache globalCache);
}

class LFU2LRU extends StorageTransfer{
    public Object convertDSBasedOnPolicy(int defaultCapacity, GlobalCache globalCache){
        LinkedHashMap cache = new LinkedHashMap<Object, CacheNode>(defaultCapacity,0.89f, true){
            protected boolean removeEldestEntry(Map.Entry<Object, CacheNode> eldest) {
                if (size() > defaultCapacity){
                    return true;
                }
                return false;
            }
        };

        if (globalCache.getCache() instanceof HashMap){
            LinkedHashMap<Object, CacheNode> currCache = (LinkedHashMap<Object, CacheNode>) globalCache.getCache();
            for(Map.Entry<Object, CacheNode> currElement: currCache.entrySet()){
                cache.put(currElement.getKey(), currElement.getValue());
            }

        }




        return cache;
    }
}

class LRU2LFU extends StorageTransfer{
    public ArrayList<Object> convertDSBasedOnPolicy(int defaultCapacity, GlobalCache globalCache){
        LinkedHashMap<Object, CacheNode> cache = new LinkedHashMap<Object, CacheNode>(defaultCapacity);
        PriorityQueue<CacheNode> heap = new PriorityQueue<CacheNode>((a, b) -> a.getFrequency() - b.getFrequency());

        if (globalCache.getCache() instanceof LinkedHashMap){
            LinkedHashMap<Object, CacheNode> currCache = (LinkedHashMap<Object, CacheNode>) globalCache.getCache();
            for(Map.Entry<Object, CacheNode> currElement: currCache.entrySet()){
                cache.put(currElement.getKey(), currElement.getValue());
                heap.offer(currElement.getValue());
            }

        }


        return new ArrayList<>(Arrays.asList(cache, heap));

    }
}

class CacheManager<T>{
    private EvictionPolicy evictionPolicy;
    static CacheManager cacheManager;
    static StorageTransfer storageTransfer;
    static GlobalCache globalCache;
    static int defaultCapacity;


    private CacheManager(){

    }


    public static CacheManager getInstance(){
        if (cacheManager == null){
            return new CacheManager();
        }
        return cacheManager;
    }

    public synchronized void setEvictionPolicy(EvictionPolicy evictionPolicy, GlobalCache globalCache, int defaultCapacity){
        this.evictionPolicy = evictionPolicy;
        this.globalCache = globalCache;
        this.defaultCapacity = defaultCapacity;
    }

    public synchronized void setEvictionPolicy(EvictionPolicy evicitionPolicy){
        this.evictionPolicy = evicitionPolicy;
//        System.out.println("this.eviction "+ this.evictionPolicy.getPolicyname() +" eviction "+evictionPolicy.getPolicyname());
    }

    public synchronized void updateEvictionPolicy(EvictionPolicy evictionPolicy){
        if (this.evictionPolicy instanceof LRU && evictionPolicy instanceof LFU){
            StorageTransfer storageTransfer = new LRU2LFU();
            ArrayList<Object> transferredCache = (ArrayList<Object>) storageTransfer.convertDSBasedOnPolicy(defaultCapacity,globalCache); // make sure to return helper also
            globalCache.setCache((LinkedHashMap<Object, CacheNode>) transferredCache.get(0),(PriorityQueue<CacheNode>) transferredCache.get(1));
            setEvictionPolicy(evictionPolicy);
        }
        else if (this.evictionPolicy instanceof LFU && evictionPolicy instanceof LRU){
            StorageTransfer storageTransfer = new LFU2LRU();
            LinkedHashMap<Object, CacheNode> transferredCache = (LinkedHashMap<Object, CacheNode>) storageTransfer.convertDSBasedOnPolicy(defaultCapacity, globalCache);
            globalCache.setCache(transferredCache);
            setEvictionPolicy(evictionPolicy);
        }
    }

    public synchronized void put(CacheNode cacheNode){
       this.evictionPolicy.put(cacheNode, globalCache, defaultCapacity);
    }


    public synchronized CacheNode get(Object k){
        return evictionPolicy.get(k, globalCache, defaultCapacity);
    }

    public void printCache(){
        evictionPolicy.printCache(globalCache);
    }

}

class GlobalCache{
    static GlobalCache globalCache;
    static Object cache;
    static Object helperCache;

    private GlobalCache() {

    }


    public static GlobalCache getInstance(){
        if (globalCache == null){
            return new GlobalCache();
        }
        return globalCache;
    }

    public Object getCache() {
        return cache;
    }

    public void setCache(LinkedHashMap<Object, CacheNode> cache, PriorityQueue<CacheNode> helperCache) {
        this.helperCache = helperCache;
        this.cache = cache;
    }

    public void setCache(LinkedHashMap<Object, CacheNode> cache){
        this.cache = cache;
        this.helperCache = null;
    }

    public Object getHelperCache() {
        return helperCache;
    }

    public void setHelperCache(Object helperCache) {
        GlobalCache.helperCache = helperCache;
    }
}



public class DynamicCache {
    public static void main(String[] args){

        CacheManager cacheManager = CacheManager.getInstance();
        int defaultCapacity = 5;

        GlobalCache globalCache = GlobalCache.getInstance();
//        System.out.println("Global Cache has been initiated ");
        globalCache.setCache(new LinkedHashMap<Object, CacheNode>(defaultCapacity), new PriorityQueue<CacheNode>((a, b) -> a.getFrequency() - b.getFrequency()) );
//        System.out.println("policy has been set to LFU");
        cacheManager.setEvictionPolicy(new LFU(), globalCache, 5);

//        globalCache.setCache(new LinkedHashMap<Object, CacheNode>(defaultCapacity,0.89f, true){
//            protected boolean removeEldestEntry(Map.Entry<Object,CacheNode> eldest) {
//                if (size() > defaultCapacity){
//                    return true;
//                }
//                return false;
//            }
//        }, null);
//        System.out.println("policy has been updated to LRU");
//        cacheManager.setEvictionPolicy(new LRU(), globalCache, 5);





        cacheManager.put(new CacheNode( 8));
        cacheManager.put(new CacheNode( 9));
        cacheManager.put(new CacheNode( 6));
        cacheManager.put(new CacheNode( 5));
        cacheManager.put(new CacheNode( 7));


//        System.out.println("Fetching the elements from cache");
        cacheManager.get(8);
        cacheManager.get(7);



        cacheManager.updateEvictionPolicy(new LRU());
        cacheManager.put(new CacheNode(0));
        cacheManager.put(new CacheNode(4));

        cacheManager.updateEvictionPolicy(new LFU());
        cacheManager.put(new CacheNode(1));
        cacheManager.put(new CacheNode(6));







    }
}






