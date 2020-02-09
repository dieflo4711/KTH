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
public class Node implements Iterable<Node> {
    public static int numbOfChildren = 256;
    private Node[] children = new Node[numbOfChildren];
    private Node parent;
    private char key;
    private int value;
    private boolean hasChildren = false;
    
    public Node(){}
    
    public Node(Node parent, char key){
        this.parent = parent;
        this.key = key;
    }
    
    public String getKey() {
        Node x = this;
        StringBuilder key = new StringBuilder();
        while(x.parent != null) {
            key.insert(0, x.key);
            x = x.parent;
        }
        return key.toString();
    }

    public Node addChild(int keyVal){
        if(children[keyVal] == null) {
            children[keyVal] = new Node(this, (char)keyVal);
            hasChildren = true;
        }
        return children[keyVal];
    }
    public boolean hasChildren() {
        return hasChildren;
    }
    public int toInt(String s) {
        return s.charAt(0);
    }
    public void incrementValue() {
        value++;
    }
    public int getValue() {
        return value;
    }
    public Node getChild(int key){
        return children[key];
    }
    @Override
    public Iterator<Node> iterator() {
        return new NodeIt(this);
    }
}
