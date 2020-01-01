package com.jemylibs.uilib.ZView;

import com.jemylibs.uilib.utilities.alert.ZAlert;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public interface ZFxml extends Initializable {

    static <U extends ZFxml> U get(String path) {
        U controller = null;
        try {
            FXMLLoader L = new FXMLLoader(ZFxml.class.getResource(path));
            Parent load = L.load();
            controller = L.getController();
            controller.setView(load);
        } catch (IOException e) {
            ZAlert.errorHandle(e);
        }
        return controller;
    }

    static Parent get(String path, Initializable con) {
        FXMLLoader L = new FXMLLoader(ZFxml.class.getResource(path));
        L.setController(con);
        Parent load = null;
        try {
            load = L.load();
        } catch (IOException e) {
            ZAlert.errorHandle(e);
        }
        return load;
    }

    static Parent get(String path, ZFxml con) {
        System.err.println(path);
        FXMLLoader L = new FXMLLoader(ZFxml.class.getResource(path));
        L.setController(con);
        Parent load = null;
        try {
            load = L.load();
            con.setView(load);
        } catch (IOException e) {
            ZAlert.errorHandle(e);
        }
        return load;
    }

    void setView(Parent parent);

}
