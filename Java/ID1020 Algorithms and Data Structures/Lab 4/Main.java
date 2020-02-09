/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Map.Entry;
import java.util.Iterator;
/**
 *
 * @author diego
 */
public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie(); 
        
        trie.put("ab");
        trie.put("ac");
        trie.put("ac");
        trie.put("b");
        trie.put("bad");
        trie.put("baf");
        trie.put("baf");
        trie.put("baf");
        trie.put("bc");
        trie.put("bc");
        trie.put("bc");
        trie.put("bc");
        Iterator<Entry<String, Integer>> trieIt = trie.iterator("b");

        String[] items = {"a", "ab", "ac", "b", "ba", "bad", "baf", "bc"};
        /*for (String item : items)
            System.out.printf("%s: %d\n", item, trie.get(item));*/
        //System.out.println(trie.count("a"));
        //System.out.println("Distinct: " + trie.distinct("b"));
        
        /*for (Entry<String, Integer> item : trie)
            System.out.printf("(%s, %d)\n", item.getKey(), item.getValue());*/
        while(trieIt.hasNext()){
           Entry<String, Integer> entry = trieIt.next();
           System.out.printf("(%s, %d)\n", entry.getKey(), entry.getValue());
        }
        

    }
}
