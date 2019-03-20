public class mOccurances {
	
	BRandomAccessFile occurances;
	
	int wordindex = 0;
	int occuranceCount = 0;
	
	public mOccurances() throws Exception{
		occurances = new BRandomAccessFile(mStatic.basePath+"occurance","rw");
		occurances.seek(wordindex+mStatic.intBytes);
	}
	
	
	public int FinalizeWord() throws Exception{
		
		final int index = wordindex;

		occurances.seek(wordindex);

		occurances.writeInt(occuranceCount);

		wordindex += (occuranceCount*mStatic.intBytes+mStatic.intBytes);
		occuranceCount = 0;

		occurances.seek(wordindex+4);
		return index;
	}
	
	public void AddOccurance(int textIndex) throws Exception{
		occurances.writeInt(textIndex);
		occuranceCount++;
	}
	
	public mOccuranceObject GetOccurances(int index) throws Exception{
		occurances.seek(index);
		return new mOccuranceObject(occurances.readInt(), index+4);
	}
	
}
