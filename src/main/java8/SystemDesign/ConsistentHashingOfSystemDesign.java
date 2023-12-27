package SystemDesign;


import org.w3c.dom.Node;

import java.util.*;
import java.util.stream.Collectors;

class Node1{
    String name;


    Node1(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class ConsistentHashingOfSystemDesign{
    public static void main(String[] args){

        Node1 n1 = new Node1("server1");
        Node1 n8 = new Node1("server8");
        Node1 n10 = new Node1("server10");
        Node1 n16 = new Node1("server16");

        SortedMap<Long, Node1> hashCircle = new TreeMap<>();

        List<Node1> nodes = new ArrayList(Arrays.asList(n1, n8, n10, n16));

        for(Node1 n : nodes){
            long h = n.getName().hashCode() % 10000;
            hashCircle.put(h, n);
        }

        String r1 = "request1";
        String r8 = "request2";

        hashCircle.forEach((k, v) -> System.out.println(k+" "+v.getName() )) ;

        routeRequestToRandomServer(r1, hashCircle);

    }

    public static void routeRequestToRandomServer(String r, SortedMap<Long, Node1> hashCircle){
        long m=Long.MAX_VALUE;
        String nodeName = "";

        long rH = r.hashCode() % 10000;
        System.out.println(rH);
        for(Map.Entry<Long, Node1> e : hashCircle.entrySet()){
            long tM = m;

//            System.out.println(e.getValue().getName()+" "+Math.abs(rH - e.getKey()));
            m= Math.min(m, Math.abs(rH - e.getKey()));

            if (tM != m){
                nodeName= e.getValue().getName();
            }
        }

        System.out.println(r+ " will be routed to "+nodeName );




    }
}