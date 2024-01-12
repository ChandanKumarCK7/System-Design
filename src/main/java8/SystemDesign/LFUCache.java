package SystemDesign;



import org.w3c.dom.Node;


import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;




class CacheNode{

    int v;

    public CacheNode( int v) {

        this.v = v;
    }


    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}

class CacheManager{

//    cache;
    static CacheManager cacheManager;
    static Deque<CacheNode> cache;
    static int defaultCapacity = 5;
    static HashSet<Integer> hashSet;

    private CacheManager(){

    }

    static CacheManager getInstance(){
        if (cacheManager == null){
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    public Node get(){
        return null;
    }

    public synchronized void put(CacheNode node){
        if (cache.size() == defaultCapacity && hashSet.contains(node.getV()) == false){
            cache.removeFirst();
        }
        if(hashSet.contains(node.getV())){
            Optional<CacheNode> filterNode = cache.stream().filter(c-> node.getV() == c.getV()).findAny();
            if (filterNode.isPresent()){
                System.out.println("value present that has to be inserted at last is "+filterNode.get().getV());
                cache.remove(filterNode.get());
                hashSet.remove(node.getV());
            }
        }
        hashSet.add(node.getV());
        cache.addLast(node);

    }

    public void storeCache(Deque<CacheNode> cache){
        this.cache= cache;
        hashSet = new HashSet<>();
        hashSet.addAll(cache.stream().map(c -> c.getV()).collect(Collectors.toSet()));
    }

    public void printCache(){
        cache.stream().forEach(c->System.out.println(c.getV()));
    }

}
public class LFUCache {
    public static void main(String [] args){





        Deque<CacheNode> cache = new LinkedList<>();
        cache.offer(new CacheNode( 8));
        cache.offer(new CacheNode( 9));
        cache.offer(new CacheNode( 6));
        cache.offer(new CacheNode( 5));
        cache.offer(new CacheNode( 7));



        CacheManager cacheManager= CacheManager.getInstance();
        cacheManager.storeCache(cache);
        cacheManager.printCache();
        cacheManager.put(new CacheNode(8));
        cacheManager.put(new CacheNode(7));
        cacheManager.printCache();
    }




}
