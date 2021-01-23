package com.epo.plugin;

import com.epo.form.GraylogSearchForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class GraylogToolWindow2 implements ToolWindowFactory {
    public static String WINDOW_NAME = "Graylog2";
    public static String CONTENT_NAME = "Local2";

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
    }

}