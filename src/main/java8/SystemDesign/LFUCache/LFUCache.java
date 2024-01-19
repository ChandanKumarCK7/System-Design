package SystemDesign.LFUCache;



import org.w3c.dom.Node;


import java.util.*;


class CacheNode{

    int k;
    int frequency;

    public CacheNode( int k) {
        this.k = k;
        this.frequency = 1;
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

class CacheManager{

    //    cache;
    static CacheManager cacheManager;
    static PriorityQueue<CacheNode> heap;    // sort the queue based on descending order of frequency;
    static HashMap<Object, CacheNode> cache; // Object - key, CacheNode - location
    static int defaultCapacity;

    private CacheManager(){

    }

    static CacheManager getInstance(){
        if (cacheManager == null){
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    public CacheNode get(Object k){
        if (cache.containsKey(k) == false){
            return null;
        }
        CacheNode fetchedNode = cache.get(k);
        heap.remove(fetchedNode);
        fetchedNode.setFrequency(fetchedNode.getFrequency()+1);
        heap.offer(fetchedNode);
        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();
        return fetchedNode;
    }

    public synchronized void put(CacheNode node){
        if (cache == null){
            createCache(defaultCapacity);
        }
        if (cache.size() >= defaultCapacity){
            CacheNode removableNode = heap.poll();
            cache.remove(removableNode.getK());
        }
        cache.put(node.getK(), node);
        heap.offer(node);
        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();
    }

    public void createCache(int defaultCapacity){
        heap = new PriorityQueue<>((a, b) -> a.getFrequency() - b.getFrequency());
        cache = new HashMap<>(defaultCapacity);
        this.defaultCapacity = defaultCapacity;
    }

    public void printCache(){
        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();
    }

}
public class LFUCache {
    public static void main(String [] args){




        int defaultCapacity = 5;




        // if all of them have been accessed once then remove the one that entered first
        // else find the element that was accessed least and remove that



        CacheManager cacheManager= CacheManager.getInstance();
        cacheManager.createCache(defaultCapacity);
        cacheManager.put(new CacheNode( 8));
        cacheManager.put(new CacheNode( 9));
        cacheManager.put(new CacheNode( 6));
        cacheManager.put(new CacheNode( 5));
        cacheManager.put(new CacheNode( 7));

//        cacheManager.printCache();
        cacheManager.get(8);
        cacheManager.get(7);
        cacheManager.put(new CacheNode(0));
        cacheManager.put(new CacheNode(4));
//        cacheManager.printCache();

    }




}
