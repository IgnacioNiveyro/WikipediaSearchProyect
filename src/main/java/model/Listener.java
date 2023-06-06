package model;

import java.sql.SQLException;

public interface Listener {
    public void finishSearch();
    public void notifyErrorLoadingDataBase(SQLException sqlException);
    public void fetchPage();
    public void notifyViewErrorSavingLocally(SQLException sqlException);
    public void notifyViewSaveCorrect();
    public void didUpdateListener();
    public void didSaveInHistoryListener();
    public void notifyErrorGettingUserHistory(SQLException sqlException);
    public void notifyViewDeleteCorrect();
    public void notifyViewErrorDeleting(SQLException sqlException);
}
