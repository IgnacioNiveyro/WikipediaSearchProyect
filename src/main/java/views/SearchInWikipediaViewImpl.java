package views;

import controller.VideoGameInfoController;
import model.VideoGameInfoModel;

import javax.swing.*;
import java.awt.*;

public class SearchInWikipediaViewImpl implements SearchInWikipediaView{

    private VideoGameInfoController videoGameInfoController;
    private VideoGameInfoModel videoGameInfoModel;
    protected JPanel content;
    private JPanel storagePane;
    private JTextField searchBoxInWikipedia;
    private JButton searchButton;
    private JTextPane searchInWikipediaDisplayPane;
    private JButton saveLocallyButton;
    protected String tabbedTitle;
    public SearchInWikipediaViewImpl(VideoGameInfoController videoGameInfoController, VideoGameInfoModel videoGameInfoModel) {
        this.videoGameInfoController = videoGameInfoController;
        this.videoGameInfoModel = videoGameInfoModel;
        initListeners();
        tabbedTitle = "Search in Wikipedia!";
        searchInWikipediaDisplayPane.setContentType("text/html");
        //lastUpdateLbl.setText(""); revisar dps para que es y adaptar
    }
    @Override
    public String getTabbedName(){ return this.tabbedTitle; }
    @Override
    public Container getContent() {
        return this.content;
    }

    @Override
    public void startWaitingStatus(){}

    @Override
    public void stopWaitingStatus(){}

    private void initListeners(){
        searchButton.addActionListener(actionEvent -> videoGameInfoController
                .onEventSearch(searchBoxInWikipedia.getText()));

        //videoGameInfoModel.addListener(new )
    }
}
