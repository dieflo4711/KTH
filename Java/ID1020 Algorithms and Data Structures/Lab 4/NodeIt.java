/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Iterator;
/**
 *
 * @author diego
 */
public class NodeIt implements Iterator<Node>{
    private int curIdx = 0;
    private Node node;
    private Iterator<Node> currentN = null;
    
    public NodeIt(Node node) {
        this.node = node;
    }
    @Override
    public boolean hasNext() {
        if(currentN != null && currentN.hasNext())
            return true;
        
        for(int i = curIdx; i  < Node.numbOfChildren; i++)
            if(node.getChild(i) != null) 
                return true;

        return false;
    }
    @Override
    public Node next() {
        if (!node.hasChildren()) 
            throw new IndexOutOfBoundsException();
        
        if(currentN != null && currentN.hasNext())
            return currentN.next();
 
        for(;curIdx < node.numbOfChildren; curIdx++) {
            Node child = node.getChild(curIdx);
            if(child != null) {
                //System.out.println("has children! " + child.getKey());
                curIdx++;
                currentN = child.iterator();
                return child;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}


