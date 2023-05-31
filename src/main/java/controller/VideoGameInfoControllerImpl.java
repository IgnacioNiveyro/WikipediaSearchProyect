package controller;

import model.VideoGameInfoModel;
import views.SearchInWikipediaView;
import views.StoredInfoView;

public class VideoGameInfoControllerImpl implements VideoGameInfoController {
    private VideoGameInfoModel videoGameInfoModel;
    private SearchInWikipediaView searchInWikipediaView;
    private StoredInfoView storedInfoView;
    private Thread taskThread;

    public VideoGameInfoControllerImpl(VideoGameInfoModel videoGameInfoModel){
        this.videoGameInfoModel = videoGameInfoModel;
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


}
