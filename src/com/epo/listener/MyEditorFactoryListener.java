package com.epo.listener;

import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class MyEditorFactoryListener implements EditorFactoryListener {
    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        String virtualFile = editor.getDocument().toString();
        if(virtualFile.indexOf('[')>0 && virtualFile.indexOf(']')>0) {
            virtualFile = virtualFile.substring(virtualFile.indexOf('[') + 1, virtualFile.lastIndexOf(']'));
            System.out.println(virtualFile);
        }
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {

    }
}
