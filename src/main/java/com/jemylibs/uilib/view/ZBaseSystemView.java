package com.jemylibs.uilib.view;

import com.jemylibs.uilib.utilities.alert.ZAlert;

public interface ZBaseSystemView extends ZNode {

    void onReload(source reload_source) throws Exception; // todo remove all reloads on initialize call cause we call it on open

    default void reload(source reload_source) {
        try {
            onReload(reload_source);
        } catch (Exception e) {
            ZAlert.errorHandle(e);
        }
    }

    enum source {
        internal, external
    }
}
