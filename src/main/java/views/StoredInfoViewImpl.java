package views;

import controller.VideoGameInfoController;
import model.VideoGameInfoModel;

import javax.swing.*;
import java.awt.*;

public class StoredInfoViewImpl implements StoredInfoView {
    private VideoGameInfoController videoGameInfoController;
    private VideoGameInfoModel videoGameInfoModel;
    protected JPanel content;
    private JComboBox searchBoxInStoredInfo;
    private JTextPane storedInfoDisplayPane;
    private JScrollPane storedInfoGeneralPane;
    protected String tabbedTitle;

    public StoredInfoViewImpl(VideoGameInfoController videoGameInfoController, VideoGameInfoModel videoGameInfoModel) {
        this.videoGameInfoController = videoGameInfoController;
        this.videoGameInfoModel = videoGameInfoModel;
        initListeners();
        tabbedTitle = "Stored Info";
        storedInfoDisplayPane.setContentType("text/html");
        //lastUpdateLbl.setText(""); revisar dps para que es y adaptar
    }

    @Override
    public Container getContent() {
        return this.content;
    }
    public String getTabbedName(){ return this.tabbedTitle; }

    public void initListeners(){}
}
