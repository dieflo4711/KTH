import java.io.RandomAccessFile;

public class mArray {

	BRandomAccessFile array;

	public mArray() throws Exception {
		array = new BRandomAccessFile(mStatic.basePath + "array", "rw");
		array.seek(wordindex+4);
	}

	private int wordindex = 0;
	private int arrayOffset = 0;

	public int FinalizeArr() throws Exception {

		final int index = wordindex;

		array.seek(wordindex);

		array.writeInt(arrayOffset);

		wordindex += (arrayOffset+4);
		arrayOffset = 0;

		array.seek(wordindex+4);

		return index;
	}

	public void AddWordEnd(String wordEnd, int occuranceIndex) throws Exception {

		array.writeInt(mStatic.GetHash(wordEnd));
		array.writeInt(occuranceIndex);
		arrayOffset += (mStatic.intBytes*2);
	}

	public int GetOccuranceReference(int index, int textwhash) throws Exception {
		array.seek(index);

		int arrayLength = array.readInt();

		int offset = 0;
		while (offset <= arrayLength) {

			int wHash = array.readInt();

			if(wHash == textwhash)
			{
				int wIndex = array.readInt();
				return wIndex;
			}
			else
			{
				array.skipBytes(4);
				offset += (mStatic.intBytes+mStatic.charBytes+mStatic.intBytes);
				continue;
			}
		}

		return -1;
	}
}
