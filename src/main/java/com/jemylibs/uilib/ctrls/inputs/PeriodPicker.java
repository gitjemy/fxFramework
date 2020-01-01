package com.jemylibs.uilib.ctrls.inputs;

import com.jemylibs.sedb.Query.ZQ.Between;
import com.jemylibs.sedb.ZCOL._TimeStamp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PeriodPicker extends HBox {

    private DatePicker FromDate = new DatePicker(), ToDate = new DatePicker();
    private EventHandler<ActionEvent> Event = null;

    {
        FromDate.setOnAction(e -> {
            if (Event != null) Event.handle(e);
        });
        ToDate.setOnAction(e -> {
            if (Event != null) Event.handle(e);
        });

        Label FDL = new Label(" من تاريخ : ");
        Label TDL = new Label("إلى تاريخ : ");
        FDL.setMinWidth(USE_PREF_SIZE);
        TDL.setMinWidth(USE_PREF_SIZE);
        setSpacing(5);
        setMaxWidth(400);
        setAlignment(Pos.CENTER);
        getChildren().addAll(FDL, FromDate, TDL, ToDate);


        //cell factory
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate Fr = FromDate.getValue();
                LocalDate to = ToDate.getValue();
                if (Fr != null && to != null) {
                    if (item.isAfter(Fr.minusDays(1)) && item.isBefore(to.plusDays(1))) {
                        setStyle("-fx-background-color: #428ad2;-fx-text-fill: #fff;");
                    }
                }
                if (Fr != null) {
                    setTooltip(new Tooltip("من " + Fr.toString() + " الى" + item.toString()));
                }
            }
        };
        ToDate.setDayCellFactory(dayCellFactory);
        FromDate.setDayCellFactory(dayCellFactory);
    }

    public PeriodPicker() {
        super();
    }

    public PeriodPicker(double spacing) {
        super(spacing);
    }

    public PeriodPicker(Node... children) {
        super(children);
    }

    public PeriodPicker(double spacing, Node... children) {
        super(spacing, children);
    }

    public void setOnPeriodChange(EventHandler<ActionEvent> E) {
        this.Event = E;
    }

    public Between GetMysqlCondition(_TimeStamp MysqlColumn) {
        LocalDateTime from = null, to = null;
        if (FromDate.getValue() != null) from = FromDate.getValue().atStartOfDay();
        if (ToDate.getValue() != null) to = ToDate.getValue().plusDays(1).atStartOfDay();
        return new Between(MysqlColumn, from, to);
    }

    public void toVertical() {
        Label FDL = new Label(" من تاريخ : ");
        Label TDL = new Label("إلى تاريخ : ");
        FDL.setMinWidth(USE_PREF_SIZE);
        TDL.setMinWidth(USE_PREF_SIZE);

        HBox FBox = new HBox(FDL, FromDate);
        HBox TBox = new HBox(TDL, ToDate);
        FBox.setSpacing(5);
        TBox.setSpacing(5);
        HBox.setHgrow(FromDate, Priority.ALWAYS);
        HBox.setHgrow(ToDate, Priority.ALWAYS);
        FromDate.setMaxWidth(Double.MAX_VALUE);
        ToDate.setMaxWidth(Double.MAX_VALUE);
        VBox vBox = new VBox(FBox, TBox);
        vBox.setSpacing(5);
        HBox.setHgrow(vBox, Priority.ALWAYS);
        getChildren().setAll(vBox);
    }

    public void setPeriod(LocalDate from, LocalDate to) {
        this.FromDate.setValue(from);
        this.ToDate.setValue(to);
    }

    public LocalDateTime getFrom() {
        return FromDate.getValue().atStartOfDay();
    }

    public LocalDateTime getTo() {
        return ToDate.getValue().plusDays(1).atStartOfDay();
    }
}
