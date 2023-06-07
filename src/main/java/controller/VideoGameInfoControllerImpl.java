package controller;

import model.ModelDB;
import model.ModelDBInterface;
import model.VideoGameInfoModel;
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
    public void saveHistory(String userSearchTerm, String selectedPage){
        if(userSearchTerm != "" && selectedPage != "")
            modelDB.saveHistory(userSearchTerm.replace("'", "`"), selectedPage);
    }
    @Override
    public void onEventSaveLocallyButton(){
        if(searchInWikipediaView.getLastSearchedText() != "")
            modelDB.saveInfo(searchInWikipediaView.getSelectedResultTitle().replace("'","`"),searchInWikipediaView.getLastSearchedText());
    }
    public void searchGameFromHistory(String selectedContent){
        searchInWikipediaView.redirectToThisTab();
        String selectedGame = parseSelectedGame(selectedContent);
        onEventSearch(selectedGame);
    }
    private String parseSelectedGame(String selectedContent){
        int indice = selectedContent.indexOf("/");
        if (indice >= 0) {
            return selectedContent.substring(0, indice).trim();
        }
        return selectedContent.trim();
    }
    public void searchGameInfoDB(String selectedGame){
        try{
            /** storedInfoView.showContent()*/ /** PARA AHCER DPS LLAMAR A UN METODO DE LA VISTA Y PASARLE (modelDB.getContent(selectedGame)*/
            storedInfoView.getStoredInfoDisplayPane().setText(modelDB.getContent(selectedGame));
        }catch(SQLException e) {
            storedInfoView.showErrorGetContent(e);
        }
    }
    public void onEventDelete(int indexSelected, String selectedGame){
        if(indexSelected > -1){
            try{
                modelDB.onEventDeleteItem(selectedGame);
                uploadGameList();
            }catch(SQLException sqlException){

            }
        }
    }
    private void uploadGameList(){
        storedInfoView.uploadGameList();
    }

    public void saveData(String selectedItem, String informationText){
        modelDB.saveInfo(selectedItem,informationText);
    }
}
