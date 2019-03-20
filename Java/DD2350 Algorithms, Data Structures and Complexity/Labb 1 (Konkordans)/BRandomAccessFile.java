import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class BRandomAccessFile {

    RandomAccessFile raf;
    FileInputStream fis;
    FileOutputStream fos;
    BufferedInputStream bis;
    BufferedOutputStream bus;

    public BRandomAccessFile(String path, String p) throws Exception{
        raf = new RandomAccessFile(path, p);
        fis = new FileInputStream(raf.getFD());
        fos = new FileOutputStream(raf.getFD());
        bis = new BufferedInputStream(fis);
        bus = new BufferedOutputStream(fos);
    }

    public void seek(int index) throws Exception
    {
        flushOutput();
        raf.seek(index);
        bis = new BufferedInputStream(fis);
        bus = new BufferedOutputStream(fos);
    }

    public void writeInt(int val) throws Exception {
        write(val >> 24);
        write(val >> 16);
        write(val >> 8);
        write(val >> 0);
    }

    //byte[] outBuffer = new byte[8192];
    //int outIndex = 0;
    public void write(int val) throws Exception{
        /*
        if(outIndex < outBuffer.length)
        {
            outBuffer[outIndex] = (byte)val;
            outIndex++;
        }
        else
        {
            raf.write(outBuffer);
            outIndex = 0;
        }
        */
        bus.write(val);
    }


    public char readChar() throws Exception
    {
        int val = 0;
        for(int i = mStatic.charBytes-1; i >= 0; i--)
        {
            val |= (read() << i*8);
        }
        
        return (char)val;
    }

    public void skipBytes(int n) throws Exception{
        for(int i = 0; i < n; i++)
        {
            read();
        }
    }

    public void writeChar(char chr) throws Exception{
        for(int i = mStatic.charBytes-1; i >= 0; i--)
        {
            write(chr >> i*8);
        }
    }

    public void writeBytes(byte[] val) throws Exception
    {
        for(int i = 0; i < val.length; i++)
        {
            write(val[i]);
        }
    }

    public int readInt() throws Exception{
        int val = 0;
        val |= (read() << 24);
        val |= (read() << 16);
        val |= (read() << 8);
        val |= (read() << 0);
        return val;
    }

    public int read() throws Exception
    {
        return bis.read();
    }

    private void flushOutput() throws Exception {
        bus.flush();
    }

}