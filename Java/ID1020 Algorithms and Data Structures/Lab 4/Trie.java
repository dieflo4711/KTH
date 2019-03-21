/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author diego
 */
public class Trie implements Iterable<Entry<String, Integer>> {
    private Node root;
    
    public Trie() {
        root = new Node();
    }
    
    public int toInt(String s) {
        return s.charAt(0);
    }
    
    public void put(String key){
        if(key == "")
            return;
        put(root, key);
    }
    private void put(Node x, String key) {
        int keyVal = toInt(key);
        if(key.length() <= 1)
            x.addChild(keyVal).incrementValue();
        else
            put(x.addChild(keyVal), key.substring(1));
    }
    public Node getNode(String key) {
        return getNode(root, key);
    }
    private Node getNode(Node x, String key) {
        if(key.length() < 1 || x == null)
            return x;
        return getNode(x.getChild(toInt(key)), key.substring(1));
    }
    
    public int get(String key) {
        Node x = getNode(key);
        
        if(x == null)
            return 0;
        return x.getValue();
    }
    
    public int count(String key) {
        Node x = getNode(key);
        return count(x);
    }
    private int count(Node x) {
        int count = x.getValue();
        Node xChild;
        for(int i = 0; i <x.numbOfChildren; i++) {
            xChild = x.getChild(i);
            if(xChild != null)
                count += count(xChild);
        }
        return count;    
    }
    
    public int distinct(String key) {
        Node x = getNode(key);
        return distinct(x);
    }
    private int distinct(Node x) {
        if(x == null)
            return 0;
        
        int count = x.getValue() > 0 ? 1 : 0;
        Node xChild;
        for(int i = 0; i <x.numbOfChildren; i++) {
            xChild = x.getChild(i);
            if(xChild != null)
                count += distinct(xChild);
        }
        return count;  
    }
    public Iterator<Entry<String, Integer>> iterator() {
        return new TrieIt(root);
    }
    public Iterator<Entry<String, Integer>> iterator(String k) {
        return new TrieIt(getNode(k));
    }
}
