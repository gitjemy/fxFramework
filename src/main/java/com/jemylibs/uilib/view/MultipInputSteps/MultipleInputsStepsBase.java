package com.jemylibs.uilib.view.MultipInputSteps;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class MultipleInputsStepsBase extends VBox {

    protected final VBox vBox;
    protected final Label StepTitle;
    protected final StackPane StackBane;
    protected final BorderPane borderPane;
    protected final Button BackBut;
    protected final Button NextBut;

    public MultipleInputsStepsBase() {

        vBox = new VBox();
        StepTitle = new Label();
        StackBane = new StackPane();
        borderPane = new BorderPane();
        BackBut = new Button();
        NextBut = new Button();

        setSpacing(5.0);
        setStyle("-fx-background-color: rgba(0,0,0,0);");
        setPadding(new Insets(5.0));

        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.getStyleClass().add("ControlsGroupWhite");

        StepTitle.setMaxWidth(Double.MAX_VALUE);
        StepTitle.setText("Label");
        StepTitle.setTextFill(javafx.scene.paint.Color.valueOf("#11111e"));
        StepTitle.setFont(new Font("JF Flat Regular", 19.0));
        VBox.setMargin(StepTitle, new Insets(0.0));
        StepTitle.setPadding(new Insets(10.0, 30.0, 10.0, 30.0));

        VBox.setVgrow(StackBane, Priority.ALWAYS);

        borderPane.getStyleClass().add("ControlsGroupWhite");

        BorderPane.setAlignment(BackBut, Pos.CENTER);
        BackBut.setMnemonicParsing(false);
        BackBut.setText("السابق");
        borderPane.setLeft(BackBut);

        BorderPane.setAlignment(NextBut, Pos.CENTER);
        NextBut.setMnemonicParsing(false);
        NextBut.setText("التالي");
        borderPane.setRight(NextBut);

        vBox.getChildren().add(StepTitle);
        vBox.getChildren().add(StackBane);
        getChildren().add(vBox);
        getChildren().add(borderPane);
    }
}
