package com.tapwater.browserFunction;

import com.tapwater.browserFunction.command.*;
import javafx.scene.web.WebEngine;
import java.util.List;
import java.util.ArrayList;

public class ScriptEngine {
    private final WebEngine webEngine;
    private final List<ScriptCommand> commands;

    public ScriptEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.commands = new ArrayList<>();
        registerDefaultCommands();
    }

    private void registerDefaultCommands() {
        registerCommand(new SearchTextCommand());
        registerCommand(new ClickElementCommand());
        registerCommand(new ExtractTextCommand());
        registerCommand(new NavigateCommand());
    }

    public void registerCommand(ScriptCommand command) {
        commands.add(command);
        command.setWebEngine(webEngine);
    }

    public void executeScript(String script) {
        String[] lines = script.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            for (ScriptCommand cmd : commands) {
                if (cmd.matches(line)) {
                    cmd.execute(line);
                    break;
                }
            }
        }
    }
}