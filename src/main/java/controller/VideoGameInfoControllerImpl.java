package controller;

import model.VideoGameInfoModel;
import views.SearchInWikipediaView;

public class VideoGameInfoControllerImpl implements VideoGameInfoController {
    private VideoGameInfoModel videoGameInfoModel;
    private SearchInWikipediaView searchInWikipediaView;
    private Thread taskThread;

    public VideoGameInfoControllerImpl(VideoGameInfoModel videoGameInfoModel){
        this.videoGameInfoModel = videoGameInfoModel;
    }

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView) {this.searchInWikipediaView = searchInWikipediaView; }

    @Override
    public void onEventSearch(String title){
        taskThread = new Thread(() -> {
            //searchInWikipediaView.startWaitingStatus();
            videoGameInfoModel.search(title);
            //searchInWikipediaView.stopWaitingStatus();
        });
        taskThread.start();
    }


}
