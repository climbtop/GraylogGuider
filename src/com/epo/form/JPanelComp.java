package com.epo.form;

import javax.swing.*;
import java.awt.*;

public class JPanelComp {
    private int PW = 20;
    private int PH = 20;
    private JPanel jpanel;
    private GridBagLayout layout;
    private GridBagConstraints gbag;

    public JPanelComp(JPanel jpanel) {
        this.jpanel = jpanel;
        this.layout=new GridBagLayout();
        this.jpanel.setLayout(layout);
        this.gbag=new GridBagConstraints();
        this.gbag.fill=GridBagConstraints.BOTH;
    }

    public void add(Component comp, int x, int y, int w, int h) {
        gbag.gridx=x;
        gbag.gridy=y;
        gbag.gridwidth=w;
        gbag.gridheight=h;
        layout.setConstraints(comp, gbag);
        jpanel.add(comp);
        setSize(comp,w,h);
    }

    public void setSize(Component comp, int w, int h) {
        Dimension winDim = jpanel.getPreferredSize();
        int comW = Double.valueOf((w*1.0/PW) * winDim.getWidth()).intValue();
        int comH = Double.valueOf((h*1.0/PH) * winDim.getHeight()).intValue();
        comp.setPreferredSize(new Dimension(comW, comH));
    }
}
