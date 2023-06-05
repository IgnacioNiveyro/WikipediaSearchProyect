package controller;

import model.ModelDB;
import model.ModelDBInterface;
import model.VideoGameInfoModel;
import model.VideoGameInfoModelImpl;
import views.HistoryView;
import views.SearchInWikipediaView;
import views.StoredInfoView;

import java.io.IOException;
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
    }

    public void setSearchInWikipediaView(SearchInWikipediaView searchInWikipediaView) {this.searchInWikipediaView = searchInWikipediaView; }
    public void setStoredInfoView(StoredInfoView storedInfoView){ this.storedInfoView = storedInfoView; }
    public void setHistoryView(HistoryView historyView){this.historyView = historyView; }
    @Override
    public void onEventSearch(String title){
        SaveHistory(title);
        searchNow(title);
    }
    private void searchNow(String title){
        taskThread = new Thread(() -> {
            searchInWikipediaView.startWorkingStatus();
            try {
                model.searchNow(title);
            }catch(IOException e){
                searchInWikipediaView.showErrorSearching(e);
            }
            searchInWikipediaView.stopWorkingStatus();
        });
        taskThread.start();
    }
    public void SaveHistory(String title){
        if(title != "")
            modelDB.saveHistory(title.replace("'", "`"), "extracto de vainilla");
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
