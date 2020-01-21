package com.jemylibs.uilib.ZView;

import com.jemylibs.uilib.Application;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;

import java.io.IOException;

public interface ZFxml extends Initializable {

    static <U extends ZFxml> U get(String path) {
        U controller = null;
        try {
            FXMLLoader L = new FXMLLoader(ZFxml.class.getResource(path), Application.getApplication().getBundle());
            Parent load = L.load();
            controller = L.getController();
            controller.setView(load);
        } catch (IOException e) {
            ZAlert.errorHandle(e);
        }
        return controller;
    }

    static Parent get(String path, Initializable con) {
        FXMLLoader L = new FXMLLoader(ZFxml.class.getResource(path), Application.getApplication().getBundle());
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
        FXMLLoader L = new FXMLLoader(ZFxml.class.getResource(path), Application.getApplication().getBundle());
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
