/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import se.kth.id1020.util.Document;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Word;
import java.util.*;

/**
 *
 * @author diego
 */
public class TinySearchEngine implements TinySearchEngineBase {
    Indexing idxIng = new Indexing();
    
    //Build the index
    public void insert(Word word, Attributes attr) {
        idxIng.insert(word, attr);
    }
    
    //Searching
    public List<Document> search(String query) {//Try?
        try {
            return Parser.parse(query, idxIng);
        } catch (Exception e) {
            System.out.println("Ops");
            return null;
        }
        /*return p.splitAndUnion();
        List arr = idxIng.search(query);
        Attributes a;
        ArrayList<Document> docArr = new ArrayList<>();

        for(int i = 0; i < arr.size(); i++){
            a = (Attributes) arr.get(i);
            docArr.add(a.document);
        }
        return docArr;*/
    }
}
