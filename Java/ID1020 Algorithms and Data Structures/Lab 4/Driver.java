/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;
/**
 *
 * @author diego
 * http://www.gutenberg.org/cache/epub/98/pg98.txt
 */
import edu.princeton.cs.introcs.In;
import java.net.URL;

public class Driver {

    public static class valCompGreatest implements Comparator<Entry<String, Integer>> {
        @Override
        public int compare(Entry<String, Integer> val_1, Entry<String, Integer> val_2) {
            if(val_1.getValue() < val_2.getValue())
                return 1;
            else 
                return -1;   
        }
    }
    public static class valCompLowest implements Comparator<Entry<String, Integer>> {
        @Override
        public int compare(Entry<String, Integer> val_1, Entry<String, Integer> val_2) {
            if(val_2.getValue() < val_1.getValue())
                return 1;
            else 
                return -1;   
        }
    }
    public static void main(String[] args) {
        Trie trie = new Trie();
        URL url = ClassLoader.getSystemResource("kap1.txt");

        if (url != null) {
           System.out.println("Reading from: " + url);
        } else {
           System.out.println("Couldn't find file: kap1.txt");
        }

        In input = new In(url);

        while (!input.isEmpty()) {
           String line = input.readLine().trim();
           String[] words = line.split("(\\. )|:|,|;|!|\\?|( - )|--|(\' )| ");
           String lastOfLine = words[words.length - 1];

           if (lastOfLine.endsWith(".")) {
              words[words.length - 1] = lastOfLine.substring(0, lastOfLine.length() - 1);
           }

           for (String word : words) {
              String word2 = word.replaceAll("\"|\\(|\\)", "");
              // Remove and replace non-ascii chars
              word2 = word2.replaceAll("[^\\x00-\\x7F]", "");
              word2 = word2.toLowerCase();

              if (word2.isEmpty()) {
                 continue;
              }

              //System.out.println(word2);
              trie.put(word2);
              // Add the word to the trie
            }
        }
        //Perform analysis
        
        //1)
        PriorityQueue<Entry<String, Integer>> hFreq = new PriorityQueue<Entry<String, Integer>>(new valCompLowest());
        for(Entry<String, Integer> item : trie) {
            if(hFreq.size() < 10)
                hFreq.add(item);
            else if(item.getValue() > hFreq.peek().getValue()) {
                hFreq.poll();
                hFreq.add(item);
            }  
        }
        LinkedList<Entry<String, Integer>> hFreqO = new LinkedList<>(hFreq);
        hFreqO.sort(new valCompGreatest());
        
        System.out.println("The 10 most frequent words: ");
        for (Entry<String, Integer> item : hFreqO)
            System.out.println(item);
        System.out.println();
        //2)
        PriorityQueue<Entry<String, Integer>> lFreq = new PriorityQueue<Entry<String, Integer>>(new valCompGreatest());
        for(Entry<String, Integer> item : trie) {
            if(item.getValue() > 0 ) {
                if(lFreq.size() < 10)
                    lFreq.add(item);
                else if(lFreq.peek().getValue() > item.getValue()) {
                    lFreq.poll();
                    lFreq.add(item);
                }
            }
        }
        
        System.out.println("The 10 least frequent words: ");
        LinkedList<Entry<String, Integer>> lFreqO= new LinkedList<>(lFreq);
        lFreqO.sort(new valCompLowest());
        for (Entry<String, Integer> item : lFreqO)
            System.out.println(item);

        System.out.println();
        //3)
        Node chosen = null;
        int chosenCount = 0;
        Node tmp;
        for(Entry<String, Integer> item : trie) {
            if(item.getKey().length() == 2) {
                tmp = trie.getNode(item.getKey());
                if(tmp != null) {
                    if(chosen == null || trie.count(item.getKey()) > chosenCount) {
                        chosen = tmp;
                        chosenCount = trie.count(chosen.getKey());
                    }
                }
            }
        }
        System.out.println("Prefix of length 2 (highest frequency:)");
        System.out.printf("Prefix: %s, word frequency: %d\n", chosen.getKey(), chosenCount);
        System.out.println();
        //4)
        String key = null; 
        String disKey = null;
        int disValue = 0;
        for(int i = 0; i < Node.numbOfChildren; i++) {
            key = String.format("%s", (char)(i));
            if(disKey == null || trie.distinct(key) > disValue) {
                disKey = key;
                disValue = trie.distinct(key);
            }
        }
        System.out.println("Letter that most most different words start with:");
        System.out.printf("%s with %d different word starts.\n", disKey, disValue);
        
   }
}
