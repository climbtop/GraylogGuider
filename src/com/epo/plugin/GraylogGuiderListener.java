package com.epo.plugin;

import com.epo.form.GraylogSearchForm;
import com.epo.form.SearchParam;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiModifierListImpl;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
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

    /**
     * 填充类的起始行
     * @param editor
     * @param searchParam
     */
    private static void selectdClassBodyLine(Editor editor, SearchParam searchParam){
        try{
            PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, editor.getProject());
            if(psiFile==null) return;
            Document document = editor.getDocument();
            CaretModel caret = editor.getCaretModel();
            int offset = caret.getOffset();
            PsiElement element = psiFile.findElementAt(offset);
            PsiClass containingClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if(containingClass==null) return;

            int selectedLine = document.getLineNumber(offset);
            TextRange textRange = containingClass.getTextRange();
            int classStartLine = document.getLineNumber(textRange.getStartOffset());
            int classStopLine = document.getLineNumber(textRange.getEndOffset());

            boolean isSelectedHeader = false;
            PsiElement[] elements = containingClass.getChildren();
            for(PsiElement ele : elements){
                if(ele instanceof PsiModifierListImpl) {
                    TextRange range = ele.getTextRange();
                    int startLine = document.getLineNumber(range.getStartOffset());
                    int stopLine = document.getLineNumber(range.getEndOffset());
                    if(classStartLine<=selectedLine && selectedLine<=stopLine) {
                        isSelectedHeader = true;
                        break;
                    }
                }
            }

            if(isSelectedHeader){
                searchParam.setLineStart(classStartLine+1);
                searchParam.setLineStop(classStopLine+1);
            }
        }catch(Exception e){
        }
    }

    /**
     * 填充方法的起始行
     * @param editor
     * @param searchParam
     */
    private static void selectdMethodBodyLine(Editor editor, SearchParam searchParam){
        try{
            PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, editor.getProject());
            if(psiFile==null) return;
            Document document = editor.getDocument();
            CaretModel caret = editor.getCaretModel();
            int offset = caret.getOffset();
            PsiElement element = psiFile.findElementAt(offset);
            PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
            if(containingMethod==null) return;

            int selectedLine = document.getLineNumber(offset);
            TextRange textRange = containingMethod.getTextRange();
            int methodStartLine = document.getLineNumber(textRange.getStartOffset());
            int methodStopLine = document.getLineNumber(textRange.getEndOffset());

            boolean isSelectedHeader = false;
            PsiElement[] elements = containingMethod.getChildren();
            for(PsiElement ele : elements){
                if(ele instanceof PsiModifierListImpl) {
                    TextRange range = ele.getTextRange();
                    int startLine = document.getLineNumber(range.getStartOffset());
                    int stopLine = document.getLineNumber(range.getEndOffset());
                    if(methodStartLine<=selectedLine && selectedLine<=stopLine) {
                        isSelectedHeader = true;
                        break;
                    }
                }
            }

            if(isSelectedHeader){
                searchParam.setLineStart(methodStartLine+1);
                searchParam.setLineStop(methodStopLine+1);
            }
        }catch(Exception e){
        }
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
                int searchLine = selectionModel.getSelectionStartPosition().getLine();
                int lineCount = editor.getDocument().getLineCount();
                String lineCodeText = getLineCodeText(editor, searchLine);

                //Graylog查询方式
                SearchParam searchParam = new SearchParam();
                searchParam.setLineCount(lineCount);
                searchParam.setLineNumber(searchLine+1);
                searchParam.setSourceFile(virtualFile);
                searchParam.setSearchText(searchText);
                searchParam.setLineCodeText(lineCodeText);
                //Graylog选择类体,方法体
                selectdClassBodyLine(editor, searchParam);
                selectdMethodBodyLine(editor, searchParam);

                GraylogGuiderService.getInstance().searchGraylogMessage(searchParam);
            }
            private String getLineCodeText(Editor editor, int line){
                try{
                    Document document = editor.getDocument();
                    int startOffset = document.getLineStartOffset(line);
                    int endOffset = document.getLineEndOffset(line);
                    TextRange textRange = new TextRange(startOffset, endOffset);
                    return document.getText(textRange);
                }catch(Exception e){
                    return null;
                }
            }
        };
        return callback;
    }


}
