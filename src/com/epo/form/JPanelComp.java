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
    private java.util.List<Dimension> dimeList;

    public JPanelComp(JPanel jpanel) {
        this.jpanel = jpanel;
        this.layout=new GridBagLayout();
        this.jpanel.setLayout(layout);
        this.gbag=new GridBagConstraints();
        this.gbag.fill=GridBagConstraints.BOTH;
        this.compList = new ArrayList<>();
        this.dimeList = new ArrayList<>();
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
        dimeList.add(new Dimension(w,h));
    }

    private void setCompSize(Component comp, int x, int y, int w, int h) {
        Dimension winDim = jpanel.getPreferredSize();
        int comW = Double.valueOf((w*1.0/PW) * winDim.getWidth()).intValue();
        int comH = Double.valueOf((h*1.0/PH) * winDim.getHeight()).intValue();
        comp.setPreferredSize(new Dimension(comW, comH));
        comp.setSize(new Dimension(comW, comH));
    }

    public void setSize(Dimension winDim){
        for(int i=0; i<compList.size(); i++){
            Component component = compList.get(i);
            Dimension dimension = dimeList.get(i);
            int comW = Double.valueOf((dimension.getWidth()*1.0/PW) * winDim.getWidth()).intValue();
            int comH = Double.valueOf((dimension.getHeight()*1.0/PH) * winDim.getHeight()).intValue();
            component.setPreferredSize(new Dimension(comW, comH));
            component.setSize(comW, comH);
        }
    }
}
