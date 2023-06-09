package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
public class VideoGameInfoModelImpl implements VideoGameInfoModel{
    private API api;
    private JsonArray query;
    private Gson gson;
    private ArrayList<Listener> listeners;
    public JsonElement searchResultContent;
    public VideoGameInfoModelImpl(){
        api = new APIImpl();
        gson = new Gson();
        listeners = new ArrayList<Listener>();
    }

    public void searchNow(String title) throws IOException {
        Response <String> callForSearchResponse = api.getSearchAPI().searchForTerm(title +  " articletopic:\"video-games\"").execute();
        JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        this.query = query.get("search").getAsJsonArray();
        for (Listener l : listeners)
            l.finishSearch();
    }

    public void redoSearch(String selectedGame) throws IOException{
        Response <String> callForSearchResponse = api.getSearchAPI().redoSearchForTerm(selectedGame +  " articletopic:\"video-games\"").execute();
        JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        this.query = query.get("search").getAsJsonArray();
        for (Listener l : listeners)
            l.finishSearch();
    }
    public ArrayList<Listener> getListeners(){ return this.listeners;}
    public void getPageIntroduction(SearchResult searchResult) {
        Response<String> callForPageResponse = api.getPageAPI(searchResult.pageID);
        JsonObject jsonObject = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jsonObject.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        searchResultContent = page.get("extract");
        for (Listener l : listeners)
            l.fetchPage();
    }
    public JsonElement getSearchResultContent() {
        return this.searchResultContent;
    }
    public JsonArray getQuery(){
        return this.query;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }
}
