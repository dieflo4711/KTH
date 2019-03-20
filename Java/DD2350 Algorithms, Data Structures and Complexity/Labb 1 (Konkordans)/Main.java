/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static String[] readWordList(BufferedReader input) throws IOException {
   String[] list = new String[500000];
   int idx = 0;
    while (true) {
      String s = input.readLine();
      if (s.equals("#"))
        break;
      list[idx] = s;
      idx++;
    }
    return list;
  }
		    
  final static int WORD_MAXLEN = 41;
  final static int[][] matrix = new int[WORD_MAXLEN][WORD_MAXLEN];

  public static void main(String args[]) throws IOException {
    long t1 = System.currentTimeMillis();
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    
    String[] wordList = readWordList(stdin);
    String word;

	for(int i = 0; i < WORD_MAXLEN; i++){
		matrix[0][i] = i;
		matrix[i][0] = i;
	}
	
    while((word = stdin.readLine()) != null) {
      ClosestWords closestWords = new ClosestWords(word,wordList,matrix);

      System.out.print(word + " (" + closestWords.getMinDistance() + ")");
      for (String w : closestWords.getClosestWords())
        System.out.print(" " + w);
      System.out.println();
    
    }
    
        long tottime = (System.currentTimeMillis() - t1);
        //System.out.println("CPU time: " + tottime + " ms");

  }
}
