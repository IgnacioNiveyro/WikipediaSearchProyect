package model;

import dyds.videogameInfo.fulllogic.WikipediaSearchAPI;
import retrofit2.Response;

public interface API {

    public WikipediaSearchAPI getSearchAPI();

    public Response<String> getPageAPI(String id);
}
