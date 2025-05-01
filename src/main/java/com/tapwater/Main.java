package com.tapwater;


import com.tapwater.browser.Browser;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Browser browser = new Browser();

        primaryStage.setTitle("Power AI Browser");
        primaryStage.setScene(browser.getScene());
        primaryStage.show();

        // Load initial URL
        browser.loadInitialUrl();
    }

    public static void main(String[] args) {
        launch(args);
    }
}