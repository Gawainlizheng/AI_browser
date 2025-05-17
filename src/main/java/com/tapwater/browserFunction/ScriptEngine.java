package com.tapwater.browserFunction;

import com.tapwater.browserFunction.command.*;
import javafx.scene.web.WebEngine;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;

public class ScriptEngine {
    private final WebEngine webEngine;
    private final TextArea progressLog;
    private final List<ScriptCommand> commands;
    private final Map<String, String> elementMap;

    public ScriptEngine(WebEngine webEngine, TextArea progressLog) {
        this.webEngine = webEngine;
        this.progressLog = progressLog;
        this.commands = new ArrayList<>();
        this.elementMap = new HashMap<>();
        registerDefaultCommands();
    }

    private void registerDefaultCommands() {
        registerCommand(new SearchTextCommand(elementMap));
        registerCommand(new ClickElementCommand(elementMap));
        registerCommand(new ExtractTextCommand());
        registerCommand(new NavigateCommand());
        registerCommand(new WaitCommand());
        registerCommand(new InputCommand());
    }

    public void registerCommand(ScriptCommand command) {
        commands.add(command);
        command.setWebEngine(webEngine);
        command.setProgressLog(progressLog);
    }

    public void executeScript(String script) {
        String[] lines = script.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            for (ScriptCommand cmd : commands) {
                if (cmd.matches(line)) {
                    progressLog.appendText("Executing: " + line + "\n");
                    cmd.execute(line);
                    break;
                }
            }
        }
    }
}