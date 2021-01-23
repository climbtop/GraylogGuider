package com.epo.form;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraylogSearchForm {
    private Project project;
    private ToolWindow toolWindow;
    private JPanel mainPanel;
    private ComboBox<String> environment;
    private JTextField searchText;
    private JButton searchBtn;
    private ComboBox<String> searchRange;
    private ComboBox<String> pageSize;
    private JCheckBox isDetails;
    private JLabel totalRecords;
    private JTextArea consoleView;

    public GraylogSearchForm(Project project, ToolWindow toolWindow){
        this.project = project;
        this.toolWindow = toolWindow;
        this.createContentPanel();
    }

    public void createContentPanel() {
        mainPanel = new JPanel();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension = new Dimension((int)screensize.getWidth()-50,
                (int)screensize.getHeight()/3*2);
        mainPanel.setPreferredSize(dimension);
        JPanelComp jcom = new JPanelComp(mainPanel);

        //第1行
        jcom.add(new JLabel("Env:"), 0, 0, 1, 1);
        environment = new ComboBox<String>(new String[] {"Prod","Uat","Sit"});
        environment.setSelectedIndex(1);
        jcom.add(environment, 1, 0, 2, 1);

        jcom.add(new JLabel("Word:"), 3, 0, 1, 1);
        searchText = new JTextField("");
        jcom.add(searchText, 4, 0, 14, 1);

        searchBtn = new JButton("Qeury");
        jcom.add(searchBtn, 18, 0, 2, 1);

        //第2行
        jcom.add(new JLabel("Range:"), 0, 1, 1, 1);
        searchRange= new ComboBox<String>(new String[] {"15Min","30Min","1Hour","2Hour","8Hour","1Day","2Day","5Day","7Day","14Day","30Day"});
        searchRange.setSelectedIndex(1);
        jcom.add(searchRange, 1, 1, 2, 1);

        jcom.add(new JLabel("Size:"), 3, 1, 1, 1);
        pageSize = new ComboBox<String>(new String[] {"10", "20", "50","100","200","500","1000"});
        pageSize.setSelectedIndex(2);
        jcom.add(pageSize, 4, 1, 2, 1);

        jcom.add(new JLabel("Detail:"), 6, 1, 1, 1);
        isDetails = new JCheckBox();
        jcom.add(isDetails, 7, 1, 1, 1);

        jcom.add(new JPanel(), 8, 1, 9, 1);

        jcom.add(new JLabel("Total:"), 17, 1, 1, 1);
        totalRecords = new JLabel("0");
        jcom.add(totalRecords, 18, 1, 2, 1);

        //第3行
        consoleView = new JTextArea();
        jcom.add(consoleView, 0, 2, 20, 18);

        //组件操作
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("-----");
            }
        });

    }

    public JPanel getContentPanel(){
        return mainPanel;
    }

    public static void main(String[] args) {
        GraylogSearchForm form = new GraylogSearchForm(null,null);
        JFrame jf=new JFrame();
        jf.setContentPane(form.getContentPanel());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

}