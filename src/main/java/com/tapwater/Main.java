package com.tapwater;

import com.tapwater.browser.Browser;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * AI Browser 的主应用程序类
 * 负责初始化浏览器界面和启动应用程序
 */
public class Main extends Application {

    /**
     * 示例脚本：
     * 1. 访问Google主页
     * 2. 等待10秒
     * 3. 在Google搜索框中搜索"baidu"
     * 4. 在搜索框中输入"abd"
     * 5. 点击搜索结果中的百度链接
     */
    /*
    go https://www.google.com/
    wait 10s
    search Google Search = @baidu
    input #APjFqb abd
    click @baidu
    */

    /**
     * JavaFX应用程序的入口点
     * 初始化浏览器界面并显示主窗口
     *
     * @param primaryStage JavaFX主舞台，用于显示应用程序窗口
     */
    @Override
    public void start(Stage primaryStage) {
        // 创建浏览器实例
        Browser browser = new Browser();

        // 设置窗口标题和场景
        primaryStage.setTitle("Power AI Browser");
        primaryStage.setScene(browser.getScene());
        primaryStage.show();

        // 加载初始URL
        browser.loadInitialUrl();
    }

    /**
     * 应用程序的主入口点
     * 启动JavaFX应用程序
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }
}