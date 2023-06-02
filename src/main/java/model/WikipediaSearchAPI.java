package model;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaSearchAPI {

  @GET("api.php?action=query&list=search&utf8=1&format=json&srlimit=5")
  Call<String> searchForTerm(@Query("srsearch") String term);

}
