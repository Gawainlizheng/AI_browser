package com.tapwater.browserFunction.command;

import com.tapwater.browserFunction.command.ScriptCommand;

import javafx.scene.web.WebEngine;
import java.io.FileWriter;
import java.io.IOException;

public class ExtractTextCommand implements ScriptCommand {
    private WebEngine webEngine;

    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("extract ") || commandLine.startsWith("save ");
    }

    @Override
    public void execute(String commandLine) {
        String[] parts = commandLine.split(" ");
        if (parts.length < 3) return;

        String selector = parts[1];
        String filename = parts[2];

        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "element ? element.textContent : ''", selector);

        String content = (String) webEngine.executeScript(script);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}