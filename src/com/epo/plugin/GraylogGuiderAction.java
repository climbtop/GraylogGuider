package com.epo.plugin;

import com.alibaba.fastjson.JSON;
import com.epo.form.SearchParam;
import com.epo.graylog.GraylogCaller;
import com.epo.graylog.GraylogClient;
import com.epo.graylog.bean.AbstractConfig;
import com.epo.graylog.bean.Message;
import com.epo.graylog.bean.impl.UatConfig;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import com.intellij.openapi.project.*;
import com.intellij.openapi.editor.*;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GraylogGuiderAction extends AnAction {
    private Map<String,Boolean> isFileInitMap = new HashMap<String,Boolean>();
    private Map<String,EditorMouseListener> editorMouseMap = new HashMap<String,EditorMouseListener>();


    private void unInitPsiFileListener(AnActionEvent event){
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        if(psiFile==null || psiFile.getVirtualFile()==null) return;
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
    }

    private boolean initPsiFileListener(AnActionEvent event,final GraylogCallback callback){
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        if(psiFile==null || psiFile.getVirtualFile()==null) return false;
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
        SelectionModel selectionModel = editor.getSelectionModel();

        EditorMouseListener listener = new EditorMouseListener() {
            @Override
            public void mouseClicked(@NotNull EditorMouseEvent event) {
                callback.trigger(virtualFile, editor);
            }
        };
        editorMouseMap.put(virtualFile, listener);
        editor.addEditorMouseListener(listener);
        isFileInitMap.put(virtualFile,Boolean.TRUE);
        return true;
    }

    private ConsoleView getConsoleView(AnActionEvent event, String contentName){
        try {
            ToolWindow toolWindow = ToolWindowManager.getInstance(event.getProject())
                    .getToolWindow(GraylogToolWindow.WINDOW_NAME);
            if (toolWindow == null) return null;
            ContentManager contentManager = toolWindow.getContentManager();
            if (contentManager == null) return null;
            Content content = contentManager.findContent(contentName);
            if (content == null) return null;
            ConsoleView consoleView = (ConsoleView) content.getComponent();
            return consoleView;
        }catch(Throwable e){
            return null;
        }
    }

    private void clearToConsoleView(AnActionEvent event, String contentName){
        ConsoleView consoleView = getConsoleView(event,contentName);
        if(consoleView!=null) {
            consoleView.clear();
        }
    }

    private void printToConsoleView(AnActionEvent event, String contentName, String message){
        ConsoleView consoleView = getConsoleView(event,contentName);
        if(consoleView!=null) {
            consoleView.print(message+"\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        }
    }

    public void showNotifyTip(String message){
        NotificationGroup notificationGroup = new NotificationGroup(GraylogToolWindow.NOTIFY_NAME,
                NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification(message, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }

    private void triggerActionEventNow(AnActionEvent event, GraylogCallback callback) {
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        String virtualFile = psiFile.getVirtualFile().getCanonicalPath();
        DataContext dataContext = event.getDataContext();
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        callback.trigger(virtualFile, editor);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        GraylogCallback callback = new GraylogCallback() {
            @Override
            public void trigger(String psiFile, Editor editor) {
                //提取查询参数
                SelectionModel selectionModel = editor.getSelectionModel();
                String searchText = selectionModel.getSelectedText();
                int searchLine = selectionModel.getSelectionStartPosition().getLine()+1;
                int lineCount = editor.getDocument().getLineCount();
                //Graylog查询方式
                SearchParam searchParam = new SearchParam();
                searchParam.setLineCount(lineCount);
                searchParam.setLineNumber(searchLine);
                searchParam.setSourceFile(psiFile);
                searchParam.setSearchText(searchText);
                GraylogGuiderService.getInstance().searchGraylogMessage(searchParam);
            }
        };

        boolean initResult = initPsiFileListener(event, callback);
        if(!initResult){
            showNotifyTip("GraylogGuider uninstalled.");
            unInitPsiFileListener(event);
        }else{
            showNotifyTip("GraylogGuider installed.");
        }

        triggerActionEventNow(event,callback);
    }


}
