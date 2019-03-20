import java.io.RandomAccessFile;
import java.util.Iterator;

public class mOccuranceObject implements Iterator<Integer> {

	RandomAccessFile occurances = new RandomAccessFile(mStatic.basePath+"occurance","r");

    public int count;
    private int current;
    public int start;

    public mOccuranceObject(int count, int start) throws Exception {
        this.count = count;
        this.start = start;

        occurances.seek(start);
    }

    public Integer next() {
        try{
            current++;
            return occurances.readInt();
        }
        catch(Exception e)
        {
            return -1;
        }
    }

    public boolean hasNext()
    {
        return current < count;
    }

}