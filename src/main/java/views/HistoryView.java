package views;

import java.sql.SQLException;

public interface HistoryView extends BaseView{
    void showErrorSavingHistory(SQLException sqlException);
    void showErrorGettingUserHistory(SQLException sqlException);
    void setHistoryDataBase();
}
