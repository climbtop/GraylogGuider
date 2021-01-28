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
    private Map<String,EditorMouseListener> editorMouseMap = new HashMap<String,EditorMouseListener>();

    private void showNotifyTip(String message){
        NotificationGroup notificationGroup = new NotificationGroup(GraylogToolWindow.NOTIFY_NAME,
                NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification(message, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Boolean watcherFlag = GraylogGuiderService.getInstance().getSearchForm().getWatcherFlag();
        watcherFlag = !watcherFlag;
        GraylogGuiderService.getInstance().getSearchForm().setWatcherFlag(watcherFlag);
        if(watcherFlag){
            showNotifyTip("GraylogGuider installed.");
        }else {
            showNotifyTip("GraylogGuider uninstalled.");
        }
    }

}
