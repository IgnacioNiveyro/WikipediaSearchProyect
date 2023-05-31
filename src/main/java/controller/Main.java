
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.VideoGameInfoController;
import controller.VideoGameInfoControllerImpl;
import dyds.videogameInfo.fulllogic.DataBase;
import model.VideoGameInfoModel;
import model.VideoGameInfoModelImpl;
import views.*;


public class Main {
    private static DataBase dataBase;

    public static void main(String[] args) {
        dataBase = new DataBase();
        /** pregunta para Gotti, es correcto que
         * al método loadDataBase le agregue en el encabezado
         * throws SQLException y lo agarre en el main ?*/
        dataBase.loadDatabase();

        VideoGameInfoModel model = new VideoGameInfoModelImpl();

        VideoGameInfoController controller = new VideoGameInfoControllerImpl(model);
        SearchInWikipediaView wikiView = new SearchInWikipediaViewImpl(controller, model);
        controller.setSearchInWikipediaView(wikiView);

        StoredInfoView storedInfo = new StoredInfoViewImpl(controller, model);
        controller.setStoredInfoView(storedInfo);

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


