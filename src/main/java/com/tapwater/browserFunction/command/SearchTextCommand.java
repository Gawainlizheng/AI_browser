package com.tapwater.browserFunction.command;

import java.util.Map;
import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;
import java.nio.charset.StandardCharsets;

public class SearchTextCommand implements ScriptCommand {
    private WebEngine webEngine;
    private TextArea progressLog;
    private final Map<String, String> elementMap;

    public SearchTextCommand(Map<String, String> elementMap) {
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
        return commandLine.startsWith("search ") || commandLine.startsWith("find ");
    }

    @Override
    public void execute(String commandLine) {
        try {
            String utf8CommandLine = new String(commandLine.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            String[] parts = utf8CommandLine.split("=");
            if (parts.length < 2) {
                progressLog.appendText("Invalid search format. Use: search text = varname\n");
                return;
            }

            String searchPart = parts[0].trim();
            String varName = parts[1].trim();
            String text = searchPart.substring(searchPart.indexOf(" ") + 1).trim();
            // First highlight all matching elements
            String highlightScript = String.format(
                    "var elements = document.querySelectorAll('*:not(script):not(style)');" +
                            "var foundIds = [];" +
                            "for (var i = 0; i < elements.length; i++) {" +
                            "    if (elements[i].textContent.includes('%s')) {" +
                            "        elements[i].style.border = '2px solid red';" +
                            "        foundIds.push(elements[i].id || elements[i].getAttribute('data-id') || '');" +
                            "    }" +
                            "}" +
                            "foundIds.length > 0 ? foundIds[0] : '';", text);

            Object result = webEngine.executeScript(highlightScript);
            String elementId = result != null ? result.toString() : "";

            if (!elementId.isEmpty()) {
                elementMap.put(varName, "#" + elementId);
                progressLog.appendText("Found element with text '" + text + "' and saved as @" + varName + "\n");
            } else {
                progressLog.appendText("No element found with text '" + text + "'\n");
            }
        } catch (Exception e) {
            progressLog.appendText("Error in search command: " + e.getMessage() + "\n");
        }
    }
}