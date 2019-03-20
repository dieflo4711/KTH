
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diego
 */
public class BinarySearch {
    public static int search(String key, ArrayList<AttributeCont> a) {
        return search(key, a, 0, a.size());
    }
    public static int search(String key, ArrayList<AttributeCont> a, int lo, int hi) {
        if (hi <= lo) return -(lo + 1);
        int mid = lo + (hi - lo) / 2;
        int cmp = a.get(mid).word.compareTo(key);
        if      (cmp > 0) return search(key, a, lo, mid);
        else if (cmp < 0) return search(key, a, mid+1, hi);
        else              return mid;
    }
}
