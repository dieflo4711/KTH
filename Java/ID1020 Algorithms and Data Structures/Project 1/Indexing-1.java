/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Word;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author diego
 */
public class Indexing {//
    private ArrayList<AttributeCont> indexing = new ArrayList<AttributeCont>();
    
    public void insert(Word word, Attributes attr) {
        int pos = BinarySearch.search(word.word , indexing);
        //if(word.word.equals("nightmare") && attr.document.name.equals("cp16"))
            //System.out.println("Out: Word = " + word.word + " Pos = " + pos + " Document = " + attr.document.name);
        if(pos < 0) {
            pos = -pos - 1;
            if(pos == indexing.size()-1)
                indexing.add(new AttributeCont(word));
            else
                indexing.add(pos, new AttributeCont(word));
        }
        indexing.get(pos).addData(attr);
    }
    
    public ArrayList<Attributes> search(String key) {
        int pos = BinarySearch.search(key, indexing);
        
        if(pos < 0)
            return null;
        return indexing.get(pos).data;
    }
}
