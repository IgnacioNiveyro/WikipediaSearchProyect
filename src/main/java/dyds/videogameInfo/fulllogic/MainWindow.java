package dyds.videogameInfo.fulllogic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainWindow {
  private JTextField searchBoxInWikipedia;
  private JButton searchButton;
  private JPanel contentPane;
  private JTextPane searchInWikipediaDisplayPane;
  private JButton saveLocallyButton;
  private JTabbedPane tabbedPane;
  private JPanel searchPanel;
  private JPanel storagePane;
  private JComboBox searchBoxInStoredInfo;
  private JTextPane storedInfoDisplayPane;
  private JScrollPane storedInfoGeneralPane;
  private JScrollPane searchInWikipediaGeneralPane;

  DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
  String selectedResultTitle = null; //For storage purposes, se below that it may not coincide with the searched term
  String text = ""; //Last searched text! this variable is central for everything

  /** --- */

  Object [] sortedTitles;

  public MainWindow() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/w/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
    WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

    searchInWikipediaDisplayPane.setContentType("text/html");
    storedInfoDisplayPane.setContentType("text/html");

    /** Comentario nacho clean code
     * Sirve para cuando recién inicias la aplicación que en searchBoxInStoredInfo aparezcan los títulos guardados previamente.
     *
    searchBoxInStoredInfo.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
     */

    sortedTitles = sortDatabaseTitles();
    searchBoxInStoredInfo.setModel(new DefaultComboBoxModel(sortedTitles));

    // From here on is where the magic happends: querying wikipedia, showing results, etc.
    searchButton.addActionListener(e -> new Thread(() -> {
              //This may take some time, dear user be patient in the meanwhile!
              setWorkingStatus();
              // get from service
              Response<String> callForSearchResponse;
              try {

                //ToGustavo: First, lets search for the term in Wikipedia
                callForSearchResponse = searchAPI.searchForTerm(searchBoxInWikipedia.getText() + " articletopic:\"video-games\"").execute();

                //Show the result for testing reasons, if it works, dont forget to delete!
                //System.out.println("JSON " + callForSearchResponse.body());

                //ToGustavo: This is the code process the search results for the query
                //Each result is stored in a JsonArray
                /** Gson convierte objetos Java en su representación JSON (JavaScript Object Notation) y viceversa. */
                Gson gson = new Gson();
                /** A partir de ese punto, se puede acceder y manipular los datos dentro del objeto JsonObject utilizando los métodos proporcionados por la biblioteca Gson. */
                JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
                /** toma el objeto JSON contenido en jobj bajo la clave "query" y lo asigna a la variable query como un objeto JSON completo. */
                JsonObject query = jobj.get("query").getAsJsonObject();

                Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
                JsonArray jsonResults = query.get("search").getAsJsonArray();

                //ToGustavo: shows each result in the JSonArry in a Popupmenu
                JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
                for (JsonElement je : jsonResults) {
                  JsonObject searchResult = je.getAsJsonObject();
                  String searchResultTitle = searchResult.get("title").getAsString();
                  String searchResultPageId = searchResult.get("pageid").getAsString();
                  String searchResultSnippet = searchResult.get("snippet").getAsString();

                  SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
                  searchOptionsMenu.add(sr);

                  //ToGustavo: Adding an event to retrive the wikipage when the user clicks an item in the Popupmenu
                  sr.addActionListener(actionEvent -> {
                    try {

                      setWorkingStatus();
                      Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();

                      System.out.println("JSON " + callForPageResponse.body());

                      //ToGustavo: This is similar to the code above, but here we parse the wikipage answer.
                      JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
                      JsonObject query2 = jobj2.get("query").getAsJsonObject();
                      JsonObject pages = query2.get("pages").getAsJsonObject();
                      Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                      Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                      JsonObject page = first.getValue().getAsJsonObject();
                      JsonElement searchResultExtract2 = page.get("extract");
                      if (searchResultExtract2 == null) {
                        text = "No Results";
                      } else {
                        text = "<h1>" + sr.title + "</h1>";
                        selectedResultTitle = sr.title;
                        text += searchResultExtract2.getAsString().replace("\\n", "\n");
                        text = textToHtml(text);

                        //Not yet...
                        //text+="\n" + "<a href=https://en.wikipedia.org/?curid=" + searchResultPageId +">View Full Article</a>";
                      }
                      searchInWikipediaDisplayPane.setText(text);
                      searchInWikipediaDisplayPane.setCaretPosition(0);
                      //Back to edit time!
                      setWatingStatus();
                    } catch (Exception e12) {
                      System.out.println(e12.getMessage());
                    }
                  });
                }
                searchOptionsMenu.show(searchBoxInWikipedia, searchBoxInWikipedia.getX(), searchBoxInWikipedia.getY());
              } catch (IOException e1) {
                e1.printStackTrace();
              }

              //Now you can keep searching stuff!
              setWatingStatus();
    }).start());

    saveLocallyButton.addActionListener(actionEvent -> {
      if(text != ""){
        // save to DB  <o/
        DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
        searchBoxInStoredInfo.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
      }
    });

    searchBoxInStoredInfo.addActionListener(actionEvent -> storedInfoDisplayPane.setText(textToHtml(DataBase.getExtract(searchBoxInStoredInfo.getSelectedItem().toString()))));

    JPopupMenu storedInfoPopup = new JPopupMenu();

    JMenuItem deleteItem = new JMenuItem("Delete!");
    deleteItem.addActionListener(actionEvent -> {
        if(searchBoxInStoredInfo.getSelectedIndex() > -1){
          DataBase.deleteEntry(searchBoxInStoredInfo.getSelectedItem().toString());
          searchBoxInStoredInfo.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
          storedInfoDisplayPane.setText("");
        }
    });
    storedInfoPopup.add(deleteItem);

    JMenuItem saveItem = new JMenuItem("Save Changes!");
    saveItem.addActionListener(actionEvent -> {
        // save to DB  <o/
        DataBase.saveInfo(searchBoxInStoredInfo.getSelectedItem().toString().replace("'", "`"), storedInfoDisplayPane.getText());  //Dont forget the ' sql problem
        //searchBoxInStoredInfo.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
    });
    storedInfoPopup.add(saveItem);

    storedInfoDisplayPane.setComponentPopupMenu(storedInfoPopup);


  }

  private Object[] sortDatabaseTitles(){
    return DataBase.getTitles().stream().sorted().toArray();
  }
  private void setWorkingStatus() {
    for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
    searchInWikipediaDisplayPane.setEnabled(false);
  }

  private void setWatingStatus() {
    for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
    searchInWikipediaDisplayPane.setEnabled(true);
  }

  public static void main(String[] args) {

    JFrame frame = new JFrame("Video Game Info");
    frame.setContentPane(new MainWindow().contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    DataBase.loadDatabase();
    DataBase.saveInfo("test", "sarasa");


    System.out.println(DataBase.getExtract("test"));
    System.out.println(DataBase.getExtract("nada"));
  }

  public static String textToHtml(String text) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String fixedText = text
        .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..

    builder.append(fixedText);

    builder.append("</font>");

    return builder.toString();
  }

}
