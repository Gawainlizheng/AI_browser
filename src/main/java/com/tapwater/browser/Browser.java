package com.tapwater.browser;

import com.tapwater.browserFunction.ScriptEngine;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Browser {
    private WebView webView;
    private WebEngine webEngine;
    private TextField urlField;
    private TextArea scriptArea;
    private TextArea progressLog;
    private ScriptEngine scriptEngine;
    private SplitPane mainLayout;

    public Browser() {
        webView = new WebView();
        webEngine = webView.getEngine();
        urlField = new TextField("https://www.google.com/");
        scriptArea = new TextArea();
        progressLog = new TextArea();
        progressLog.setEditable(false);
        scriptEngine = new ScriptEngine(webEngine, progressLog);

        setupUI();
    }

    private void setupUI() {
        urlField.setOnAction(e -> webEngine.load(urlField.getText()));

        // Create execute button for scripts
        Button executeButton = new Button("Execute Script");
        executeButton.setOnAction(e -> {
            progressLog.appendText("=== Executing Script ===\n");
            scriptEngine.executeScript(scriptArea.getText());
        });

        // Create script controls panel
        VBox scriptPanel = new VBox(
                new VBox(scriptArea, executeButton),
                new VBox(progressLog)
        );
        scriptPanel.setSpacing(10);

        // Create browser layout
        BorderPane browserLayout = new BorderPane();
        browserLayout.setTop(urlField);
        browserLayout.setCenter(webView);

        // Create main split pane
        mainLayout = new SplitPane();
        mainLayout.getItems().addAll(browserLayout, scriptPanel);
        mainLayout.setDividerPosition(0, 0.7);
    }

    public Scene getScene() {
        return new Scene(mainLayout, 1200, 800);
    }

    public void loadInitialUrl() {
        webEngine.load(urlField.getText());
    }
}