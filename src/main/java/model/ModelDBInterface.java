package model;

import java.sql.SQLException;
public interface ModelDBInterface {
    public void saveInfo(String title, String extract);

    public void saveHistory(String title, String extract);

    public Object[] getTitleOfDataBase();
    public Object[] getHistoryOfDataBase();

    public String getContent(String string) throws SQLException;

    public void onEventDeleteItem(String selectedGame) throws SQLException;

    public void setDataBase(DataBase dataBase);
}
