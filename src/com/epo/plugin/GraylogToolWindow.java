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
    public static String CONTENT_NAME = "Local";
    public static String CONTENT_NAME_UAT = "Uat";
    public static String CONTENT_NAME_PROD = "Prod";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), CONTENT_NAME, true);
        toolWindow.getContentManager().addContent(content);
    }
}