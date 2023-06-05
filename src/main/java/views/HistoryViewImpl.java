package views;

import controller.VideoGameInfoController;
import model.Listener;
import model.ModelDB;
import model.VideoGameInfoModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class HistoryViewImpl implements HistoryView{

    private JPanel content;
    private JScrollPane historyInfoGeneralPane;
    private JTextPane storedInfoDisplayPane;
    private JComboBox storedHistory;
    private JOptionPane errorMessage;
    private VideoGameInfoController controller;
    private VideoGameInfoModel model;
    private ModelDB modelDB;
    public String tabbedName = "";
    public HistoryViewImpl(VideoGameInfoController controller, VideoGameInfoModel model){
        this.controller = controller;
        this.model = model;
        modelDB = new ModelDB(this.model);
        tabbedName = "Search History";
        storedInfoDisplayPane.setContentType("text/html");
        initListeners();
    }
    public void initListeners(){
        model.addListener(new Listener() {
            @Override
            public void finishSearch() {

            }

            @Override
            public void fetchPage() {

            }

            @Override
            public void notifyViewErrorSavingLocally(SQLException sqlException) {

            }

            @Override
            public void notifyViewSaveCorrect() {

            }

            @Override
            public void didUpdateListener() {
                setHistoryDataBase();
            }

            @Override
            public void didSaveInHistoryListener() {
                //setHistoryDataBase();
            }

            @Override
            public void notifyErrorGettingUserHistory(SQLException sqlException) {
                showErrorGettingUserHistory(sqlException);
            }
        });
    }
    private void showErrorGettingUserHistory(SQLException sqlException){
        JOptionPane.showMessageDialog(errorMessage,"Error getting user history");
    }
    private void setHistoryDataBase(){
        //storedHistory.setModel(new DefaultComboBoxModel<Object>(modelDB.getHistoryOfDataBase()));
        storedHistory.setModel(new DefaultComboBoxModel<Object>(modelDB.getTitleOfDataBase()));
    }
    public Container getContent(){return this.content;}

    public String getTabbedName() {return this.tabbedName;}
}
