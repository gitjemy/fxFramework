package com.jemylibs.uilib.ctrls.inputs;

import com.jemylibs.data.seimpl.utility.Lang;
import com.jemylibs.uilib.utilities.JDouble;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;

public class PercentTextField extends TextField {

    public PercentTextField() {
        super();
        init();
    }

    public PercentTextField(String text) {
        super(text);
        init();
    }

    private void init() {

        setTextFormatter(new TextFormatter<>(c -> {
            String newText = c.getControlNewText();
            String Text = c.getText();
            if (newText.isEmpty()) {
                return c;
            }
            if (Lang.isDouble(newText)) {
                BigDecimal decimal = BigDecimal.valueOf(Double.parseDouble(newText));
                if (Text.equals(newText) && Text.length() > 2) {
                    c.setText(decimal.toPlainString());
                }
                if (decimal.scale() > JDouble.maxScale) {
                    return null;
                }
                if (decimal.compareTo(BigDecimal.valueOf(100)) > 0) {
                    return null;
                } else if (decimal.compareTo(BigDecimal.ZERO) < 0) {
                    return null;
                }
                return c;
            } else {
                return null;
            }
        }));

    }

    public BigDecimal getValue() {
        if (getText().isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            return BigDecimal.valueOf(Double.valueOf(getText()));
        }
    }

    public void setValue(BigDecimal Val) {
        if (Val == null) {
            setText("");
        } else if (Val.compareTo(BigDecimal.ZERO) == 0) {
            setText("");
        } else {
            setText(Val.toPlainString());
        }
    }

    public void setValue(double Val) {
        setValue(BigDecimal.valueOf(Val));
    }

    public double getDouble() {
        if (getText().isEmpty()) {
            return 0;
        } else {
            return Double.valueOf(getText());
        }
    }
}
