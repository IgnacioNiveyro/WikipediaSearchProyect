package model;

import dyds.videogameInfo.fulllogic.WikipediaPageAPI;
import dyds.videogameInfo.fulllogic.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

public class APIImpl implements API{

    private WikipediaSearchAPI searchAPI;
    private WikipediaPageAPI pageAPI;

    public APIImpl(){
        retrofit();
    }

    public void retrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    public WikipediaSearchAPI getSearchAPI() { return searchAPI; }

    @Override
    public Response<String> getPageAPI(String id) {
        Response<String> response = null;
        try {
            response = pageAPI.getExtractByPageID(id).execute();
        }catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
