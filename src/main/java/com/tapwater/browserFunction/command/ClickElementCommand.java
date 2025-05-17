package com.tapwater.browserFunction.command;

import java.util.Map;
import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

public class ClickElementCommand implements ScriptCommand {
    private WebEngine webEngine;
    private TextArea progressLog;
    private final Map<String, String> elementMap;

    public ClickElementCommand(Map<String, String> elementMap) {
        this.elementMap = elementMap;
    }

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
        return commandLine.startsWith("click ");
    }

    @Override
    public void execute(String commandLine) {
        String selector = commandLine.substring(6).trim();

        // Check if it's a variable reference
        if (selector.startsWith("@")) {
            String varName = selector.substring(1);
            selector = elementMap.getOrDefault(varName, selector);
            progressLog.appendText("Resolved @" + varName + " to " + selector + "\n");
        }

        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "if (element) {" +
                        "   element.click();" +
                        "   return true;" +
                        "}" +
                        "return false;", selector);

        Boolean clicked = (Boolean) webEngine.executeScript(script);
        if (clicked) {
            progressLog.appendText("Clicked element: " + selector + "\n");
        } else {
            progressLog.appendText("Failed to click element: " + selector + "\n");
        }
    }
}