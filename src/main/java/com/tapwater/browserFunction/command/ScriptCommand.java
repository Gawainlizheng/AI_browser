package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;

public interface ScriptCommand {
    void setWebEngine(WebEngine webEngine);
    boolean matches(String commandLine);
    void execute(String commandLine);
}