package views;

import controller.VideoGameInfoController;
import model.Listener;
import model.ModelDB;
import model.VideoGameInfoModel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StoredInfoViewImpl implements StoredInfoView {
    private VideoGameInfoController controller;
    private VideoGameInfoModel model;
    private ModelDB modelDB;
    protected JPanel content;
    private JComboBox storedGameInfo;
    private JTextPane storedInfoDisplayPane;
    private JScrollPane storedInfoGeneralPane;
    protected String tabbedTitle;
    private JOptionPane errorMessage;

    public StoredInfoViewImpl(VideoGameInfoController controller, VideoGameInfoModel model) {
        this.controller = controller;
        this.model = model;
        tabbedTitle = "Stored Info";
        storedInfoDisplayPane.setContentType("text/html");
        modelDB = new ModelDB(this.model);
        initListeners();
        setTitleDataBase();
    }
    public void startWorkingStatus() {
        for(Component c: this.content.getComponents()) c.setEnabled(false);
        storedInfoDisplayPane.setEnabled(false);
    }

    public void stopWorkingStatus() {
        for(Component c: this.content.getComponents()) c.setEnabled(true);
        storedInfoDisplayPane.setEnabled(true);
    }
    @Override
    public Container getContent() {
        return this.content;
    }
    public String getTabbedName(){ return this.tabbedTitle; }

    public void initListeners(){
        storedGameInfo.addActionListener(ActionEvent -> controller.searchGameInfoDB());
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
                setTitleDataBase();
            }
            @Override
            public void didSaveInHistoryListener() {

            }
            @Override
            public void notifyErrorGettingUserHistory(SQLException sqlException) {

            }
        });
    }
    private void setTitleDataBase(){
        storedGameInfo.setModel(new DefaultComboBoxModel<Object>(modelDB.getTitleOfDataBase()));
    }
    public String getSelectedGame(){ return storedGameInfo.getSelectedItem().toString();}

    public JTextPane getStoredInfoDisplayPane() { return storedInfoDisplayPane;}

    public void showErrorGetContent(SQLException exception){
        JOptionPane.showMessageDialog(errorMessage, "Error getting content");
    }
}
