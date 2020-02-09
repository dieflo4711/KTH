/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Word;
/**
 *
 * @author diego
 */

public class AttributeCont {
    ArrayList<Attributes> data = new ArrayList<>();
    String word;
    
    public AttributeCont(Word word) {
        this.word = word.word;
    }
    public void addData(Attributes attr) {
        data.add(attr);
    }
}
