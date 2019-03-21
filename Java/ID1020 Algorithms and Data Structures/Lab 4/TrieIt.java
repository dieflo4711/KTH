/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 *
 * @author diego
 */
public class TrieIt implements Iterator<Entry<String, Integer>>{
    private Node firstN;
    private Iterator<Node> itN;
    private boolean firstNstarted = false;
    
    public TrieIt(Node firstN) {
        this.firstN = firstN;
        
        itN = firstN.iterator();
    }
    @Override
    public boolean hasNext() {
        return itN.hasNext() || !firstNstarted;
    }
    @Override
    public Entry<String, Integer> next() {
        if(!firstNstarted) {
            firstNstarted = true;
            return new SimpleImmutableEntry<>(firstN.getKey(), firstN.getValue());
        } else {
            Node nextN = itN.next();
            return new SimpleImmutableEntry<>(nextN.getKey(), nextN.getValue());
        }
        
    }
}
