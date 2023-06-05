package controller;

import views.HistoryView;
import views.SearchInWikipediaView;
import views.StoredInfoView;

public interface VideoGameInfoController {

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView);
    public void setStoredInfoView(StoredInfoView storedInfoView);
    public void setHistoryView(HistoryView historyView);
    void onEventSearch(String title);
    void onEventSaveLocallyButton();
    void searchGameInfoDB();
}
