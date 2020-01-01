package com.jemylibs.uilib.ctrls;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Pages extends HBox {

    boolean OrderedByDesc = false;

    IntegerProperty PageIndex = new SimpleIntegerProperty(0);
    IntegerProperty MaxPageIndex = new SimpleIntegerProperty(0);

    Button Next;
    Button Back;
    Button Last;
    Button First;

    public Pages() {
        super();
        Init();
    }

    public Pages(double spacing) {
        super(spacing);
        Init();
    }

    public Pages(Node... children) {
        super(children);
        Init();
    }

    public Pages(double spacing, Node... children) {
        super(spacing, children);
        Init();
    }

    private void Init() {
        getStyleClass().add("pages");

        Next = new Button("التالي");
        Back = new Button("السابق");

        Last = new Button("الاحدث");
        First = new Button("الاقدم");

        Label PageIndexLabel = new Label();
        PageIndexLabel.setMinWidth(30);
        PageIndexLabel.setMaxHeight(500);
        PageIndexLabel.setText(PageIndex.get() + "");
        PageIndexLabel.setAlignment(Pos.CENTER);

//        PageIndexLabel.focusedProperty().addListener((obs, oldv, newv) -> {
//            if (newv) {
//                int LEs = (PageIndex.get() + "").length() + 1;
//                PageIndexLabel.setText(PageIndexLabel.getText().substring(LEs));
//            }
//        });
//        PageIndexLabel.setOnKeyTyped(va -> {
//            if (va.getCode() == KeyCode.ENTER) {
//                int PI = Integer.valueOf(PageIndexLabel.getText());
//                if (PI <= MaxPageIndex) {
//                    PageIndex.toDbKey(PI);
//                }
//            }
//        });
        Next.setOnAction(value -> {
            PageIndex.set(PageIndex.get() + 1);
        });

        Back.setOnAction(value -> {
            PageIndex.set(PageIndex.get() - 1);
        });

        Last.setOnAction(value -> {
            ToLastPage();
        });

        First.setOnAction(value -> {
            ToFirstPage();
        });

        PageIndex.addListener((obs, oldIndex, newIndex) -> {
            if (newIndex.intValue() <= 0) {
                Back.setDisable(true);
                First.setDisable(true);

            } else {
                Back.setDisable(false);
                First.setDisable(false);
            }

            if (newIndex.intValue() >= MaxPageIndex.get() - 1) {
                Last.setDisable(true);
                Next.setDisable(true);
            } else {
                Last.setDisable(false);
                Next.setDisable(false);
            }
            PageIndexLabel.setText((newIndex.intValue() + 1) + "/" + MaxPageIndex.get());
        });

        MaxPageIndex.addListener((obs, oldIndex, newIndex) -> {
            if (PageIndex.intValue() <= 0) {
                First.setDisable(true);
                Back.setDisable(true);
            } else {
                First.setDisable(false);
                Back.setDisable(false);
            }
            if (PageIndex.intValue() >= newIndex.intValue() - 1) {
                Last.setDisable(true);
                Next.setDisable(true);
            } else {
                Last.setDisable(false);
                Next.setDisable(false);
            }
            ToLastPage();
            PageIndexLabel.setText((PageIndex.get() + 1) + "/" + newIndex.intValue());
        });

        setSpacing(5);
        getChildren().addAll(First, Back, PageIndexLabel, Next, Last);
    }

    public IntegerProperty getPageIndexProperty() {
        return PageIndex;
    }

    public int getPageIndex() {
        return PageIndex.get();
    }

    public IntegerProperty getMaxPageIndexProperty() {
        return MaxPageIndex;
    }

    public void setMaxPageIndex(int MaxPageIndex) {
        this.MaxPageIndex.set(MaxPageIndex);
    }

    public void ToLastPage() {
        PageIndex.set(MaxPageIndex.get() - 1);
    }

    public void ToFirstPage() {
        PageIndex.set(0);
    }

    public boolean isLastPage() {
        return PageIndex.intValue() == MaxPageIndex.get() - 1;
    }

    public void setOrderedByDesc(boolean OrderedByDesc) {
        this.OrderedByDesc = OrderedByDesc;
        if (OrderedByDesc) {
            Next.setText("السابق");
            Back.setText("التالي");

            Last.setText("الاقدم");
            First.setText("الاحدث");
        } else {
            Next.setText("التالي");
            Back.setText("السابق");

            Last.setText("الاحدث");
            First.setText("الاقدم");
        }
    }
}
