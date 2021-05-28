package com.todocorp.app;

import com.codename1.properties.BooleanProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;

public class Todo implements PropertyBusinessObject {
    public final Property<String, Todo> name = new Property<>("name", "");
    public final BooleanProperty<Todo> checked = new BooleanProperty<>("checked", false);
    private final PropertyIndex idx = new PropertyIndex(this, "Todo", name, checked);

    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }
}
