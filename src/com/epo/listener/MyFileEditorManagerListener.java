package com.epo.listener;

import com.epo.form.GraylogSearchForm;
import com.epo.plugin.GraylogGuiderService;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.FoldRegion;
import com.intellij.openapi.editor.FoldingModel;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class MyFileEditorManagerListener implements FileEditorManagerListener {
    @Override
    public void fileOpenedSync(@NotNull FileEditorManager source, @NotNull VirtualFile file, @NotNull Pair<FileEditor[], FileEditorProvider[]> editors) {
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        setExpandedFoldRegion(source.getSelectedTextEditor());
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        //setExpandedFoldRegion(event.getManager().getSelectedTextEditor());
    }

    protected void setExpandedFoldRegion(Editor editor) {
        //开启监听标识
        GraylogSearchForm searchForm = GraylogGuiderService.getInstance().getSearchForm();
        if(searchForm==null || !searchForm.getWatcherFlag())return;
        //展开代码行不折叠
        if (editor == null || editor.getFoldingModel() == null) return;
        FoldingModel folding = editor.getFoldingModel();
        FoldRegion[] foldRegions = folding.getAllFoldRegions();
        for (FoldRegion foldRegion : foldRegions) {
            try {
                if (!foldRegion.isExpanded()) {
                    foldRegion.setExpanded(true);
                }
            } catch (Throwable e) {
            }
        }
    }
}
