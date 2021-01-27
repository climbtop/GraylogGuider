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
    private Component zero;
    private java.util.List<Component> compList;
    private java.util.List<Dimension> posiList;
    private java.util.List<Dimension> dimeList;

    public JPanelComp(JPanel jpanel) {
        this.jpanel = jpanel;
        this.layout=new GridBagLayout();
        this.jpanel.setLayout(layout);
        this.gbag=new GridBagConstraints();
        this.gbag.fill=GridBagConstraints.BOTH;
        this.compList = new ArrayList<>();
        this.posiList = new ArrayList<>();
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
        posiList.add(new Dimension(x,y));
        dimeList.add(new Dimension(w,h));
        if(zero==null) {
            zero = comp;
        }
    }

    private void setCompSize(Component comp, int x, int y, int w, int h) {
        Dimension winDim = jpanel.getPreferredSize();
        int comW = Double.valueOf((w*1.0/PW) * winDim.getWidth()).intValue();
        int comH = Double.valueOf((h*1.0/PH) * winDim.getHeight()).intValue();
        comH = adjustHeight((int)winDim.getHeight(), y, h, PH, comH);
        comp.setPreferredSize(new Dimension(comW, comH));
        comp.setSize(new Dimension(comW, comH));
    }

    public void setSize(Dimension winDim){
        for(int i=0; i<compList.size(); i++){
            Component component = compList.get(i);
            Dimension positions = posiList.get(i);
            Dimension dimension = dimeList.get(i);
            int comW = Double.valueOf((dimension.getWidth()*1.0/PW) * winDim.getWidth()).intValue();
            int comH = Double.valueOf((dimension.getHeight()*1.0/PH) * winDim.getHeight()).intValue();
            comH = adjustHeight((int)winDim.getHeight(), (int)positions.getHeight(), (int)dimension.getHeight(), PH, comH);
            component.setPreferredSize(new Dimension(comW, comH));
            component.setSize(comW, comH);
        }
    }

    public boolean isZero(){
        if(zero==null)return true;
        Rectangle rectangle = zero.getBounds();
        return rectangle.getX()<20.0 && rectangle.getY()<20.0;
    }

    public String getZeroXY() {
        return zero!=null?(zero.getX()+","+zero.getY()):"";
    }

    public int adjustHeight(int winHeight, int y, int h, int PH, int comH){
        int minH = 30;
        if(y<2){
            return minH;
        }else{
            return winHeight - (PH-h)*minH;
        }
    }

}
