package com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control;

import com.jemylibs.uilib.utilities.ZValidate;
import com.jemylibs.sedb.utility.JDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A DateTimePicker with configurable datetime format where both date and time
 * can be changed via the text field and the date can additionally be changed
 * via the JavaFX default date picker.
 */
@SuppressWarnings("unused")
public class DateTimePicker extends DatePicker {

    private DateTimeFormatter formatter;
    private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());

    private ObjectProperty<String> format = new SimpleObjectProperty<String>() {
        public void set(String newValue) {
            super.set(newValue);
            formatter = DateTimeFormatter.ofPattern(newValue);
        }
    };

    public DateTimePicker() {
        getStyleClass().add("datetime-picker");
        setFormat(JDateTime.db_time_stamp_format);
        setConverter(new InternalConverter());

        // Syncronize changes to the underlying date value back to the dateTimeValue
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                dateTimeValue.set(null);
            } else if (dateTimeValue.get() == null) {
                dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
            } else {
                LocalTime time = dateTimeValue.get().toLocalTime();
                dateTimeValue.set(LocalDateTime.of(newValue, time));
            }
        });

        // Syncronize changes to dateTimeValue back to the underlying date value
        dateTimeValue.addListener((observable, oldValue, newValue) -> {
            setValue(newValue == null ? null : newValue.toLocalDate());
        });

        // Persist changes onblur
        getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                simulateEnterPressed();
            }
        });

    }

    private void simulateEnterPressed() {
        getEditor().commitValue();
    }

    public LocalDateTime getDateTimeValue() {
        simulateEnterPressed();
        return dateTimeValue.get();
    }

    public void setDateTimeValue(LocalDateTime dateTimeValue) {
        this.dateTimeValue.set(dateTimeValue);
    }

    public void Validate() throws ZValidate {
        simulateEnterPressed();
        LocalDateTime get = dateTimeValue.get();
        if (get == null) {
            throw new ZValidate(this, "يرجي إدخال التاريخ بشكل صحيح");
        }
    }

    public ObjectProperty<LocalDateTime> dateTimeValueProperty() {
        return dateTimeValue;
    }

    public String getFormat() {
        return format.get();
    }

    public void setFormat(String format) {
        this.format.set(format);
    }

    public ObjectProperty<String> formatProperty() {
        return format;
    }

    class InternalConverter extends StringConverter<LocalDate> {

        public String toString(LocalDate object) {
            LocalDateTime value = getDateTimeValue();
            return (value != null) ? value.format(formatter) : "";
        }

        public LocalDate fromString(String value) {
            if (value == null || value.isEmpty()) {
                dateTimeValue.set(null);
                return null;
            }

            dateTimeValue.set(LocalDateTime.parse(value, formatter));
            return dateTimeValue.get().toLocalDate();
        }
    }

}
