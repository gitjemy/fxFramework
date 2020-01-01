package com.jemylibs.uilib.ctrls.panes.itemview;

import com.jemylibs.uilib.utilities.alert.ZAlert;

import java.util.function.Function;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ImageItem<T> extends Item<T, StackPane> {
    final ImageView imageView;
    final private Function<T, String> image_path;

    public ImageItem(String text, Function<T, String> image_path, float height) {
        super(text, new StackPane());
        imageView = new ImageView();
        getView().getChildren().add(imageView);
        Rectangle rectangle = new Rectangle();
        this.image_path = image_path;


        getView().setAlignment(Pos.CENTER);
        imageView.setFitHeight(height);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setWidth(height);
        rectangle.setHeight(height);

        //Setting the height and width of the arc
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);

        double v = height / 2d;
        imageView.setClip(new Circle(v, v, v));
        getView().setPrefHeight(height + 5);


    }

    @Override
    final public void item_update(T t) {
        try {
            String image_url = image_path.apply(t);
            if (image_url != null && !image_url.isEmpty()) {
                Image image = new Image(image_url);
                imageView.setImage(image);

                double w = image.getWidth();
                double h = image.getHeight();
                if (w > h) {
                    imageView.setViewport(new Rectangle2D((w - h) / 2, 0, h, h));
                } else {
                    imageView.setViewport(new Rectangle2D(0, (h - w) / 2, w, w));
                }
            }
        } catch (Throwable throwable) {
            ZAlert.errorHandle(throwable);
        }
    }
}
