package com.tapwater.browserFunction.command;

import com.tapwater.browserFunction.command.ScriptCommand;
import javafx.scene.web.WebEngine;

public class SearchTextCommand implements ScriptCommand {
    private WebEngine webEngine;

    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("search ") || commandLine.startsWith("find ");
    }

    @Override
    public void execute(String commandLine) {
        String text = commandLine.substring(commandLine.indexOf(" ") + 1).trim();
        String script = String.format(
                "var elements = document.querySelectorAll('*');" +
                        "for (var i = 0; i < elements.length; i++) {" +
                        "   if (elements[i].textContent.includes('%s')) {" +
                        "       elements[i].style.border = '2px solid red';" +
                        "   }" +
                        "}", text);
        webEngine.executeScript(script);
    }
}