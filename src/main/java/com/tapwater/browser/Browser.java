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
    private ScriptEngine scriptEngine;
    private SplitPane mainLayout;  // Store the main layout as a field

    public Browser() {
        webView = new WebView();
        webEngine = webView.getEngine();
        urlField = new TextField("https://www.google.com/");
        scriptArea = new TextArea();
        scriptEngine = new ScriptEngine(webEngine);

        setupUI();
    }

    private void setupUI() {
        urlField.setOnAction(e -> webEngine.load(urlField.getText()));

        // Create execute button for scripts
        Button executeButton = new Button("Execute Script");
        executeButton.setOnAction(e -> scriptEngine.executeScript(scriptArea.getText()));

        // Create script controls panel
        HBox scriptControls = new HBox(executeButton);
        VBox scriptPanel = new VBox(scriptArea, scriptControls);

        // Create browser layout
        BorderPane browserLayout = new BorderPane();
        browserLayout.setTop(urlField);
        browserLayout.setCenter(webView);

        // Create main split pane
        mainLayout = new SplitPane();
        mainLayout.getItems().addAll(browserLayout, scriptPanel);
        mainLayout.setDividerPosition(0, 0.7); // 70% for browser, 30% for script
    }

    public Scene getScene() {
        return new Scene(mainLayout, 1200, 800);  // Use the stored mainLayout
    }

    public void loadInitialUrl() {
        webEngine.load(urlField.getText());
    }
}