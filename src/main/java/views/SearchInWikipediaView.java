package views;

import java.io.IOException;

public interface SearchInWikipediaView extends BaseView{
    void startWorkingStatus();

    void stopWorkingStatus();

    String getLastSearchedText();

    String getSelectedResultTitle();

    void showErrorSearching(IOException ioException);
}
