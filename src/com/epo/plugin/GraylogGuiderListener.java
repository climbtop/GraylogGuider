package com.epo.plugin;

import com.epo.form.GraylogSearchForm;
import com.epo.form.SearchParam;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

public class GraylogGuiderListener {
    private static Map<String, EditorMouseListener> editorMouseMap = new HashMap<String,EditorMouseListener>();

    private static String getVirtualFile(Editor editor){
        String virtualFile = editor.getDocument().toString();
        if(virtualFile==null || virtualFile.indexOf('[')<0 || virtualFile.indexOf(']')<0){
            return null;
        }
        virtualFile = virtualFile.substring(virtualFile.indexOf('[') + 1, virtualFile.lastIndexOf(']'));
        if(virtualFile.startsWith("file://")){
            virtualFile = virtualFile.substring(7);
        }
        if(!virtualFile.toLowerCase().endsWith(".java")){
            return null;
        }
        return virtualFile;
    }

    public static void unregisterEditorListener(Editor editor){
        String virtualFile = getVirtualFile(editor);
        if(virtualFile==null) return;

        EditorMouseListener listener = editorMouseMap.get(virtualFile);
        if(listener==null) return;

        editor.removeEditorMouseListener(listener);
        editorMouseMap.remove(virtualFile);
    }

    public static void registerEditorListener(Editor editor){
        String virtualFile = getVirtualFile(editor);
        if(virtualFile==null) return;

        EditorMouseListener listener = editorMouseMap.get(virtualFile);
        if(listener!=null) return;

        GraylogCallback callback = getListenerCallback();
        listener = new EditorMouseListener() {
            @Override
            public void mouseClicked(@NotNull EditorMouseEvent event) {
                callback.trigger(virtualFile, editor);
            }
        };

        editor.addEditorMouseListener(listener);
        editorMouseMap.put(virtualFile, listener);
    }

    private static GraylogCallback getListenerCallback(){
        GraylogCallback callback = new GraylogCallback() {
            @Override
            public void trigger(String virtualFile, Editor editor) {
                //开启监听标识
                GraylogSearchForm searchForm = GraylogGuiderService.getInstance().getSearchForm();
                if(searchForm==null || !searchForm.getWatcherFlag())return;
                //提取查询参数
                SelectionModel selectionModel = editor.getSelectionModel();
                String searchText = selectionModel.getSelectedText();
                int searchLine = selectionModel.getSelectionStartPosition().getLine()+1;
                int lineCount = editor.getDocument().getLineCount();
                //Graylog查询方式
                SearchParam searchParam = new SearchParam();
                searchParam.setLineCount(lineCount);
                searchParam.setLineNumber(searchLine);
                searchParam.setSourceFile(virtualFile);
                searchParam.setSearchText(searchText);
                GraylogGuiderService.getInstance().searchGraylogMessage(searchParam);
            }
        };
        return callback;
    }


}
