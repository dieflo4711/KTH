import java.io.RandomAccessFile;


public class mHashtable {
	
	RandomAccessFile hashtable;
	
	public mHashtable() throws Exception{
		hashtable = new RandomAccessFile(mStatic.basePath+"hashtable","rw");
	}
	
	public void InsertIntoHashtable(int arrayReference, int phash) throws Exception{

		hashtable.seek(phash);

		try{
			int val = -1;
			int boffset = 0;
			while(val != 0)
			{
				val = hashtable.readInt();
				boffset+=mStatic.intBytes;
				if(val == 0)
				{
					val = hashtable.readInt();
					boffset+=mStatic.intBytes;
				}
			}

			hashtable.seek(phash+boffset-2*mStatic.intBytes);

			hashtable.writeInt(phash);
			hashtable.writeInt(arrayReference);

			
		}
		catch (Exception e)
		{
			hashtable.seek(phash);
			hashtable.writeInt(phash);
			hashtable.writeInt(arrayReference);
		}		
	}
	
	public int GetArrayReference(int phash) throws Exception {
		hashtable.seek(phash);

		int thash = -1;
		int val = -1;

		try{
			while(thash != phash)
			{
				thash = hashtable.readInt();
				val = hashtable.readInt();
			}
		}
		catch(Exception e)
		{
			return -1;
		}

		return val;
	}

}
