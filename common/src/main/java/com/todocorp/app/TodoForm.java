package com.todocorp.app;

import static com.codename1.ui.CN.*;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ToastBar;
import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.util.EasyThread;
import java.util.ArrayList;
import java.util.List;

public class TodoForm extends Form {
    private static final String STORAGE_NAME = "todo-storage.json";
    private EasyThread ioThread = EasyThread.start("todo-io");
    private boolean loaded;
    public TodoForm() {
        super("Todo App", BoxLayout.y());
        FloatingActionButton fab = FloatingActionButton.
                createFAB(FontImage.MATERIAL_ADD);
        fab.bindFabToContainer(this);
        fab.addActionListener(e -> addNewItem());
        getToolbar().addMaterialCommandToRightBar("",
                FontImage.MATERIAL_CLEAR_ALL, e -> clearAll());
        load();
    }

    private void addNewItem() {
        if(loaded) {
            TodoItem td = new TodoItem("", false);
            add(td);
            revalidate();
            td.edit();
            save();
        } else {
            ToastBar.showErrorMessage("Please wait, loading...");
        }
    }

    private void clearAll() {
        int cc = getContentPane().getComponentCount();
        for(int i = cc - 1 ; i >= 0 ; i--) {
            TodoItem t = (TodoItem)getContentPane().getComponentAt(i);
            if(t.isChecked()) {
                t.remove();
            }
        }
        save();
        getContentPane().animateLayout(300);
    }
    private void load() {
        getContentPane().removeAll();
        ioThread.run(() -> {
            if (existsInStorage(STORAGE_NAME)) {
                List<Todo> list = new Todo().getPropertyIndex().loadJSONList(STORAGE_NAME);
                callSerially(() -> {
                    for (Todo todo : list) {
                        add(new TodoItem(todo.name.get(), todo.checked.get()));
                    }
                    revalidateWithAnimationSafety();
                    loaded = true;
                });
            }
        });
    }
    public void save() {
        List<Todo> list = new ArrayList<>();
        for(Component cmp : getContentPane()) {
            TodoItem item = (TodoItem)cmp;
            Todo todo = new Todo()
                    .name.set(item.getText())
                    .checked.set(item.isChecked());
            list.add(todo);
        }
        ioThread.run(() -> PropertyIndex.storeJSONList(STORAGE_NAME, list));
    }
}
