package com.epo.plugin;

import com.epo.form.GraylogSearchForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class GraylogToolWindow implements ToolWindowFactory {
    public static String WINDOW_NAME = "Graylog";
    public static String CONTENT_NAME = "Local";
    public static String NOTIFY_NAME = "GraylogTips";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //创建出GraylogSearchForm对象
        GraylogSearchForm searchForm = new GraylogSearchForm(project, toolWindow);
        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(searchForm.getContentPanel(), CONTENT_NAME, false);
        //给toolWindow设置内容
        toolWindow.getContentManager().addContent(content);
    }

    /*
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), CONTENT_NAME, true);
        toolWindow.getContentManager().addContent(content);
    }
     */
}