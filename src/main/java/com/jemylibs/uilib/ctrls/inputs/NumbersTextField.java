package com.jemylibs.uilib.ctrls.inputs;

import javafx.scene.control.TextField;

public class NumbersTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            if (end == getText().length()) {
                super.replaceText(start, end, text);
            }
            if (this.getText().length() > 9) {
                this.setText(this.getText().substring(0, 9));
                selectAll();
                deselect();
            }
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
            if (this.getText().length() > 9) {
                this.setText(this.getText().substring(0, 9));
                selectAll();
                deselect();
            }
        }
    }

    private boolean validate(String text) {
        return (getText() + text).matches("[0-9]*(\\.[0-9]{1,2})?");
    }

    public int getIntValue() {
        if (getText().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(getText());
        }
    }

    public void setIntValue(int val) {
        setText(val + "");
    }
}
