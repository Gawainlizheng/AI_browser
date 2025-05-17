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

/**
 * 浏览器核心类
 * 负责管理浏览器界面和功能组件
 * 包括网页显示、URL输入、脚本执行等功能
 */
public class Browser {
    /** 网页视图组件，用于显示网页内容 */
    private WebView webView;
    /** 网页引擎，负责加载和渲染网页 */
    private WebEngine webEngine;
    /** URL输入框，用于输入和显示当前网页地址 */
    private TextField urlField;
    /** 脚本输入区域，用于编写自动化脚本 */
    private TextArea scriptArea;
    /** 执行日志区域，用于显示脚本执行过程和结果 */
    private TextArea progressLog;
    /** 脚本引擎，负责解析和执行自动化脚本 */
    private ScriptEngine scriptEngine;
    /** 主布局面板，用于组织界面组件 */
    private SplitPane mainLayout;

    /**
     * 浏览器构造函数
     * 初始化所有必要的组件和界面元素
     */
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

    /**
     * 设置用户界面
     * 创建和配置所有UI组件，设置事件处理器
     */
    private void setupUI() {
        // 设置URL输入框的回车事件，加载输入的URL
        urlField.setOnAction(e -> webEngine.load(urlField.getText()));

        // 创建脚本执行按钮
        Button executeButton = new Button("Execute Script");
        executeButton.setOnAction(e -> {
            progressLog.appendText("=== Executing Script ===\n");
            scriptEngine.executeScript(scriptArea.getText());
        });

        // 创建脚本控制面板
        VBox scriptPanel = new VBox(
                new VBox(scriptArea, executeButton),
                new VBox(progressLog)
        );
        scriptPanel.setSpacing(10);

        // 创建浏览器布局
        BorderPane browserLayout = new BorderPane();
        browserLayout.setTop(urlField);
        browserLayout.setCenter(webView);

        // 创建主分割面板
        mainLayout = new SplitPane();
        mainLayout.getItems().addAll(browserLayout, scriptPanel);
        mainLayout.setDividerPosition(0, 0.7);
    }

    /**
     * 获取浏览器场景
     * @return 包含浏览器界面的Scene对象
     */
    public Scene getScene() {
        return new Scene(mainLayout, 1200, 800);
    }

    /**
     * 加载初始URL
     * 在浏览器启动时加载默认网页
     */
    public void loadInitialUrl() {
        webEngine.load(urlField.getText());
    }
}