package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FIcon;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.MalformedURLException;
import java.util.function.Function;

public class ImageUrlCol<T> extends col<T, String> {

    public ImageUrlCol(String title, double prefWidth, FIcon emptyIcon, Function<T, File> urlSupplier) {
        super(title);
        setSortable(false);
        double v = prefWidth / 2d;

        setCellFactory(param -> new TableCell<T, String>() {
            ImageView imageView;
            Circle circle_null;

            void create() {
                imageView = new ImageView();
                imageView.setFitHeight(prefWidth);
                imageView.setFitWidth(prefWidth);
                imageView.setPreserveRatio(true);

                //Setting the height and width of the arc
                setAlignment(Pos.CENTER);
                Circle circle = new Circle(v, v, v);
                imageView.setClip(circle);
            }

            public T getCurrentItem() {
                return getTableView().getItems().get(getIndex());
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    File image_url = urlSupplier.apply(getCurrentItem());
                    try {
                        if (image_url != null && image_url.exists()) {
                            Image image = new Image(image_url.toURL().toString());
                            if (imageView == null) create();
                            imageView.setImage(image);
                            double w = image.getWidth();
                            double h = image.getHeight();
                            if (w > h) {
                                imageView.setViewport(new Rectangle2D((w - h) / 2, 0, h, h));
                            } else {
                                imageView.setViewport(new Rectangle2D(0, (h - w) / 2, w, w));
                            }
                            setGraphic(imageView);
                        } else {
                            forEmpty();
                        }
                    } catch (MalformedURLException e) {
                        forEmpty();
                    }
                } else {
                    forNull();
                }
            }

            void forEmpty() {
                if (emptyIcon != null) {
                    setGraphic(emptyIcon.mk((int) prefWidth, new Color(0, 0, 0, .75)));
                } else {
                    forNull();
                }
            }

            void forNull() {
                if (circle_null == null) circle_null = new Circle(v, v, v, new Color(0, 0, 0, 0));
                setGraphic(circle_null);
            }

        });
        getStyleClass().add("");
    }
}

