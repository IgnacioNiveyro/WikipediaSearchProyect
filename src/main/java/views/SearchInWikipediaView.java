package views;

public interface SearchInWikipediaView extends BaseView{
    void startWorkingStatus();

    void stopWorkingStatus();

    String getLastSearchedText();

    String getSelectedResultTitle();

}
