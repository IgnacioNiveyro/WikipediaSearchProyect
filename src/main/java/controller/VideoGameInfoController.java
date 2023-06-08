package controller;

import views.HistoryView;
import views.SearchInWikipediaView;
import views.StoredInfoView;

public interface VideoGameInfoController {

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView);
    public void setStoredInfoView(StoredInfoView storedInfoView);
    public void setHistoryView(HistoryView historyView);
    void onEventSearch(String title);
    void onEventSaveLocallyButton(String title, String gameContent);
    void searchGameInfoDB(String selectedGame);
    void searchGameFromHistory(String selectedGame);
    void onEventDelete(int selectedIndex, String selectedGame);
    void saveData(String selectedItem, String informationText);
    void saveHistory(String userSearchTerm, String selectedPage);
}
