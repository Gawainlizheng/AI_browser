package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

public class WaitCommand implements ScriptCommand {
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
        return commandLine.startsWith("wait ");
    }

    @Override
    public void execute(String commandLine) {
        String timeStr = commandLine.substring(5).trim().replaceAll("[^0-9]", "");
        try {
            int seconds = Integer.parseInt(timeStr);
            progressLog.appendText("Waiting for " + seconds + " seconds...\n");
            Thread.sleep(seconds * 1000);
            progressLog.appendText("Wait completed\n");
        } catch (InterruptedException | NumberFormatException e) {
            progressLog.appendText("Invalid wait time: " + timeStr + "\n");
        }
    }
}