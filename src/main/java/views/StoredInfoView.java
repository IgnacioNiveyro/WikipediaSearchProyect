package views;

import javax.swing.*;
import java.sql.SQLException;

public interface StoredInfoView extends BaseView {

    String getSelectedGame();
    JTextPane getStoredInfoDisplayPane();
    void showErrorGetContent(SQLException exception);
    void uploadGameList();


}
