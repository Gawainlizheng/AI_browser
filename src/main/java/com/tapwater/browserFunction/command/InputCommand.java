package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

public class InputCommand implements ScriptCommand {
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
        return commandLine.startsWith("input ");
    }

    @Override
    public void execute(String commandLine) {
        String rest = commandLine.substring(6).trim();
        String[] parts = rest.split(" ", 2);
        if (parts.length < 2) return;

        String selector = parts[0];
        String text = parts[1];
        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "if (element) {" +
                        "   element.value = '"+ text +"';" +
                        "   return true;" +
                        "}" +
                        "return false;", selector, text.replace("'", "\\'"));
        System.out.println("script " + script);
        Boolean success = (Boolean) webEngine.executeScript(script);
        if (success) {
            progressLog.appendText("Entered text '" + text + "' into " + selector + "\n");
        } else {
            progressLog.appendText("Failed to enter text into " + selector + "\n");
        }
    }
}