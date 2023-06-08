package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;

public interface VideoGameInfoModel {

    void searchNow(String title) throws IOException;

    public JsonArray getQuery();

    public void addListener(Listener listener);

    public ArrayList<Listener> getListeners();

    public void getPageIntroduction(SearchResult searchResult);

    public JsonElement getSearchResultContent();

    public void redoSearch(String selectedGame) throws IOException;
}
