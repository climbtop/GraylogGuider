package com.epo;

public interface ActionCallback {
    void trigger(String psiFile, int line, int column);
}
