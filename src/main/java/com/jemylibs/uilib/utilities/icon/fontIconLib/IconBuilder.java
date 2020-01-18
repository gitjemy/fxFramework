//    FontIcon is a JavaFX library to use FontIcons
//    Copyright (C) 2014-2017 Adrián Romero Corchado.
//
//    This file is part of FontIcon
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//     
//         http://www.apache.org/licenses/LICENSE-2.0
//     
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.

package com.jemylibs.uilib.utilities.icon.fontIconLib;

import com.jemylibs.uilib.utilities.icon.fontIconLib.decorator.FillPaint;
import com.jemylibs.uilib.utilities.icon.fontIconLib.decorator.IconDecorator;
import com.jemylibs.uilib.utilities.icon.fontIconLib.decorator.Shine;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FIcon;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Builder;

/**
 * @author adrian
 */
public class IconBuilder implements Builder<Icon> {

    private Icon icon;

    public static IconBuilder create(FIcon iconf, double size) {

        IconBuilder builder = new IconBuilder();
        builder.icon = Icon.create();
        builder.icon.getProperties().put("ICONLABEL", iconf.toString());
        builder.icon.setText(iconf.getString());
        builder.icon.setFont(Font.font(iconf.getFontName(), size));
        return builder;
    }

    public static IconBuilder create(FIcon icon) {
        return create(icon, 14.0);
    }

    public static IconBuilder create() {
        IconBuilder builder = new IconBuilder();
        builder.icon = Icon.create();
        return builder;
    }

    public static Icon menu_bar(FIcon icon) {
        return create(icon, 15.0)
                .color(new Color(0 / 255f, 0 / 255f, 0 / 255f, 0.71)).build();
    }

    public static Icon button(FIcon icon, Color color) {
        return create(icon, 15.0).color(color).build();
    }

    @Override
    public Icon build() {
        return icon;
    }

    public IconBuilder iconFont(FIcon iconf) {
        icon.getProperties().put("ICONLABEL", iconf.toString());
        icon.setText(iconf.getString());
        icon.setFont(Font.font(iconf.getFontName(), icon.getFont().getSize()));
        return this;
    }

    public IconBuilder iconAwesome(FontAwesome iconf) {
        return iconFont(iconf);
    }

    public IconBuilder styleClass(String styleClass) {
        icon.getStyleClass().add(styleClass);
        return this;
    }

    public IconBuilder size(double size) {
        icon.setFont(Font.font(icon.getFont().getFamily(), size));
        return this;
    }

    public IconBuilder fill(Paint fill) {
        return apply(new FillPaint(fill));
    }

    public IconBuilder color(Color color) {
        return apply(new FillPaint(color));
    }

    public IconBuilder shine(Color color) {
        return apply(new Shine(color));
    }

    public IconBuilder apply(IconDecorator visitor) {
        visitor.decorate(icon);
        return this;
    }
}
