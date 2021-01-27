package com.epo.plugin;

import com.epo.form.GraylogSearchForm;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class GraylogToolWindow implements ToolWindowFactory {
    public static String WINDOW_NAME = "Graylog";
    public static String CONTENT_NAME = "Local";
    public static String GUIDER_NAME = "Usage";
    public static String NOTIFY_NAME = "Notify";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //创建出GraylogSearchForm对象
        GraylogSearchForm searchForm = new GraylogSearchForm(project, toolWindow);

        //将其保存在工程
        GraylogGuiderService.getInstance().setSearchForm(searchForm);

        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolWindow显示的内容
        Content content = contentFactory.createContent(searchForm.getContentPanel(), CONTENT_NAME, false);
        //给toolWindow设置内容
        toolWindow.getContentManager().addContent(content);

        //添加Guider指引说明页
        createGuiderUsageContent(project, toolWindow);
    }


    private void createGuiderUsageContent(@NotNull Project project, @NotNull ToolWindow toolWindow){
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), GUIDER_NAME, true);
        toolWindow.getContentManager().addContent(content);
        StringBuffer sb = new StringBuffer();
        sb.append("GraylogGuider Usages:\n\n");
        sb.append(" 1. Open a target file.\n");
        sb.append(" 2. Expand all code's lines in the file, that can match the right line number.\n");
        sb.append(" 3. Press the Hot Key [Ctrl + Atl + G]. Disable it just press again.\n");
        sb.append(" 4. Click a log output line in the file. The target's logs will be searching for.\n");
        consoleView.print(sb.toString(), ConsoleViewContentType.NORMAL_OUTPUT);
    }

}