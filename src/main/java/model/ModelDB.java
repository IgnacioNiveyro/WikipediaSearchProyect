package model;

import java.sql.SQLException;

public class ModelDB implements ModelDBInterface{
    private VideoGameInfoModel model;

    private DataBase dataBase;

    public ModelDB(VideoGameInfoModel model){
        this.model = model;
        dataBase = new DataBase();
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

    public void saveHistory(String title, String extract){
        boolean itWasDone = false;
        try{
            itWasDone = dataBase.saveHistory(title,extract);
        }catch(SQLException e){
            System.out.println(e);
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
            history = dataBase.getTitles().stream().sorted().toArray();
        }catch (SQLException e){
            for(Listener listener : model.getListeners())
                listener.notifyErrorGettingUserHistory(e);
        }
        return history;
    }
    public String getContent(String string) throws SQLException {
        return dataBase.getContent(string);
    }

}
