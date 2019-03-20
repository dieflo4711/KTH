public final class mStatic {

    public static final String basePath = "/var/tmp/";

    public static final int charBytes = 1;
    public static final int intBytes = 4;
    
    public static int GetHash(String str)
    {
        int h = Math.abs(str.hashCode())*8;
        return h;
    }
}