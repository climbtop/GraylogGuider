package com.epo.listener;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyModuleListener implements ModuleListener {
    @Override
    public void moduleAdded(@NotNull Project project, @NotNull Module module) {

    }

    @Override
    public void beforeModuleRemoved(@NotNull Project project, @NotNull Module module) {

    }

    @Override
    public void moduleRemoved(@NotNull Project project, @NotNull Module module) {

    }

    @Override
    public void modulesRenamed(@NotNull Project project, @NotNull List<Module> modules, @NotNull Function<Module, String> oldNameProvider) {

    }
}
