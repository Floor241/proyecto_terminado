package modelo;

public class Sistema {

    private static Instituto instituto = new Instituto();


    public static String passwordAdmin = "admin123";

    public static Instituto getInstituto() {
        return instituto;
    }
}
