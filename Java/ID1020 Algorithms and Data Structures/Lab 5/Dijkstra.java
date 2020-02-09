/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import se.kth.id1020.Graph;
import se.kth.id1020.Edge;
import se.kth.id1020.Vertex;
import java.util.*;
/**
 *
 * @author diego
 */
public class Dijkstra {  
    public static List<Vertex> shortestPath(Graph g, String fromS, String toS, boolean withWeight) {
        Vertex from = null, to = null;
        for(Vertex v : g.vertices()) {
            if(from != null && to != null)
                break;
            if(v.label.equals(fromS))
                from = v;
            if(v.label.equals(toS))
                to = v;
        }
         
        double[] val = new double[g.numberOfVertices()];
        Vertex[] prevVert = new Vertex[g.numberOfVertices()];
        PriorityQueue<Map.Entry<Vertex, Double>> paths = new PriorityQueue<>(new CompEntry());
        
        val[from.id] = 0;
        for(Vertex v : g.vertices()) {
            if(v.id != from.id)
                val[v.id] = Double.MAX_VALUE;
            paths.add(new AbstractMap.SimpleEntry<>(v, val[v.id]));
        }
        while(!paths.isEmpty()) {
            Vertex currVert = paths.poll().getKey();
            for(Edge e : g.adj(currVert.id)) {
                double weight = val[currVert.id] + (withWeight ? e.weight : 1);
                if(Double.compare(weight, val[e.to]) < 0){
                    val[e.to] = weight;
                    prevVert[e.to] = currVert; 
                    paths.add(new AbstractMap.SimpleEntry<>(g.vertex(e.to), val[e.to]));
                }
            }
        }

        List<Vertex> shortestPath = new ArrayList();
        int tmp = to.id;
        shortestPath.add(to);
        while(prevVert[tmp] != null) {
            shortestPath.add(prevVert[tmp]);
            //prevVert[s] = null
            tmp = prevVert[tmp].id;
        }
        System.out.println("Path from " + fromS + " to " + toS +" (weight = " + withWeight + "): ");
        System.out.println("Val: " + val[to.id] + " Length: " + (shortestPath.size() - 1) + " Components: " + shortestPath.size());
        
        return shortestPath;
    }
    private static class CompEntry implements Comparator<Map.Entry<Vertex, Double>> {
        @Override
        public int compare(Map.Entry<Vertex, Double> o1, Map.Entry<Vertex, Double> o2) {
            return Double.compare(o1.getValue(), o2.getValue());
        }
    }
}
