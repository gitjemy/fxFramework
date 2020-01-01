package com.jemylibs.uilib.view.MultipInputSteps;

import com.jemylibs.uilib.ZView.containers.Dialog;
import com.jemylibs.uilib.utilities.alert.ZAlert;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;

public abstract class MultipleInputsSteps extends MultipleInputsStepsBase {

    public Dialog DialogStage = new Dialog("") {
        @Override
        protected Parent getTheView() {
            return this.getView();
        }
    };

    ObservableList<InputStep> Steps;
    SimpleIntegerProperty Index;

    public MultipleInputsSteps() {
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        this.Steps = FXCollections.observableArrayList();
        initSteps(Steps);
        Index = new SimpleIntegerProperty(-1);
        BackBut.setOnAction(v -> {
            Index.set(Index.getValue() - 1);
        });
        Index.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > Steps.size() - 1) {
                Index.setValue(0);
                return;
            } else if (newValue.intValue() < 0) {
                Index.setValue(Steps.size() - 1);
                return;
            }
            StackBane.getChildren().setAll(Steps.get(newValue.intValue()));
            StepTitle.setText(Steps.get(newValue.intValue()).Title);

            Platform.runLater(() -> {
                this.DialogStage.getStage().setTitle(StepTitle.getText());
            });

            if (newValue.intValue() == Steps.size() - 1) {
                NextBut.setText("انهاء");
                NextBut.setOnAction(v -> {
                    try {
                        Finish();
                    } catch (Exception ex) {
                        ZAlert.errorHandle(ex);
                    }
                });
            } else {
                NextBut.setText("التالي");
                NextBut.setOnAction(v -> {
                    try {
                        Index.set(Index.getValue() + 1);
                    } catch (Exception ex) {
                        ZAlert.errorHandle(ex);
                    }
                });
            }
            if (newValue.intValue() == 0) {
                BackBut.setOpacity(0);
            } else {
                BackBut.setOpacity(1);
            }
            DialogStage.getStage().setOnShowing(va -> {
                Steps.forEach(a -> {
                    a.PropertyPane.setPrefWidth(a.PropertyPane.getPrefWidth());
                });
            });
        });
        Index.set(0);
    }

    private void Finish() {
        DialogStage.getStage().close();
    }

    public abstract void initSteps(ObservableList<InputStep> Steps);
}
