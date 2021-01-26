package com.epo.form;

import com.epo.graylog.bean.SearchResult;
import com.epo.graylog.bean.impl.ModuleConfig;
import com.epo.plugin.GraylogGuiderService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GraylogSearchForm{
    private Project project;
    private ToolWindow toolWindow;
    private String projectPath;
    private AtomicInteger resizeCnt;
    private AtomicBoolean resizeOff;
    private JPanelComp jcom;
    private JPanel mainPanel;
    private ComboBox<String> environment;
    private JTextField searchText;
    private JButton searchBtn;
    private ComboBox<String> searchProject;
    private ComboBox<String> searchRange;
    private ComboBox<String> pageSize;
    private JCheckBox isDetails;
    private JLabel totalRecords;
    private JTextArea consoleView;

    public GraylogSearchForm(){
        this(null, null);
    }
    public GraylogSearchForm(Project project, ToolWindow toolWindow){
        if(project!=null && toolWindow !=null) {
            this.project = project;
            this.projectPath = project.getProjectFilePath();
            this.toolWindow = toolWindow;
        }
        this.resizeCnt = new AtomicInteger(0);
        this.resizeOff = new AtomicBoolean(true);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension = new Dimension((int)screensize.getWidth()-50,
                (int)screensize.getHeight()-10);
        this.createContentPanel(dimension);
    }

    public void createContentPanel(Dimension dimension) {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(dimension);
        jcom = new JPanelComp(mainPanel);

        //第1行
        jcom.add(new JLabel("Env:"), 0, 0, 1, 1);
        environment = new ComboBox<String>(new String[] {"Prod","Uat","Sit"});
        environment.setSelectedIndex(1);
        jcom.add(environment, 1, 0, 2, 1);

        jcom.add(new JLabel("Words:"), 3, 0, 1, 1);
        searchText = new JTextField("");
        jcom.add(searchText, 4, 0, 14, 1);

        searchBtn = new JButton("Qeury");
        jcom.add(searchBtn, 18, 0, 2, 1);

        //第2行
        jcom.add(new JLabel("Model:"), 0, 1, 1, 1);
        searchProject = new ComboBox<String>(new String[] {"Hybris","Inventory","Hap","Sourcing","Promotion","MALL"});
        searchProject.setSelectedIndex(0);
        jcom.add(searchProject, 1, 1, 2, 1);

        jcom.add(new JLabel("Range:"), 3, 1, 1, 1);
        searchRange= new ComboBox<String>(new String[] {"5Min","15Min","30Min","1Hour","2Hour","8Hour","1Day","2Day","5Day","7Day","14Day","30Day"});
        searchRange.setSelectedIndex(3);
        jcom.add(searchRange, 4, 1, 2, 1);

        jcom.add(new JLabel("Size:"), 6, 1, 1, 1);
        pageSize = new ComboBox<String>(new String[] {"10", "20", "50","100","200","500","1000"});
        pageSize.setSelectedIndex(2);
        jcom.add(pageSize, 7, 1, 2, 1);

        jcom.add(new JLabel("Detail:"), 9, 1, 1, 1);
        isDetails = new JCheckBox();
        jcom.add(isDetails, 10, 1, 1, 1);

        jcom.add(new JPanel(), 11, 1, 6, 1);

        jcom.add(new JLabel("Total:"), 17, 1, 1, 1);
        totalRecords = new JLabel("0");
        jcom.add(totalRecords, 18, 1, 2, 1);

        //第3行
        consoleView = new JTextArea();
        consoleView.setAutoscrolls(true);
        consoleView.setMargin(new Insets(5,5,5,5));
        consoleView.setBackground(new Color(42,42,42));
        consoleView.setEditable(false);
        JBScrollPane jbScrollPane = new JBScrollPane(consoleView);
        jcom.add(jbScrollPane, 0, 2, 20, 18);

        //缩放事件
        mainPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                mainPanelResize();
            }
            public void componentShown(ComponentEvent e) {
                mainPanelResize();
            }
        });
        //组件操作
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击按钮触发搜索
                SearchParam searchParam = new SearchParam();
                GraylogGuiderService.getInstance().searchGraylogMessage(readSearchParam(), searchParam);
            }
        });
    }

    public void mainPanelResize(){
        resizeCnt.addAndGet(1);
        if(!resizeOff.get()) return;
        do{
            resizeCnt.set(0);
            resizeOff.set(false);
            jcom.setSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
            resizeOff.set(true);
        }while(resizeCnt.get()>0);
    }

    public SearchConfig readSearchParam(){
        SearchConfig searchConfig = new SearchConfig();
        searchConfig.setEnvironment(String.valueOf(environment.getSelectedItem()));
        searchConfig.setProjectName(String.valueOf(searchProject.getSelectedItem()));
        searchConfig.setSearchRange(String.valueOf(searchRange.getSelectedItem()));
        searchConfig.setPageSize(String.valueOf(pageSize.getSelectedItem()));
        searchConfig.setSearchText(searchText.getText());
        searchConfig.setIsDetails(isDetails.isSelected()?"Y":"N");
        searchConfig.setSearchRange(String.valueOf(parseRangeMinutes(searchConfig.getSearchRange())));
        return searchConfig;
    }

    public void writeSearchParam(SearchConfig searchConfig){
        searchProject.setSelectedItem(searchConfig.getProjectName());
        searchText.setText(searchConfig.getSearchText());
        totalRecords.setText("0");
        consoleView.setText("");
    }

    public void emptyToConsoleView(SearchResult result){
        getConsoleView().setText("");
        getTotalRecords().setText(String.valueOf(result.getTotalResults()));
    }

    public void printToConsoleView(String message){
        getConsoleView().append(message);
    }

    public Integer parseRangeMinutes(String text){
        switch(text){
            case "5Min": return 5*60;
            case "15Min": return 15*60;
            case "30Min": return 30*60;
            case "1Hour": return 1*60*60;
            case "2Hour": return 2*60*60;
            case "8Hour": return 8*60*60;
            case "1Day": return 1*24*60*60;
            case "2Day": return 2*24*60*60;
            case "5Day": return 5*24*60*60;
            case "7Day": return 7*24*60*60;
            case "14Day": return 14*24*60*60;
            case "30Day": return 30*24*60*60;
            default: return 5*60;
        }
    }

    public JPanel getContentPanel(){
        return mainPanel;
    }

    public JTextArea getConsoleView() {
        return consoleView;
    }

    public JLabel getTotalRecords() {
        return totalRecords;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public static void main(String[] args) {
        GraylogSearchForm form = new GraylogSearchForm();
        JFrame jf=new JFrame();
        jf.setContentPane(form.getContentPanel());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }


}
