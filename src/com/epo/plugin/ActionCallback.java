package com.epo.plugin;

import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;

public interface ActionCallback {
    void trigger(String psiFile, SelectionModel selectionModel);
}
