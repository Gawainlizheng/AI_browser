package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

public interface ScriptCommand {
    void setWebEngine(WebEngine webEngine);
    void setProgressLog(TextArea progressLog);
    boolean matches(String commandLine);
    void execute(String commandLine);
}