package model;

import java.sql.SQLException;
public interface ModelDBInterface {
    public void saveInfo(String title, String extract);

    public Object[] getTitleOfDataBase();

    public String getContent(String string) throws SQLException;
}
