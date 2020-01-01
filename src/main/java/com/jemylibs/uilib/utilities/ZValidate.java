package com.jemylibs.uilib.utilities;

import com.jemylibs.data.seimpl.utility.Lang;
import com.jemylibs.uilib.ctrls.inputs.DecimalTextField;
import com.jemylibs.uilib.ctrls.inputs.NumbersTextField;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class ZValidate extends Exception {

    Node node;

    public ZValidate(Node node, String message) {
        super(message);
        this.node = node;
    }

    public ZValidate(Node node, Throwable e) {
        super(e.getMessage());
        this.node = node;
    }

    public static void Valid(Node node, String message, boolean valid) throws ZValidate {
        if (!valid) {
            throw new ZValidate(node, message);
        }
    }

    public static String empty(TextInputControl node) throws ZValidate {
        String text = node.getText();
        if (text.isEmpty()) {
            String Msg = "املأ هذا الحقل من فضلك";
            throw new ZValidate(node, Msg);
        } else {
            return text;
        }
    }

    public static BigDecimal validateRange(DecimalTextField node, double min, double max) throws ZValidate {
        double val = node.getDouble();
        String msg = "";
        if (val < min) {
            msg = "القيمة اقل من المتاح )" + min + ")";
        } else if (val > max) {
            msg = "القيمة اكبر من المتاح )" + max + ")";
        } else {
            return node.getValue();
        }
        throw new ZValidate(node, msg);
    }

    public static int validateRange(NumbersTextField node, int min, int max) throws ZValidate {
        int val = node.getIntValue();
        String msg = "";
        if (val < min) {
            msg = "القيمة اقل من المتاح )" + min + ")";
        } else if (val > max) {
            msg = "القيمة اكبر من المتاح )" + max + ")";
        } else {
            return val;
        }
        throw new ZValidate(node, msg);
    }

    public static void ValidLength(TextInputControl node, int max, boolean can_be_empty) throws ZValidate {
        if (!can_be_empty) {
            empty(node);
        }
        if (node.getText().length() > max) {
            throw new ZValidate(node, "هذا الحقل اكبر من المتاح");
        }
    }

    public static double ValidNotLessZanOne(TextInputControl node) throws ZValidate {
        double v;
        if (node instanceof DecimalTextField) {
            v = ((DecimalTextField) node).getDouble();
        } else {
            v = ValidisNumeric(node);
        }
        if (v < 1) {
            throw new ZValidate(node, "لا يمكن ان يكون بقيمة صفر");
        } else {
            return v;
        }
    }

    public static double ValidNotNegative(TextInputControl node) throws ZValidate {
        double v;
        if (node instanceof DecimalTextField) {
            v = ((DecimalTextField) node).getDouble();
        } else {
            v = ValidisNumeric(node);
        }
        if (v < 0) {
            throw new ZValidate(node, "القيمة بالسالب");
        } else {
            return v;
        }
    }

    public static void ValidSelectionEmpty(Control node) throws ZValidate {
        if (node instanceof ListView) {
            ListView L = (ListView) node;
            if (L.getSelectionModel().isEmpty()) {
                throw new ZValidate(node, "إختر من فضلك");
            }
        } else if (node instanceof TableView) {
            TableView T = (TableView) node;
            if (T.getSelectionModel().isEmpty()) {
                throw new ZValidate(node, "إختر من فضلك");
            }
        } else if (node instanceof ChoiceBox) {
            ChoiceBox CH = (ChoiceBox) node;
            if (CH.getSelectionModel().isEmpty()) {
                throw new ZValidate(node, "إختر من فضلك");
            }
        }
    }

    public static <R> R ValidSelectionEmpty(SelectionModel<R> selectionMode, Control node) throws ZValidate {
        if (selectionMode.isEmpty()) {
            throw new ZValidate(node, "إختر من فضلك");
        } else {
            return selectionMode.getSelectedItem();
        }
    }

    public static <R> void do_if_selected(SelectionModel<R> selectionMode, Control node, Consumer<R> consumer) {
        if (selectionMode.isEmpty()) {
            try {
                throw new ZValidate(node, "إختر من فضلك");
            } catch (ZValidate zValidate) {
                ZAlert.errorHandle(zValidate);
            }
        } else {
            R item = selectionMode.getSelectedItem();
            consumer.accept(item);
        }
    }

    public static <z> ObservableList<z> empty(TableView<z> node) throws ZValidate {
        return empty(node.getItems(), node);
    }

    public static <z> ObservableList<z> empty(ListView<z> node) throws ZValidate {
        return empty(node.getItems(), node);
    }

    public static <z> ObservableList<z> empty(ObservableList<z> list, Control node) throws ZValidate {
        if (list.isEmpty()) {
            throw new ZValidate(node, "يرجى إضافة بيانات أولا");
        }
        return list;
    }

    public static double ValidisNumeric(TextInputControl node) throws ZValidate {
        if (!Lang.IsNumber(node.getText())) {
            throw new ZValidate(node, "هذا الحقل يجب ان يحتوي على رقم");
        } else {
            try {
                return Double.parseDouble(node.getText());
            } catch (Throwable e) {
                throw new ZValidate(node, "هذا الحقل يجب ان يحتوي على رقم");
            }
        }
    }

    public static void validate(validator validator) {
        try {
            validator.valid();
        } catch (Exception ZUILogic) {
            ZAlert.errorHandle(ZUILogic);
        }
    }

    public static void validate(Node node, validator validator) {
        try {
            validator.valid();
        } catch (Exception ZUILogic) {
            ZAlert.Tooltips.ShowErrorTip(node, ZUILogic.getMessage());
        }
    }

    public Node getNode() {
        return node;
    }

    public interface validator {
        void valid() throws Exception;
    }
}
