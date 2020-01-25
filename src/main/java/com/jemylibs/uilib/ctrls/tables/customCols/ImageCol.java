package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.function.Function;

public class ImageCol<T> extends col<T, String> {

    public ImageCol(String title, double PrefWidth, Function<T, Image> urlSupplier) {
        super(title);
        setSortable(false);
        if (PrefWidth != -1) {
            setPrefWidth(PrefWidth + 10);
        }

        setCellFactory(param -> new TableCell<T, String>() {

            final ImageView imageView = new ImageView();
            final HBox box = new HBox();
            Rectangle rectangle = new Rectangle();

            {
                box.setAlignment(Pos.CENTER);
                imageView.setFitHeight(PrefWidth);
                imageView.setFitWidth(PrefWidth);
                imageView.setPreserveRatio(true);

                rectangle.setX(0);
                rectangle.setY(0);
                rectangle.setWidth(PrefWidth);
                rectangle.setHeight(PrefWidth);

                //Setting the height and width of the arc
                rectangle.setArcWidth(10);
                rectangle.setArcHeight(10);
                setAlignment(Pos.CENTER);
                double v = PrefWidth / 2d;
                imageView.setClip(new Circle(v, v, v));
                setPrefHeight(PrefWidth + 5);
            }

            public T getCurrentItem() {
                return getTableView().getItems().get(getIndex());
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(imageView);
                    try {
                        Image image_url = urlSupplier.apply(getCurrentItem());
                        if (image_url != null) {
                            imageView.setImage(image_url);
                            double w = image_url.getWidth();
                            double h = image_url.getHeight();
                            if (w > h) {
                                imageView.setViewport(new Rectangle2D((w - h) / 2, 0, h, h));
                            } else {
                                imageView.setViewport(new Rectangle2D(0, (h - w) / 2, w, w));
                            }
                        }
                    } catch (Throwable throwable) {
                        setGraphic(null);
                    }
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    public ImageCol(String title, Function<T, Image> imageUrlSupplier) {
        this(title, 50, imageUrlSupplier);
    }
}

