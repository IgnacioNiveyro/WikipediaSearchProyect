
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import dyds.videogameInfo.fulllogic.DataBase;
import views.*;


public class Main {
    private static DataBase dataBase;

    public static void main(String[] args) {
        dataBase = new DataBase();
        /** pregunta para Gotti, es correcto que
         * al método loadDataBase le agregue en el encabezado
         * throws SQLException y lo agarre en el main ?*/
        dataBase.loadDatabase();

        SearchInWikipediaView wikiView = new SearchInWikipediaViewImpl(null, null);
        StoredInfoView storedInfo = new StoredInfoViewImpl(null, null);

        ArrayList<BaseView> allMyTabs = new ArrayList<BaseView>();
        allMyTabs.add(wikiView);
        allMyTabs.add(storedInfo);
        showView(allMyTabs);

    }

    public static void showView(ArrayList<BaseView> allMyTabs){
        JFrame principalPanelView = new JFrame("Video Game Info");
        principalPanelView.setVisible(true);
        principalPanelView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        principalPanelView.pack();

        principalPanelView.setSize(500, 500);
        JTabbedPane tabbedPane1 = new JTabbedPane();
        principalPanelView.add(tabbedPane1);

        addTabbedPane(allMyTabs,tabbedPane1);
    }

    private static void addTabbedPane(ArrayList<BaseView> containerList, JTabbedPane tabbedPane){
        for(BaseView container: containerList)
            tabbedPane.addTab(container.getTabbedName(), container.getContent());
    }
}


