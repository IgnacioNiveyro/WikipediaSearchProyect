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
    public void onEventSaveLocallyButton(String title, String gameContent){
        modelDB.saveInfo(title.replace("'","`"),gameContent);
    }
    public void searchGameFromHistory(String selectedContent){
        String gameToSearch = obtenerSubstring(selectedContent);
        String selectedGame = parseSelectedGame(selectedContent);
        searchInWikipediaView.setSearchBoxInWikipedia(selectedGame);
        searchInWikipediaView.redirectToThisTab();
        try {
            model.redoSearch(gameToSearch);
        }catch(IOException e){
            searchInWikipediaView.showErrorSearching(e);
        }
    }
    private String obtenerSubstring(String texto) {
        int primerIndice = texto.indexOf("/");
        int segundoIndice = texto.indexOf("/", primerIndice + 1);

        if (primerIndice >= 0 && segundoIndice >= 0) {
            return texto.substring(primerIndice + 1, segundoIndice).trim();
        }

        return "";
    }

    private String parseSelectedGame(String selectedContent){
        int indexOfElements = selectedContent.indexOf("/");
        if (indexOfElements >= 0) {
            return selectedContent.substring(0, indexOfElements).trim();
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
