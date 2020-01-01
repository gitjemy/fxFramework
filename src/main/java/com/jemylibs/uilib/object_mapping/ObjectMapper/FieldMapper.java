package com.jemylibs.uilib.object_mapping.ObjectMapper;

import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class FieldMapper<F, T> implements Function<F, T> {
    final List<WritablePropertyControl<T, ?, ?>> controls;

    protected FieldMapper(WritablePropertyControl<T, ?, ?>... controls) {
        this.controls = Arrays.asList(controls);
    }

    public FieldMapper(ArrayList<WritablePropertyControl<T, ?, ?>> newRecordProperties) {
        this.controls = newRecordProperties;
    }
}
