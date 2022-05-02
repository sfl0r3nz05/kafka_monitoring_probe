public class MyMessageValue {
    public String NO;
    public String Tm;
    public String Src;
    public String Dst;
    public String Ptc;
    public String Len;
    public String Inf;

    public MyMessageValue(String _NO, String _Tm, String _Src, String _Dst, String _Ptc, String _Len, String _Inf) {
        NO = _NO;
        Tm = _Tm;
        Src = _Src;
        Dst = _Dst;
        Ptc = _Ptc;
        Len = _Len;
        Inf = _Inf;
    }

    public int allLength() {
        return NO.length() + Tm.length() + Src.length() + Dst.length() + Ptc.length() + Len.length() + Inf.length();
    }
}