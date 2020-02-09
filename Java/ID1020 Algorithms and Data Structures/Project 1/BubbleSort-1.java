/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Word;
import java.util.*;
import java.util.Comparator;

/**
 *
 * @author diego
 */
public class BubbleSort {
    
    public static <T> void sort(Comparator comp, List<T> list) {
        
        if (list.isEmpty())//return;
            throw new RuntimeException("List is empty!");
        int swaps = 0;
        boolean swapped = true;
        int R = list.size() - 2;

        
        while(R >= 0 && swapped){
            swapped = false;
            for(int i = 0; i <= R; i++) {
                if(less(comp, list.get(i+1), list.get(i))) {
                    swapped = true;
                    swap(list, i, i+1);
                    swaps++;
                }
            }
            R--;
        }
    }
    public static <T> void swap(List<T> list, int n1, int n2) {
        T tmp = list.get(n1);
        list.set(n1, list.get(n2));
        list.set(n2, tmp);
    }
    
    private static boolean less(Comparator comp, Object v, Object w) {
        return comp.compare(v, w) < 0;
    }

}

