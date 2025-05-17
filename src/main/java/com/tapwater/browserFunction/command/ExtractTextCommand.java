package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;
import java.io.FileWriter;
import java.io.IOException;

public class ExtractTextCommand implements ScriptCommand {
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
        return commandLine.startsWith("extract ") || commandLine.startsWith("save ");
    }

    @Override
    public void execute(String commandLine) {
        String[] parts = commandLine.split(" ");
        if (parts.length < 3) {
            progressLog.appendText("Invalid extract command format. Use: extract [selector] [filename]\n");
            return;
        }

        String selector = parts[1];
        String filename = parts[2];

        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "element ? element.textContent : ''", selector);

        String content = (String) webEngine.executeScript(script);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
            progressLog.appendText("Successfully saved content from " + selector + " to " + filename + "\n");
        } catch (IOException e) {
            progressLog.appendText("Failed to save content: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}