
public class KorpusObject {

	public String word;
	public int wordHash;
	
	public String hash;
	public int hashIndex;
	
	public int index;
	
	public KorpusObject(String word, int index)
	{
		this.word = word;
		this.wordHash = mStatic.GetHash(this.word);
		
		this.hash = word.substring(0, Math.min(word.length(), 3));
		this.hashIndex = mStatic.GetHash(this.hash);
		
		this.index = index;
	}
	
	@Override
	public String toString(){
		return word+"-> pHash:"+hashIndex+" wHash:"+wordHash;
	}
	
}
