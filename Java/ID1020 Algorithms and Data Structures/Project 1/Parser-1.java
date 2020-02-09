/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import java.util.Map.Entry;
import java.util.*;
/**
 *
 * @author diego
 */
public class Parser {
    private static boolean valid(String[] words) throws Exception {
        if(words.length < 4)
            return false;
        //Check if "orderby comes before porperty and direction"
        if(words[words.length - 3].compareToIgnoreCase("orderby") == 0) {
            //check if property key 
            if(!words[words.length - 2].equals("count") && !words[words.length - 2].equals("popularity") && !words[words.length - 2].equals("occurrence"))
                throw new Exception("Invalid porperty or no property found");
            //check if direction key 
            if(!words[words.length - 1].equals("asc") && !words[words.length - 1].equals("desc"))
                throw new Exception("Invalid direction or no direction found");
            return true;
        } 
        return false;
            
    }
    public static List<Document> parse(String query, Indexing idxIng) throws Exception {
        String[] words = query.split("\\s+");;
        ArrayList<Attributes> attrArr = new ArrayList<>();
        
        for(String w : words) {
            if(w.compareToIgnoreCase("orderby") == 0)
                break;
            
            attrArr.addAll(idxIng.search(w));
        }
        
        if(valid(words)) {
            String prop = words[words.length - 2];
            Boolean asc = words[words.length - 1].equals("asc"); 
            
            if(prop.equals("count"))
                return orderByCount(attrArr, asc);
            else if(prop.equals("popularity"))
                BubbleSort.sort(asc ? new sortByPop() : new sortByPop().reversed(), attrArr);
            else if(prop.equals("occurrence"))
                BubbleSort.sort(asc ? new sortByOc() : new sortByOc().reversed(), attrArr);
        }
        ArrayList<Document> docArr = new ArrayList<>(attrArr.size());
        for(Attributes attr : attrArr) 
            if(!docArr.contains(attr.document))
                docArr.add(attr.document);
            
        return docArr;
    }
    
    private static List<Document> orderByCount(ArrayList<Attributes> list, boolean asc) {
        List<Entry<Document, Integer>> countList = new ArrayList();
        boolean exists = false;
        for(Attributes attr : list) {
            exists = false;
            for(Entry<Document, Integer> entry : countList) {
                if(entry.getKey().equals(attr.document)) {
                    entry.setValue(entry.getValue() + 1);
                    exists = true;
                    break;
                }
            }
            if(!exists)
                countList.add(new AbstractMap.SimpleEntry<>(attr.document, 1));
        }
        BubbleSort.sort(asc ? new sortByCount() : new sortByCount().reversed(), countList);

        List<Document> docList = new ArrayList<>();

        for(Entry<Document, Integer> entry : countList)
            if(!docList.contains(entry.getKey()))
                docList.add(entry.getKey());

        return docList;
    }
    public static class sortByPop implements Comparator<Attributes> {
        @Override
        public int compare(Attributes val_1, Attributes val_2) {
            return val_1.document.popularity - val_2.document.popularity;
        }
    }
    public static class sortByOc implements Comparator<Attributes> {
        @Override
        public int compare(Attributes val_1, Attributes val_2) {
            return val_1.occurrence - val_2.occurrence;
        }
    }
    public static class sortByCount implements Comparator<Entry<Document, Integer>> {
        @Override
        public int compare(Entry<Document, Integer> val_1, Entry<Document, Integer> val_2) {
            return val_1.getValue() - val_2.getValue();
        }
    }
}
