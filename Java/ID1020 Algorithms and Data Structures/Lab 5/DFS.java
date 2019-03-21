/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import se.kth.id1020.Graph;
import se.kth.id1020.Edge;
import java.util.*;
/**
 *
 * @author diego
 */
public class DFS {
    public static Graph g;
    private static int subGraphs = 0;
    
    public DFS(Graph g) {
        this.g = g;
    }
    
    public static int getSubGraphs() {
        boolean[] visited = new boolean[g.numberOfVertices()];
        for(int i = 0; i < g.numberOfVertices(); i++) {
            if(visited[i] != true) {
                subGraphs++;
                visitDFS(visited, i);
            }
        }
        return subGraphs;
    }
    private static void visitDFS(boolean[] visited, int i) {
        visited[i] = true;
        for(Edge e : g.adj(i)) 
            if(visited[e.to] != true) 
                visitDFS(visited, e.to);    
    }
}
