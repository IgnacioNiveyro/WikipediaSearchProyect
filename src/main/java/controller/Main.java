package controller;

import javax.swing.*;
import java.util.ArrayList;
import model.VideoGameInfoModel;
import model.VideoGameInfoModelImpl;
import views.*;



public class Main {

    public static void main(String[] args) {
        VideoGameInfoModel model = new VideoGameInfoModelImpl();

        VideoGameInfoController controller = new VideoGameInfoControllerImpl(model);

        JTabbedPane tabbedPane = new JTabbedPane();
        SearchInWikipediaView wikiView = new SearchInWikipediaViewImpl(controller, model);
        controller.setSearchInWikipediaView(wikiView);
        wikiView.setTabbedPane(tabbedPane);

        StoredInfoView storedInfo = new StoredInfoViewImpl(controller, model);
        controller.setStoredInfoView(storedInfo);

        HistoryView historyView = new HistoryViewImpl(controller, model);
        controller.setHistoryView(historyView);

        ArrayList<BaseView> allMyTabs = new ArrayList<BaseView>();
        allMyTabs.add(storedInfo);
        allMyTabs.add(wikiView);
        allMyTabs.add(historyView);
        showView(allMyTabs,tabbedPane);
    }

    public static void showView(ArrayList<BaseView> allMyTabs, JTabbedPane tabbedPane){
        JFrame principalPanelView = new JFrame("Video Game Info");
        principalPanelView.setVisible(true);
        principalPanelView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        principalPanelView.pack();

        principalPanelView.setSize(500, 500);

        principalPanelView.add(tabbedPane);

        addTabbedPane(allMyTabs,tabbedPane);
    }

    private static void addTabbedPane(ArrayList<BaseView> containerList, JTabbedPane tabbedPane){
        for(BaseView container: containerList)
            tabbedPane.addTab(container.getTabbedName(), container.getContent());
    }

}


