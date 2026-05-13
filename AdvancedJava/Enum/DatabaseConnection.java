package Enum;

//TODO: Topic: Singleton class using Enum

public enum DatabaseConnection {
    INSTANCE;

    private String url;
    private String username;

    // This block runs once when the enum class is loaded
    {
        this.url = "jdbc:mysql://localhost:3306/mydb";
        this.username = "admin";
        System.out.println("DatabaseConnection initialized once.");
    }

    public void connect() {
        System.out.println("Connecting to: " + url + " as " + username);
    }

    public void disconnect() {
        System.out.println("Disconnecting from: " + url);
    }

    public String getUrl() { return url; }
}

class Connection {
    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.INSTANCE;
        DatabaseConnection db2 = DatabaseConnection.INSTANCE;

        db1.connect();
        db2.connect();

        // Always the same instance
        System.out.println(db1 == db2); // true
    }
}
