package views;

import controller.VideoGameInfoController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StoredInfoViewImpl implements StoredInfoView {
    private VideoGameInfoController controller;
    private VideoGameInfoModel model;
    private ModelDBInterface modelDB;
    protected JPanel content;
    private JComboBox storedGameInfo;
    private JTextPane storedInfoDisplayPane;
    private JScrollPane storedInfoGeneralPane;
    protected String tabbedTitle;
    private JOptionPane errorMessage;
    private JOptionPane informationMessage;

    public StoredInfoViewImpl(VideoGameInfoController controller, VideoGameInfoModel model) {
        this.controller = controller;
        this.model = model;
        tabbedTitle = "Stored Info";
        storedInfoDisplayPane.setContentType("text/html");
        modelDB = new ModelDB(this.model);
        JPopupMenu storedInfoPopup = new JPopupMenu();
        initListeners(storedInfoPopup);
        JMenuItem saveItem = configuringSaveChangesButtom();
        storedInfoPopup.add(saveItem);
        storedInfoDisplayPane.setComponentPopupMenu(storedInfoPopup);
        setTitleDataBase();
    }
    private JMenuItem configuringSaveChangesButtom() {
        JMenuItem saveItem = new JMenuItem("Save Changes!");
        saveItem.addActionListener(actionEvent -> {
            if (storedGameInfo.getSelectedItem() != null) {
                controller.saveData(storedGameInfo.getSelectedItem().toString().replace("'", "`"),storedInfoDisplayPane.getText());
            } else
                showErrorSaving(new SQLException("Error"));
        });
        return saveItem;
    }
    public void uploadGameList(){
        setTitleDataBase();
        storedInfoDisplayPane.setText("");
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

    public void initListeners(JPopupMenu storedInfoPopup){
        storedGameInfo.addActionListener(ActionEvent -> controller.searchGameInfoDB(storedGameInfo.getSelectedItem().toString()));
        JMenuItem deleteItem = new JMenuItem("Delete!");
        deleteItem.addActionListener(actionEvent -> {
            if(storedGameInfo.getSelectedIndex()>-1)
                controller.onEventDelete(storedGameInfo.getSelectedIndex(), storedGameInfo.getSelectedItem().toString());
            else
                showErrorDeleting();
        });
        storedInfoPopup.add(deleteItem);
        model.addListener(new Listener() {
            @Override
            public void finishSearch() {

            }

            @Override
            public void notifyErrorLoadingDataBase(SQLException sqlException) {
                showErrorLoadingDataBase(sqlException);
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

            @Override
            public void notifyViewDeleteCorrect() {
                showDeleteCorrect();
            }

            @Override
            public void notifyViewErrorDeleting(SQLException sqlException) {

            }

            @Override
            public void notifyViewErrorSavingHistory(SQLException sqlException) {

            }
        });
    }
    public void showContent(String contentToShow){
        storedInfoDisplayPane.setText(contentToShow);
    }
    private void setTitleDataBase(){
        storedGameInfo.setModel(new DefaultComboBoxModel<Object>(modelDB.getTitleOfDataBase()));
    }
    public String getSelectedGame(){ return storedGameInfo.getSelectedItem().toString();}

    public JTextPane getStoredInfoDisplayPane() { return storedInfoDisplayPane;}
    public void showDeleteCorrect(){
        JOptionPane.showMessageDialog(informationMessage, "Delete correctly");
    }
    public void showErrorDeleting(){
        JOptionPane.showMessageDialog(errorMessage, "Error deleting content");
    }
    public void showErrorGetContent(SQLException exception){
        JOptionPane.showMessageDialog(errorMessage, "Error getting content");
    }
    public void showErrorLoadingDataBase(SQLException sqlException){
        JOptionPane.showMessageDialog(errorMessage, "Error loading Data Base");
    }
    public void showSaveCorrect(){
        JOptionPane.showMessageDialog(informationMessage, "Save correctly!");
    }
    public void showErrorSaving(SQLException e){
        JOptionPane.showMessageDialog(errorMessage, "Error saving data");
    }
}
