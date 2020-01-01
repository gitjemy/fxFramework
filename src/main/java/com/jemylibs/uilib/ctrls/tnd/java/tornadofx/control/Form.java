package com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control;

import java.util.stream.Stream;

import javafx.scene.layout.VBox;

public class Form extends VBox {

    public Form() {
        getStyleClass().add("form");
    }

    public double getLabelContainerWidth() {
        return getFieldsets().flatMap(Fieldset::getFields)
                .map(Field::getLabelContainer)
                .mapToDouble(f -> f.prefWidth(-1))
                .max()
                .orElse(0);
    }

    public Stream<Fieldset> getFieldsets() {
        return getChildren().stream()
                .filter(c -> c instanceof Fieldset)
                .map(c -> (Fieldset) c);
    }

    public String getUserAgentStylesheet() {
        return Form.class.getResource("form.css").toExternalForm();
    }

    public Fieldset fieldset(String text) {
        Fieldset fieldset = new Fieldset(text);
        getChildren().add(fieldset);
        return fieldset;
    }

    public Fieldset fieldset() {
        Fieldset fieldset = new Fieldset();
        getChildren().add(fieldset);
        return fieldset;
    }
}
