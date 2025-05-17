package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

public class NavigateCommand implements ScriptCommand {
    private WebEngine webEngine;
    private TextArea progressLog;

    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    @Override
    public void setProgressLog(TextArea progressLog) {
        this.progressLog = progressLog;
    }

    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("go ") || commandLine.startsWith("navigate ");
    }

    @Override
    public void execute(String commandLine) {
        String url = commandLine.substring(commandLine.indexOf(" ") + 1).trim();
        progressLog.appendText("Navigating to: " + url + "\n");
        webEngine.load(url);
    }
}