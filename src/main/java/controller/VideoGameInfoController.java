package controller;

import model.ModelDB;
import model.VideoGameInfoModelImpl;
import views.HistoryView;
import views.SearchInWikipediaView;
import views.StoredInfoView;

public interface VideoGameInfoController {

    void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView);
    void setStoredInfoView(StoredInfoView storedInfoView);
    void setHistoryView(HistoryView historyView);
    void onEventSearch(String title);
    void onEventSaveLocallyButton(String title, String gameContent);
    void searchGameInfoDB(String selectedGame);
    void searchGameFromHistory(String selectedGame);
    void onEventDelete(int selectedIndex, String selectedGame);
    void saveData(String selectedItem, String informationText);
    void saveHistory(String userSearchTerm, String selectedPage);
    void setModelDB(ModelDB modelDB);
    void setModel(VideoGameInfoModelImpl model);
}
