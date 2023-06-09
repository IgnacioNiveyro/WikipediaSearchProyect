package model;

import utils.CurrentDateManager;
import utils.DateManager;

import java.sql.SQLException;
import java.util.List;

public class ModelDB implements ModelDBInterface{
    private VideoGameInfoModel model;

    private DataBase dataBase;

    private DateManager dateManager;

    public ModelDB(VideoGameInfoModel model){
        this.model = model;
        dataBase = new DataBase();
        dateManager = new CurrentDateManager();
        loadDatabase();
    }

    public void saveInfo(String title, String extract){
        boolean itWasDone = false;
        try{
            itWasDone = dataBase.saveInfo(title,extract);
        }catch(SQLException e){
            for(Listener listener : model.getListeners())
                listener.notifyViewErrorSavingLocally(e);
        }
        for(Listener listener : model.getListeners())
            listener.didUpdateListener();
        if(itWasDone)
            for(Listener listener : model.getListeners())
                listener.notifyViewSaveCorrect();
    }

    public void saveHistory(String userSearchTerm, String pageSelectedByUser){
        boolean itWasDone = false;
        String date = dateManager.getDate().toString();
        try{
            itWasDone = dataBase.saveHistory(userSearchTerm,pageSelectedByUser,date);
        }catch(SQLException e){
            for(Listener listener : model.getListeners())
                listener.notifyViewErrorSavingHistory(e);
        }
        for(Listener listener : model.getListeners())
            listener.didSaveInHistoryListener();
    }

    public Object[] getTitleOfDataBase(){
        Object [] title = null;
        try{
            title = dataBase.getTitles().stream().sorted().toArray();
        } catch(SQLException e){
            for(Listener listener : model.getListeners())
                listener.notifyViewErrorSavingLocally(e);
        }
        return title;
    }

    public Object[] getHistoryOfDataBase(){
        Object [] history = null;
        try{
            history = dataBase.getHistory().stream().sorted().toArray();
        }catch (SQLException e){
            for(Listener listener : model.getListeners())
                listener.notifyErrorGettingUserHistory(e);
        }
        return history;
    }
    public String getContent(String string) throws SQLException {
        return dataBase.getContent(string);
    }
    public void onEventDeleteItem(String selectedGame) throws SQLException {
        boolean itWasDone = false;
        try {
            itWasDone = dataBase.deleteEntry(selectedGame);
            if (itWasDone) {
                for (Listener l : model.getListeners())
                    l.notifyViewDeleteCorrect();
            }
        } catch (SQLException e) {
        }
        if (!itWasDone)
            for (Listener l : model.getListeners())
                l.notifyViewErrorDeleting(new SQLException("Error"));
    }
    private void loadDatabase(){
        try {
            dataBase.loadDatabase();
        }catch(SQLException e){
            for(Listener listener : model.getListeners())
                listener.notifyErrorLoadingDataBase(e);
        }
    }
    public void setDataBase(DataBase dataBase){
        this.dataBase = dataBase;
    }
}
