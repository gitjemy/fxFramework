package com.jemylibs.uilib.ctrls.inputs;

import com.jemylibs.uilib.utilities.alert.ZAlert;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class DbImage extends VBox {

    final ImageView imageView = new ImageView();
    public Button selectFile = new Button("...");
    public Button deleteImage = new Button("X");
    File imagePath;

    public DbImage(String title, String imagesDir, String imageId) {
        File dir = new File(imagesDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        imagePath = new File(dir.getAbsolutePath() + "/" + imageId);


        deleteImage.setOnAction(event -> {
            try {
                Files.deleteIfExists(imagePath.toPath());
                refresh();
            } catch (IOException e) {
                ZAlert.errorHandle(e);
            }
        });
        HBox hBox = new HBox(selectFile, deleteImage);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        Rectangle rectangle = new Rectangle();
        int imageSize = 160;
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setWidth(imageSize);
        rectangle.setHeight(imageSize);

        //Setting the height and width of the arc
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);

        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);
        imageView.setPreserveRatio(true);

        double v = imageSize / 2d;
        imageView.setClip(new Circle(v, v, v));

        getChildren().addAll(imageView, hBox);

        refresh();
        selectFile.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle(title);

            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            chooser.getExtensionFilters().add(extFilter);

            File file = chooser.showOpenDialog(getScene().getWindow());

            if (file != null) {
                try {
                    Files.copy(file.toPath(), imagePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    refresh();
                } catch (IOException e) {
                    ZAlert.errorHandle(e);
                }
            }
        });
        imageView.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(10));
    }

    private void refresh() {
        try {
            Image im = new Image(imagePath.toURL().toString(), false);
            imageView.setImage(im);
            double w = im.getWidth();
            double h = im.getHeight();
            imageView.setViewport(new Rectangle2D((w - h) / 2, 0, h, h));
        } catch (MalformedURLException e) {
            ZAlert.errorHandle(e);
        }
    }
}
