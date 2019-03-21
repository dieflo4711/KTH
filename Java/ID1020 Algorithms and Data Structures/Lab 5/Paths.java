/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;
import se.kth.id1020.Vertex;
/**
 *
 * @author diego
 */


public class Paths {
    public static void main(String[] args) {
        Graph g = DataSource.load();
        String from = "Renyn", to = "Parses";
        boolean weight = true;
        
        DFS dfs = new DFS(g);
        int subGraphs = dfs.getSubGraphs();
        if(subGraphs == 1)
            System.out.println("Graph g is Connected!");
        else
            System.out.println("Graph g has " + subGraphs + " subgraphs");
        
       
        List<Vertex> shortestPath = Dijkstra.shortestPath(g, from, to, weight);
        
        for(Vertex v : shortestPath)
            System.out.println(v.label);

    }
}
