package model;

import java.io.IOException;
import java.util.ArrayList;

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
    public VideoGameInfoModelImpl(){
        api = new APIImpl();
        gson = new Gson();
        listeners = new ArrayList<Listener>();
    }
    @Override
    public void search(String title){
        searchNow(title);
    }
    private void searchNow(String title) throws IOException {
        Response <String> callForSearchResponse = api.getSearchAPI().searchForTerm(title +  " articletopic:\"video-games\"").execute();
        JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        this.query = query.get("search").getAsJsonArray();
        for (Listener l : listeners)
            l.finishSearch();
    }

}
