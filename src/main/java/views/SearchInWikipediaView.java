package views;

import javax.swing.*;
import java.io.IOException;

public interface SearchInWikipediaView extends BaseView{
    void startWorkingStatus();

    void stopWorkingStatus();

    String getLastSearchedText();

    String getSelectedResultTitle();

    void showErrorSearching(IOException ioException);

    void redirectToThisTab();

    void setTabbedPane(JTabbedPane tabbedPane1);

    void setSearchBoxInWikipedia(String content);

    void showEmptySearchingError();
}
