package server.accept;

import server.authorization.Password;
import server.database.DataBase;

import java.sql.SQLException;

public class Manager {
    public boolean readPassword(String l, Password p) throws SQLException {
        if (!p.getRegistration()) {
            return DataBase.getInstance().findName(l, p.getPassword());
        }
        return true;
    }
}
