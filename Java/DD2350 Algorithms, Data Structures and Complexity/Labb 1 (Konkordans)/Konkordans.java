import java.io.RandomAccessFile;
import java.util.NoSuchElementException;

public class Konkordans {

	static mOccurances occ;
	static mArray arr;
	static mHashtable hash;

	private static void Init() throws Exception {
		occ = new mOccurances();
		arr = new mArray();
		hash = new mHashtable();
	}

	public static void main(String[] args) throws Exception {

		if(args.length != 1)
		{
			throw new IllegalArgumentException();
		}

		Init();

		BuildDB();

		mOccuranceObject occurances = Search(args[0].toLowerCase());

		PrintOccurances(occurances);
	}

	private static mOccuranceObject Search(String search) throws Exception
	{
		int pHash = mStatic.GetHash(search.substring(0, Math.min(search.length(), 3)));
		int whash = mStatic.GetHash(search);

		int arrRef = hash.GetArrayReference(pHash);
		if(arrRef == -1) {throw new NoSuchElementException("Word not found [mHashtable] ["+search+"]");}

		int occRef = arr.GetOccuranceReference(arrRef, whash);
		if(occRef == -1) {throw new NoSuchElementException("Word not found [mArray] ["+search+"]");}

		mOccuranceObject occurances = occ.GetOccurances(occRef);
		if(occurances == null) {throw new NoSuchElementException("Word not found [mOccurances] ["+search+"]");}

		return occurances;
	}

	private static void PrintOccurances(mOccuranceObject occurances) throws Exception {
			
		RandomAccessFile raf = new RandomAccessFile(mStatic.basePath+"korpus","r");
		
		System.out.println(occurances.count+" occurances were found");
		
		final int printSize = 30;
		
		int outCount = 0;
		while(occurances.hasNext() && outCount < 20)
		{
			int index = occurances.next();
			raf.seek(index-printSize);
			
			StringBuilder output = new StringBuilder();
			
			for(int i = 0; i < printSize*2; i++)
			{
				output.append((char)raf.read());
			}
			
			System.out.println();
			System.out.println(output.toString());

			outCount++;
		}
		
		raf.close();
	}

	private static void BuildDB() throws Exception {
		
		RandomAccessFile korpus = new RandomAccessFile(mStatic.basePath+"ut","r");
		
		KorpusObject prev = GetNextWordNew(korpus);

		while(prev != null)
		{
			KorpusObject obj = GetNextWordNew(korpus);

			if(obj == null)
			{
				occ.AddOccurance(prev.index);
				arr.AddWordEnd(prev.word, occ.FinalizeWord());
				hash.InsertIntoHashtable(arr.FinalizeArr(), prev.hashIndex);
				break;
			}
			
			if(prev.wordHash == obj.wordHash)
			{
				occ.AddOccurance(prev.index);
				prev = obj;
				continue;
			}
			else
			{
				occ.AddOccurance(prev.index);
				arr.AddWordEnd(prev.word, occ.FinalizeWord());
			}
			
			if(prev.hashIndex != obj.hashIndex)
			{
				hash.InsertIntoHashtable(arr.FinalizeArr(), prev.hashIndex);
			}
			
			prev = obj;
		}

		korpus.close();
	}

	private static KorpusObject GetNextWordNew(RandomAccessFile file) throws Exception {

		String line = file.readLine();

		if(line == null)
		{
			return null;
		}
		
		StringBuilder word = new StringBuilder();

		int cIndex = 0;
		while(cIndex < line.length())
		{
			char chr = line.charAt(cIndex);
			cIndex++;
			if(chr == 32)
			{
				break;
			}
			word.append(chr);
		}

		StringBuilder indexStr = new StringBuilder();

		while(cIndex < line.length())
		{
			char chr = line.charAt(cIndex);
			cIndex++;
			if(chr == 10)
			{
				break;
			}
			indexStr.append(chr);
		}

		String wordStr = word.toString();

		return new KorpusObject(wordStr, Integer.parseInt(indexStr.toString()));
	}
}
