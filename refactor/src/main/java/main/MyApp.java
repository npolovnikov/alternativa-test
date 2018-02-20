package main;

public class MyApp {
    public static void main(String[] args) {
        try {
            System.out.println("app v.1.13");

            final DataConnection dataConnection  = new DataConnectionImpl();
            dataConnection.loadDatas("1.txt");
            dataConnection.saveData("statistika.txt");

            System.out.println("gotovo");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("oshibka!");
        }
    }
}
