package com.epo;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import com.intellij.openapi.project.*;
import com.intellij.openapi.editor.*;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GraylogGuiderAction extends AnAction {
    private Map<String,Boolean> isFileInitMap = new HashMap<String,Boolean>();
    private Map<String,EditorMouseListener> editorMouseMap = new HashMap<String,EditorMouseListener>();

    private void unInitPsiFileListener(AnActionEvent event){
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        String virtualFile = psiFile.getVirtualFile().getCanonicalPath();

        Boolean initFlag = isFileInitMap.get(virtualFile);
        if(initFlag==null || !initFlag) return;
        isFileInitMap.remove(virtualFile);

        EditorMouseListener listener = editorMouseMap.get(virtualFile);
        if(listener!=null){
            // 获取数据上下文
            DataContext dataContext = event.getDataContext();

            // 获取到数据上下文后，通过CommonDataKeys对象可以获得该File的所有信息
            Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
            Document document = editor.getDocument();

            editorMouseMap.remove(virtualFile);
            editor.removeEditorMouseListener(listener);
        }
        System.out.println("uninit source: "+virtualFile);
    }

    private boolean initPsiFileListener(AnActionEvent event,final ActionCallback callback){
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        String virtualFile = psiFile.getVirtualFile().getCanonicalPath();

        Boolean initFlag = isFileInitMap.get(virtualFile);
        if(initFlag!=null && initFlag) return false;

        // 获取当前的project对象
        Project project = event.getProject();

        // 获取数据上下文
        DataContext dataContext = event.getDataContext();

        // 获取到数据上下文后，通过CommonDataKeys对象可以获得该File的所有信息
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        Document document = editor.getDocument();


        EditorMouseListener listener = new EditorMouseListener() {
            @Override
            public void mousePressed(@NotNull EditorMouseEvent event) {
                VisualPosition vp = event.getVisualPosition();
                callback.trigger(virtualFile, vp.getLine(), vp.getColumn());
            }
            @Override
            public void mouseExited(@NotNull EditorMouseEvent event) {
            }
        };
        editorMouseMap.put(virtualFile, listener);
        editor.addEditorMouseListener(listener);

        System.out.println("init source: "+virtualFile+", total:" + document.getLineCount());
        isFileInitMap.put(virtualFile,Boolean.TRUE);
        return true;
    }

    private void triggerSelection(AnActionEvent event){
        // 获取数据上下文
        DataContext dataContext = event.getDataContext();

        // 获取到数据上下文后，通过CommonDataKeys对象可以获得该File的所有信息
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        Document document = editor.getDocument();

        SelectionModel selectionModel = editor.getSelectionModel();
        String text = selectionModel.getSelectedText();
        VisualPosition vp = selectionModel.getLeadSelectionPosition();
        VisualPosition start = selectionModel.getSelectionStartPosition();
        VisualPosition end = selectionModel.getSelectionEndPosition();

        System.out.println(text);
    }

    private ConsoleView getConsoleView(AnActionEvent event){
        ToolWindow toolWindow = ToolWindowManager.getInstance(event.getProject())
                .getToolWindow(GraylogToolWindow.WINDOW_NAME);
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.findContent(GraylogToolWindow.CONTENT_NAME);
        ConsoleView consoleView = (ConsoleView) content.getComponent();
        return consoleView;
    }

    private void println(AnActionEvent event, String message){
        getConsoleView(event).print(message, ConsoleViewContentType.NORMAL_OUTPUT);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        boolean initResult = initPsiFileListener(event, new ActionCallback() {
            @Override
            public void trigger(String psiFile, int line, int column) {
                System.out.println("press: ["+line+","+column+"], source: "+psiFile);
            }
        });
        if(!initResult){
            unInitPsiFileListener(event);
        }

        triggerSelection(event);

        //checkout: moving from release to HYBUAT-1885
        //D:\EPO_HybrisOMS\hybris\bin\custom\epo-base\.git\logs\HEAD

        println(event,"Hello World "+new Date()+"\n");

    }
}
