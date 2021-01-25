package com.epo.form;

import com.intellij.ui.render.LabelBasedRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JPanelComp {
    private int PW = 20;
    private int PH = 20;
    private JPanel jpanel;
    private GridBagLayout layout;
    private GridBagConstraints gbag;
    private java.util.List<Component> compList;

    public JPanelComp(JPanel jpanel) {
        this.jpanel = jpanel;
        this.layout=new GridBagLayout();
        this.jpanel.setLayout(layout);
        this.gbag=new GridBagConstraints();
        this.gbag.fill=GridBagConstraints.BOTH;
        this.compList = new ArrayList<>();
    }

    public void add(Component comp, int x, int y, int w, int h) {
        gbag.gridx=x;
        gbag.gridy=y;
        gbag.gridwidth=w;
        gbag.gridheight=h;
        layout.setConstraints(comp, gbag);
        jpanel.add(comp);
        setCompSize(comp,x, y, w,h);
        compList.add(comp);
    }

    private void setCompSize(Component comp, int x, int y, int w, int h) {
        Dimension winDim = jpanel.getPreferredSize();
        int comW = Double.valueOf((w*1.0/PW) * winDim.getWidth()).intValue();
        int comH = Double.valueOf((h*1.0/PH) * winDim.getHeight()).intValue();
        //System.out.println(""+x+","+y+", "+comW+","+comH);
        comp.setPreferredSize(new Dimension(comW, comH));
    }

    public void setSize(Dimension winDim){
        for(Component component : compList){
            int w = component.getWidth();
            int h = component.getHeight();
            int comW = Double.valueOf((w*1.0/PW) * winDim.getWidth()).intValue();
            int comH = Double.valueOf((h*1.0/PH) * winDim.getHeight()).intValue();
            component.setPreferredSize(new Dimension(comW, comH));
            component.setSize(comW, comH);
        }
    }
}
