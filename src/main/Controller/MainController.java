public class MainController {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new InMemoryDatabaseManager();

        view.write("Hi user!");
        view.write("Enter please name of base and password.\n" +
                "format have to be: database|userName|password");
        String string = view.read();
        String [] data = string.split("[|]");
        String databaseName = data [0];
        String userName = data [1];
        String password = data [2];
        manager.connect(databaseName, userName, password);
        view.write("Connect is successful");

    }
}
