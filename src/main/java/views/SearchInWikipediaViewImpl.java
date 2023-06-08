package views;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.VideoGameInfoController;
import controller.VideoGameInfoControllerImpl;
import model.Listener;
import model.SearchResult;
import model.VideoGameInfoModel;
import model.VideoGameInfoModelImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static model.HTMLConverter.textToHtml;

public class SearchInWikipediaViewImpl implements SearchInWikipediaView{
    String lastSearchedText = "";
    String selectedResultTitle = "";
    private VideoGameInfoController controller;
    private VideoGameInfoModel model;
    private String searchResultTitle;
    private String searchResultPageId;
    private String searchResultSnippet;
    private SearchResult selectedSearchResult;
    protected JPanel content;
    private JPanel storagePane;
    private JTextField searchBoxInWikipedia;
    private JButton searchButton;
    private JTextPane searchInWikipediaDisplayPane;
    private JButton saveLocallyButton;
    protected String tabbedTitle;
    private JOptionPane informationMessage;
    private JOptionPane errorMessage;
    private JTabbedPane tabbedPane;

    public SearchInWikipediaViewImpl(VideoGameInfoController videoGameInfoController, VideoGameInfoModel videoGameInfoModel) {
        this.controller = videoGameInfoController;
        this.model = videoGameInfoModel;
        tabbedTitle = "Search in Wikipedia!";
        searchInWikipediaDisplayPane.setContentType("text/html");
        initListeners();
        saveLocallyButton.setEnabled(false);
    }
    @Override
    public String getTabbedName(){ return this.tabbedTitle; }
    @Override
    public Container getContent() {
        return this.content;
    }


    public void startWorkingStatus() {
        for(Component c: this.storagePane.getComponents()) c.setEnabled(false);
        searchInWikipediaDisplayPane.setEnabled(false);
    }

    public void stopWorkingStatus() {
        for(Component c: this.storagePane.getComponents()) c.setEnabled(true);
        searchInWikipediaDisplayPane.setEnabled(true);
    }

    private void initListeners(){
        searchButton.addActionListener(actionEvent -> controller
                .onEventSearch(searchBoxInWikipedia.getText()));

        saveLocallyButton.addActionListener(actionEvent -> controller
                .onEventSaveLocallyButton());


        model.addListener(new Listener(){
            @Override
            public void finishSearch(){
                JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
                listOfPages(searchOptionsMenu);
                searchOptionsMenu.show(searchBoxInWikipedia, searchBoxInWikipedia.getX(), searchBoxInWikipedia.getY());
            }

            @Override
            public void notifyErrorLoadingDataBase(SQLException sqlException) {

            }

            public void fetchPage(){
                JsonElement searchResultContent = model.getSearchResultContent();
                if (searchResultContent == null) {
                    lastSearchedText = "No Results";
                } else {
                    lastSearchedText = "<h1>" + selectedSearchResult.title + "</h1>";
                    selectedResultTitle = selectedSearchResult.title;
                    /** termino buscado y pagina, falta poner la fecha */
                    controller.saveHistory(searchBoxInWikipedia.getText(),selectedResultTitle);

                    lastSearchedText += searchResultContent.getAsString().replace("\\n", "\n");
                    lastSearchedText = textToHtml(lastSearchedText);
                }
                searchInWikipediaDisplayPane.setText(lastSearchedText);
                searchInWikipediaDisplayPane.setCaretPosition(0);
                stopWorkingStatus();
            }
            public void notifyViewSaveCorrect(){ showSaveCorrect();}

            public void notifyViewErrorSavingLocally(SQLException sqlException){ showErrorSavingLocally(sqlException);}

            public void didUpdateListener(){}

            @Override
            public void didSaveInHistoryListener() {

            }

            @Override
            public void notifyErrorGettingUserHistory(SQLException sqlException) {

            }

            @Override
            public void notifyViewDeleteCorrect() {

            }

            @Override
            public void notifyViewErrorDeleting(SQLException sqlException) {

            }
        });
    }
    public void showErrorSearching(IOException ioException){
        JOptionPane.showMessageDialog(errorMessage,"Error searching!");
    }
    private void showErrorSavingLocally(SQLException sqlException){JOptionPane.showMessageDialog(errorMessage,"Error saving locally");}
    private void showSaveCorrect(){JOptionPane.showMessageDialog(informationMessage, "Save correctly!");}
    private JTextField getSearchBoxInWikipedia(){
        return this.searchBoxInWikipedia;
    }
    public String getLastSearchedText(){ return this.lastSearchedText;}
    private void listOfPages(JPopupMenu searchOptionsMenu){
        for (JsonElement jsonElement : model.getQuery()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            searchResultTitle = jsonObject.get("title").getAsString();
            searchResultPageId = jsonObject.get("pageid").getAsString();
            searchResultSnippet = jsonObject.get("snippet").getAsString();
            SearchResult searchResult = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            searchOptionsMenu.add(searchResult);
            searchResult.addActionListener(actionEvent -> {
                startWorkingStatus();
                selectedSearchResult = searchResult;

                model.getPageIntroduction(searchResult);
            });
        }
    }
    public String getSelectedResultTitle(){ return selectedResultTitle;}

    public void setTabbedPane(JTabbedPane thisTabbedPane){
        this.tabbedPane = thisTabbedPane;
    }
    public void redirectToThisTab(){
        tabbedPane.setSelectedIndex(1);
    }
    public void setSearchBoxInWikipedia(String content){
        this.searchBoxInWikipedia.setText(content);
    }
}
