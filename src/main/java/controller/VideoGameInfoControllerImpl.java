package controller;

import model.ModelDB;
import model.ModelDBInterface;
import model.VideoGameInfoModel;
import views.SearchInWikipediaView;
import views.StoredInfoView;

public class VideoGameInfoControllerImpl implements VideoGameInfoController {
    private VideoGameInfoModel videoGameInfoModel;
    private SearchInWikipediaView searchInWikipediaView;
    private StoredInfoView storedInfoView;
    private Thread taskThread;
    private ModelDBInterface modelDB;

    public VideoGameInfoControllerImpl(VideoGameInfoModel videoGameInfoModel){
        this.videoGameInfoModel = videoGameInfoModel;
        modelDB = new ModelDB(videoGameInfoModel);
        //onEventSaveLocallyButton();
    }

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView) {this.searchInWikipediaView = searchInWikipediaView; }
    public void setStoredInfoView(StoredInfoView storedInfoView){ this.storedInfoView = storedInfoView; }
    @Override
    public void onEventSearch(String title){
        taskThread = new Thread(() -> {
            searchInWikipediaView.startWorkingStatus();
            videoGameInfoModel.search(title);
            searchInWikipediaView.stopWorkingStatus();
        });
        taskThread.start();
    }
    @Override
    public void onEventSaveLocallyButton(){
        if(searchInWikipediaView.getLastSearchedText() != "")
            modelDB.saveInfo(searchInWikipediaView.getSelectedResultTitle().replace("'","`"),searchInWikipediaView.getLastSearchedText());
    }

}
