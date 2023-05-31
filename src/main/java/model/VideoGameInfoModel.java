package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;

public interface VideoGameInfoModel {

    void search(String title);

    public JsonArray getQuery();

    public void addListener(Listener listener);

    public void getPageIntroduction(SearchResult searchResult);

    public JsonElement getSearchResultContent();
}
