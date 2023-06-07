package model;

import java.sql.SQLException;
public interface ModelDBInterface {
    public void saveInfo(String title, String extract);

    public void saveHistory(String title, String extract);

    public Object[] getTitleOfDataBase();

    public String getContent(String string) throws SQLException;

    public void onEventDeleteItem(String selectedGame) throws SQLException;

    public Object[] getHistoryOfDataBase();
}
