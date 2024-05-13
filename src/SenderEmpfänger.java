public class SenderEmpfänger {

    private static int zahl;
    public static Integer getZahl() {
    return zahl;
    }
    public static void setZahl(int zahl) {
        SenderEmpfänger.zahl = zahl;
    }
    public void sendeZahl(int zahl) {
       setZahl(zahl);
    }
}
