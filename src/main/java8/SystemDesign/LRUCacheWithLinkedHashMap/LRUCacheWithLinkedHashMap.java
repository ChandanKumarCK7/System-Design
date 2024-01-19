package SystemDesign.LRUCacheWithLinkedHashMap;



import org.w3c.dom.Node;


import java.util.*;
import java.util.stream.Collectors;




class CacheNode{

    int k;
    int frequency; // not required for LRUCache though in cases of switching evictionPolicy, it could be helpful

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
    static LinkedHashMap<Object, CacheNode> cache;
//    static HashMap<Object, >

    private CacheManager(){

    }

    static CacheManager getInstance(){
        if (cacheManager == null){
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    public CacheNode get(Integer k){
        CacheNode node = cache.get(k);
        node.setFrequency(node.getFrequency()+1);        // incrementing the frequency so that if there will be change in evictionPolicy to LFUCache then it helps consistency.

        cache.forEach((o, cacheNode) -> System.out.printf("key %d frequency %d ", o, cacheNode.getFrequency()));
        System.out.println();

        return node;

    }

    public synchronized void put(CacheNode node){
        cache.put(node.getK(), node);
    }

    public void storeCache(LinkedHashMap<Object, CacheNode> cache){
        this.cache= cache;

    }

    public void createCache(int defaultCapacity){
        this.cache = new LinkedHashMap<Object, CacheNode>(defaultCapacity,0.89f, true){
            protected boolean removeEldestEntry(Map.Entry<Object, CacheNode> eldest) {
                if (size() > defaultCapacity){
                    return true;
                }
                return false;
            }
        };


    }
    void printCache(){
        cache.forEach((k, v) -> System.out.println(k));
    }

}
public class LRUCacheWithLinkedHashMap {
    public static void main(String [] args){




        int defaultCapacity=5;
        CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.createCache(defaultCapacity);
        cacheManager.put(new CacheNode( 8));
        cacheManager.put(new CacheNode( 9));
        cacheManager.put(new CacheNode( 6));
        cacheManager.put(new CacheNode( 5));
        cacheManager.put(new CacheNode( 7));


        cacheManager.get(5);



        cacheManager.put(new CacheNode(2));
        cacheManager.put(new CacheNode(4));
        cacheManager.printCache();

    }




}
