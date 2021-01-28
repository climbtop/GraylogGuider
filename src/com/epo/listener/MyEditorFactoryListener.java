package com.epo.listener;

import com.epo.plugin.GraylogGuiderListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import org.jetbrains.annotations.NotNull;

public class MyEditorFactoryListener implements EditorFactoryListener {
    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        GraylogGuiderListener.registerEditorListener(event.getEditor());
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {
        GraylogGuiderListener.unregisterEditorListener(event.getEditor());
    }
}
