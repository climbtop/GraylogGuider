package com.epo.plugin;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

public class GraylogToolWindow implements ToolWindowFactory {
    public static String WINDOW_NAME = "Graylog";
    public static String CONTENT_NAME_LOCAL = "Local";
    public static String CONTENT_NAME_UAT = "UAT";
    public static String CONTENT_NAME_PROD = "PROD";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content1 = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), CONTENT_NAME_LOCAL, true);
        toolWindow.getContentManager().addContent(content1);
        Content content2 = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), CONTENT_NAME_UAT, true);
        toolWindow.getContentManager().addContent(content2);
        Content content3 = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), CONTENT_NAME_PROD, true);
        toolWindow.getContentManager().addContent(content3);
    }
}