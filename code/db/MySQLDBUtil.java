package db;

public class MySQLDBUtil {
    private static final String INSTANCE = "xxx";
    private static final String PORT_NUM = "3306";
    public static final String DB_NAME = "xxx";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "xxx";
    public static final String URL = "jdbc:mysql://"
            + INSTANCE + ":" + PORT_NUM + "/" + DB_NAME
            + "?user=" + USERNAME + "&password=" + PASSWORD
            + "&autoReconnect=true&serverTimezone=UTC";
}

