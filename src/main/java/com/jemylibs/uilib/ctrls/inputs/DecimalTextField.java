package com.jemylibs.uilib.ctrls.inputs;

import com.jemylibs.data.seimpl.utility.Lang;
import com.jemylibs.uilib.utilities.JDouble;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;

public class DecimalTextField extends TextField {

    public DecimalTextField() {
        super();
        init();
    }

    public DecimalTextField(String text) {
        super(text);
        init();
    }

    private void init() {
        setTextFormatter(new TextFormatter<>(c -> {
            String NewText = c.getControlNewText();
            String Text = c.getText();
            if (NewText.isEmpty()) {
                return c;
            }
            if (Lang.isDouble(NewText)) {
                BigDecimal decimal = BigDecimal.valueOf(Double.parseDouble(NewText));
                if (Text.equals(NewText) && Text.length() > 2) {
                    c.setText(decimal.toPlainString());
                }
                if (decimal.scale() > JDouble.maxScale) {
                    return null;
                }
                return c;
            } else {
                return null;
            }
        }));
    }

    public BigDecimal getValue() {
        BigDecimal val;
        if (getText().isEmpty()) {
            val = BigDecimal.ZERO;
        } else {
            val = BigDecimal.valueOf(Double.valueOf(getText()));
        }
        return JDouble.formatForDb(val);
    }

    public void setValue(BigDecimal Val) {
        if (Val == null) {
            setText("");
        } else if (Val.compareTo(BigDecimal.ZERO) == 0) {
            setText("");
        } else {
            setText(JDouble.formatForDb(Val).toPlainString());
        }
    }

    public void setValue(double Val) {
        setValue(BigDecimal.valueOf(Val));
    }

    public double getDouble() {
        return getValue().doubleValue();
    }
}
