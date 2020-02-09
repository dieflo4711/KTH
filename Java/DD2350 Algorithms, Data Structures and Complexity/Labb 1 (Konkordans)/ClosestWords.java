java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = new LinkedList<String>();

  int closestDistance = -1;
  
  String prevWord = "";
  int prevWordLen = 0;

  int partDist(String w1, String w2, int[][] matrix) {
	  
	int w1Len = w1.length();
	int w2Len = w2.length();
	int sameChars = sameChars(prevWord, w2,prevWordLen,w2Len);
	  
	for(int i = 1; i <= w1Len; i++){
		for(int j = sameChars+1; j <= w2Len; j++) {
			if(j > w2Len) break;
			
			matrix[i][j] = Math.min(matrix[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1), Math.min(matrix[i-1][j] + 1, matrix[i][j-1] + 1));
		}
	}
	
	prevWord = w2;
	prevWordLen = w2Len;
	return matrix[w1Len][w2Len];
  }
  
  int sameChars(String w1, String w2,int w1Len,int w2Len) {
	  
	  int shortestLen = Math.min(w1Len,w2Len);
	  
	  for(int i = 0; i < shortestLen; i++){
		  if(w1.charAt(i) != w2.charAt(i))
			  return i;
	  }
	  
	  return shortestLen;	  
  }

  int wLen = 0;

  public ClosestWords(String w, String[] wordList, int[][] matrix) {
	
	closestDistance = -1;
	wLen = w.length();  
	
	String s;
	for (int i=0; i < wordList.length; i++) {
		s = wordList[i];
		
		if(s == null)
			break;
		
		if(closestDistance == 0)
			break;
		
	  if((Math.abs(s.length() - wLen) > closestDistance) && closestDistance != -1)
		continue;
	  
      int dist = partDist(w, s, matrix);
      if(dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords.clear();
        closestWords.add(s);
      } else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
