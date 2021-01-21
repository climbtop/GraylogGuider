package com.epo.plugin;

public interface ActionCallback {
    void trigger(String psiFile, int line, int column);
}
