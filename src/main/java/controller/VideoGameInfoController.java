package controller;

import views.SearchInWikipediaView;
import views.StoredInfoView;

public interface VideoGameInfoController {

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView);
    public void setStoredInfoView(StoredInfoView storedInfoView);
    void onEventSearch(String title);
}
