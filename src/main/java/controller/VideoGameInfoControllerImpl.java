package controller;

import model.ModelDB;
import model.ModelDBInterface;
import model.VideoGameInfoModel;
import views.HistoryView;
import views.SearchInWikipediaView;
import views.StoredInfoView;

import java.sql.SQLException;

public class VideoGameInfoControllerImpl implements VideoGameInfoController {
    private VideoGameInfoModel model;
    private SearchInWikipediaView searchInWikipediaView;
    private StoredInfoView storedInfoView;
    private HistoryView historyView;
    private Thread taskThread;
    private ModelDBInterface modelDB;

    public VideoGameInfoControllerImpl(VideoGameInfoModel videoGameInfoModel){
        this.model = videoGameInfoModel;
        modelDB = new ModelDB(videoGameInfoModel);
        //onEventSaveLocallyButton();
    }

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView) {this.searchInWikipediaView = searchInWikipediaView; }
    public void setStoredInfoView(StoredInfoView storedInfoView){ this.storedInfoView = storedInfoView; }
    public void setHistoryView(HistoryView historyView){this.historyView = historyView; }
    @Override
    public void onEventSearch(String title){
        onEventSaveHistory();
        taskThread = new Thread(() -> {
            searchInWikipediaView.startWorkingStatus();
            model.search(title);
            searchInWikipediaView.stopWorkingStatus();
        });
        taskThread.start();
    }
    public void onEventSaveHistory(){
        if(searchInWikipediaView.getLastSearchedText() != "")
            modelDB.saveHistory(searchInWikipediaView.getSelectedResultTitle().replace("'","`"),searchInWikipediaView.getLastSearchedText());
    }
    @Override
    public void onEventSaveLocallyButton(){
        if(searchInWikipediaView.getLastSearchedText() != "")
            modelDB.saveInfo(searchInWikipediaView.getSelectedResultTitle().replace("'","`"),searchInWikipediaView.getLastSearchedText());
    }

    public void searchGameInfoDB(){
        String string = storedInfoView.getSelectedGame();
        try{
            storedInfoView.getStoredInfoDisplayPane().setText(modelDB.getContent(string));
        }catch(SQLException e) {
            storedInfoView.showErrorGetContent(e);
        }
    }

}
