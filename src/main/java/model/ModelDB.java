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
        System.out.println("Mi titulo es:  "+title);
        System.out.println("Mi extracto es:  "+extract);
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
}
