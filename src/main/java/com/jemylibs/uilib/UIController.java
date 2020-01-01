package com.jemylibs.uilib;

import com.jemylibs.uilib.ZView.containers.ZTb;
import com.jemylibs.uilib.view.ZBaseSystemView;
import com.jemylibs.uilib.view.ZSystemView;
import com.jemylibs.uilib.windows.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.jemylibs.uilib.StylesManager.assertation;

public class UIController {
    public static Stage mainStage;
    public static MainView mainView;
    private static ArrayList<Stage> sub_Stages;

    static {
        assertation();


        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (StylesManager.debugcss) {
                StylesManager.applyCss(mainStage);
                if (sub_Stages != null) sub_Stages.forEach(StylesManager::applyCss);
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    static public Stage register_stage(Stage stage) {
        if (mainStage == null) {
            return stage;
        } else {
            stage.initOwner(mainStage);
            if (sub_Stages == null) {
                sub_Stages = new ArrayList<>();
                sub_Stages.add(stage);
            }
        }
        StylesManager.applyCss(stage);
        return stage;
    }

    public static void unregister_stage(Stage stage) {
        stage.close();
        sub_Stages.remove(stage);
    }

    public static void reloadAllViews() {
        mainView.MainStageTabPane.getTabs().forEach((t) -> ((ZTb) t).reload(ZBaseSystemView.source.external));
        ZSystemView.openedDialogs.forEach(zBaseSystemView -> zBaseSystemView.reload(ZBaseSystemView.source.external));
    }
}
