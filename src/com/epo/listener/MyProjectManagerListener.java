package com.epo.listener;

import com.intellij.ProjectTopics;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.PsiTreeChangeListener;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class MyProjectManagerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        MessageBusConnection connection = project.getMessageBus().connect();
        connection.subscribe(ProjectTopics.MODULES, new MyModuleListener());
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new MyFileEditorManagerListener());
        PsiManager.getInstance(project).addPsiTreeChangeListener(new MyPsiTreeChangeListener());
        EditorFactory.getInstance().addEditorFactoryListener(new MyEditorFactoryListener(), project);
    }

    @Override
    public void projectClosed(@NotNull Project project) {

    }

    @Override
    public void projectClosing(@NotNull Project project) {

    }

    @Override
    public void projectClosingBeforeSave(@NotNull Project project) {

    }
}
