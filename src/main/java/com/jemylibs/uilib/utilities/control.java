package com.jemylibs.uilib.utilities;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class control {

    public static void fireOnEnterPressed(Parent parent, Runnable onEnter, Runnable onEscape) {
        parent.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && onEnter != null) {
                onEnter.run();
            } else if (event.getCode() == KeyCode.ESCAPE && onEscape != null) {
                onEscape.run();
            }
        });
    }
}
