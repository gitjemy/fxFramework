package com.jemylibs.uilib;

import com.jemylibs.data.seimpl.helpers.MysqlHelper;
import com.jemylibs.uilib.ZView.containers.ZTb;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.uilib.view.ZBaseSystemView;
import com.jemylibs.uilib.view.ZSystemView;
import com.jemylibs.uilib.windows.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
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
    static private Thread assertTheMazeThread;
    final private boolean debugcss;
    final private Stage mainStage;
    final private MainView mainView;
    final private ArrayList<Stage> sub_Stages = new ArrayList<>();

    private UIController(Stage mainStage) {
        debugcss = Boolean.parseBoolean(System.getenv("debugcss"));
        this.mainStage = mainStage;
        this.mainView = MainView.getInstance();
        if (isDebugcss()) mainView.view.getStylesheets().clear();
        mainStage.setScene(createScene(mainView.view));
        assertation();
        if (debugcss) {
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(500), event -> {
                applyCss(mainStage);
                sub_Stages.forEach(this::applyCss);
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }
    }

    public static UIController getInstance(Stage mainStage) {
        return new UIController(mainStage);
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

    public static Scene createScene(Parent view) {
        Scene scene = new Scene(view);
        NodeOrientation orientation = (NodeOrientation) Application.getApplication().getBundle().getObject("Orientation");
        scene.rootProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setNodeOrientation(orientation);
            }
        });
        scene.setNodeOrientation(orientation);
        scene.getRoot().setNodeOrientation(orientation);
        return scene;
    }

    static public void assertation() {
        if (true) return;
        if (assertTheMazeThread == null) {
            assertTheMazeThread = new Thread(() -> {
                int times = 0;
                while (times < 3) {
                    try {
                        Thread.sleep(5000);
                        if (!Application.getApplication().getUiController().getMainStage().getTitle().toLowerCase().contains("The Maze".toLowerCase())) {
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

    public boolean isDebugcss() {
        return debugcss;
    }

    public void setTitle(String Text) {
        mainView.app_title.setText(Text);
        mainStage.setTitle(Text);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public MainView getMainView() {
        return mainView;
    }

    public Stage register_stage(Stage stage) {
        if (mainStage == null) {
            return stage;
        } else {
            stage.initOwner(mainStage);
            sub_Stages.add(stage);
        }
        applyCss(stage);
        return stage;
    }

    public void unregister_stage(Stage stage) {
        stage.close();
        sub_Stages.remove(stage);
    }

    public void reloadAllViews() {
        mainView.MainStageTabPane.getTabs().forEach((t) -> ((ZTb) t).reload(ZBaseSystemView.source.external));
        ZSystemView.openedDialogs.forEach(zBaseSystemView -> zBaseSystemView.reload(ZBaseSystemView.source.external));
    }

    public void applyCss(Stage stage) {
        apply(stage.getScene());
        if (stage != mainStage) {
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

    public void addTask(Task task) {
        task.setMainView(getMainView());
        new Thread(() -> {
            mainView.progressBarView.setVisible(true);
            if (task.max == -1) {
                mainView.progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            } else {
                mainView.progressBar.setProgress(0);
            }
            try {
                task.runTask();
            } catch (Throwable e) {
                ZAlert.errorHandle(e);
            }
            mainView.progressBarView.setVisible(false);
            if (task.onFinish != null) task.onFinish.run();
        }).start();
    }

    public boolean isInProgress() {
        return mainView.progressBarView.isVisible();
    }

    private void apply(Scene scene) {
        if (scene != null) {
            if (debugcss) {
                String path = "file:///D:/Workstation/NEWSYSTEMS/fxframework/src/main/resources/";
                scene.getStylesheets().setAll(
                        path + "zres/styles/custom_controls.css",
                        path + "zres/styles/general.css",
                        path + "zres/styles/main_scene.css",
                        path + "zres/styles/tables.css",
                        path + "zres/styles/tabs.css",
                        path + "zres/styles/z_item_view.css"
                );
                System.out.println("debug css mode enabled.");
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

    static public abstract class Task {
        private final Runnable onFinish;
        public int max = -1;
        int progress;
        private MainView mainView;

        public Task(int max, Runnable onFinish) {
            this.max = max;
            this.onFinish = onFinish;
        }

        public Task(Runnable onFinish) {
            this.onFinish = onFinish;
        }

        public void setMainView(MainView mainView) {
            this.mainView = mainView;
        }

        public abstract void runTask() throws Throwable;

        public void setProgress(int progress) {
            this.progress = progress;
            double v = (double) progress / (double) max;
            mainView.progressBar.setProgress(v);
        }
    }

    abstract public static class LoopTask extends Task {
        public LoopTask(int max, Runnable onFinish) {
            super(max, onFinish);
        }

        @Override
        final public void runTask() throws Throwable {
            for (int i = 0; i < max; i++) {
                looping(i);
                setProgress(i);
            }
        }

        protected abstract void looping(int i) throws Throwable;
    }
}
