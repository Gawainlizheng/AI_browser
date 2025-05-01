package com.tapwater.browserFunction.command;

import com.tapwater.browserFunction.command.ScriptCommand;
import javafx.scene.web.WebEngine;

public class NavigateCommand implements ScriptCommand {
    private WebEngine webEngine;

    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("go ") || commandLine.startsWith("navigate ");
    }

    @Override
    public void execute(String commandLine) {
        String url = commandLine.substring(commandLine.indexOf(" ") + 1).trim();
        webEngine.load(url);
    }
}