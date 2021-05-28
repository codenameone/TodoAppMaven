package com.todocorp.app;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import static com.codename1.ui.CN.*;

public class TodoItem extends Container {
    private TextField nameText;
    private CheckBox done = new CheckBox();
    public TodoItem(String name, boolean checked) {
        super(new BorderLayout());
        setUIID("Task");
        nameText = new TextField(name);
        nameText.setUIID("Label");
        add(CENTER, nameText);
        add(EAST, done);
        done.setToggle(true);
        done.setMaterialIcon(FontImage.MATERIAL_CHECK, 4);
        done.setSelected(checked);
        done.addActionListener(e -> ((TodoForm)getComponentForm()).save());
        nameText.addActionListener(e -> ((TodoForm)getComponentForm()).save());
    }
    public void edit() {
        nameText.startEditingAsync();
    }
    public boolean isChecked() {
        return done.isSelected();
    }
    public String getText() {
        return nameText.getText();
    }
}