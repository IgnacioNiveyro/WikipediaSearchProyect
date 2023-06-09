package UnitTest;

import controller.VideoGameInfoControllerImpl;
import model.DataBase;
import model.HTMLConverter;
import model.ModelDB;
import model.VideoGameInfoModelImpl;
import org.junit.Before;
import views.*;
import org.junit.Test;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitTest {
    private HistoryViewImpl historyViewImpl;
    private SearchInWikipediaViewImpl searchInWikipediaViewImpl;
    private StoredInfoViewImpl storedInfoViewImpl;
    private HTMLConverter html;
    private DataBase dataBase;
    private ModelDB modelDB;
    private VideoGameInfoModelImpl model;
    private VideoGameInfoControllerImpl controller;

    @Before
    public void setUp() throws Exception{

        historyViewImpl = mock(HistoryViewImpl.class);
        searchInWikipediaViewImpl = mock(SearchInWikipediaViewImpl.class);
        storedInfoViewImpl = mock(StoredInfoViewImpl.class);
        model = mock(VideoGameInfoModelImpl.class);
        controller = new VideoGameInfoControllerImpl(model);
        html = new HTMLConverter();
        dataBase = mock(DataBase.class);
        modelDB = mock(ModelDB.class);
        modelDB.setDataBase(dataBase);
        controller.setModel(model);
        controller.setModelDB(modelDB);

        controller.setHistoryView(historyViewImpl);
        controller.setStoredInfoView(storedInfoViewImpl);
        controller.setSearchInWikipediaView(searchInWikipediaViewImpl);

    }
    @Test
    public void testSimpleSearch() throws IOException{
        controller.onEventSearch("L.A. Noire");
        verify(model, times(1)).searchNow("L.A. Noire");
    }
    @Test
    public void testRedoSearch() throws IOException{
        String selectedContentFromComboBox = "la noire / L.A. Noire / Thu Jun 08 01:17:40 ART 2023";
        controller.searchGameFromHistory(selectedContentFromComboBox);
        verify(model,times(1)).redoSearch("L.A. Noire");
    }
    @Test
    public void testSimpleStorage(){
        controller.onEventSaveLocallyButton("Title of game","Content of the game");
        verify(modelDB, times(1)).saveInfo("Title of game", "Content of the game");
    }
    @Test
    public void testSaveHistory(){
        controller.saveHistory("la noire", "L.A. Noire");
        verify(modelDB, times(1)).saveHistory("la noire", "L.A. Noire");
    }
    @Test
    public void testSimpleUpdate(){
        controller.saveData("L.A. Noire", "");
        verify(modelDB, times(1)).saveInfo("L.A. Noire", "");
    }
    @Test
    public void testDeleted() throws SQLException{
        controller.onEventDelete(1, "L.A. Noire");
        verify(modelDB,times(1)).onEventDeleteItem("L.A. Noire");
    }

    /** Test que no anduvieron*/
    @Test
    public void testGetTitleOfDataBase() throws SQLException{
        ArrayList<String> titles = new ArrayList<>();
        when(dataBase.getTitles()).thenReturn(titles);
        System.out.println(dataBase.getTitles());
        assertEquals(modelDB.getTitleOfDataBase(),titles);
    }
    @Test
    public void testGetContent() throws SQLException{
        String contentExpected = "Text about L.A. Noire";
        when(dataBase.getContent("L.A. Noire")).thenReturn(contentExpected);
        String expectedContent = modelDB.getContent("L.A. Noire");
        System.out.println("mi contenido es: "+expectedContent);
        assertEquals(expectedContent, contentExpected);
    }
}
