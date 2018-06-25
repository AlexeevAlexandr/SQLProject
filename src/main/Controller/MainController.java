public class MainController {
     private DatabaseManager manager;
    private View view;
    MainController(View view, DatabaseManager manager){
        this.view = view;
        this.manager = manager;
    }

    public void run(){
        connectToDB();
    }

    public void connectToDB() {
        view.write("Hi user!");
        while (true) {
            view.write("Enter please username, password and databaseName.\n" +
                    "format have to be: userName,password,databaseName");
            try {
            String string = view.read();
            String[] data = string.split("[,]");
            if(data.length != 3){
                throw new IllegalArgumentException("\nNot correct entered number of parameters");
            }
            String userName = data[0];
            String password = data[1];
            String databaseName = data[2];

            manager.connect(userName, password, databaseName);
                    break;
            } catch (Exception e) {
                printError(e);
            }
        }
        view.write("Connect is successful");
    }

    private void printError(Exception e) {
        String message = (e.getCause() != null) ? e.getMessage() + " " + e.getCause().getMessage() : e.getMessage();
        view.write("Connect isn't successful:\n" + message + "\n" + "Try again.");
    }
}
