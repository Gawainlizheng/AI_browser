package com.tapwater.browserFunction.command;

import com.tapwater.browserFunction.command.ScriptCommand;
import javafx.scene.web.WebEngine;

public class ClickElementCommand implements ScriptCommand {
    private WebEngine webEngine;

    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("click ");
    }

    @Override
    public void execute(String commandLine) {
        String selector = commandLine.substring(6).trim();
        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "if (element) element.click();", selector);
        webEngine.executeScript(script);
    }
}