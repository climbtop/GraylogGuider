package com.epo.plugin;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

public interface GraylogCallback {
    void trigger(String psiFile, Editor editor);
}
