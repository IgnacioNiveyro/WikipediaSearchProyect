package model;

import java.sql.SQLException;

public interface Listener {
    public void finishSearch();

    public void fetchPage();
    public void notifyViewErrorSavingLocally(SQLException sqlException);
    public void notifyViewSaveCorrect();
    public void didUpdateListener();
}
