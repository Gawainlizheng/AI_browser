package com.tapwater.browserFunction.command;

import java.util.Map;
import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

/**
 * 点击元素命令类
 * 实现网页元素的点击操作
 * 支持直接选择器和变量引用的点击操作
 */
public class ClickElementCommand implements ScriptCommand {
    /** 网页引擎实例 */
    private WebEngine webEngine;
    /** 进度日志显示区域 */
    private TextArea progressLog;
    /** 元素映射表，用于存储和查找元素选择器 */
    private final Map<String, String> elementMap;

    /**
     * 构造函数
     * @param elementMap 元素映射表，用于存储和查找元素选择器
     */
    public ClickElementCommand(Map<String, String> elementMap) {
        this.elementMap = elementMap;
    }

    /**
     * 设置网页引擎
     * @param webEngine 用于执行网页操作的引擎实例
     */
    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    /**
     * 设置进度日志
     * @param progressLog 用于显示命令执行过程的日志区域
     */
    @Override
    public void setProgressLog(TextArea progressLog) {
        this.progressLog = progressLog;
    }

    /**
     * 检查命令是否匹配
     * 匹配以"click "开头的命令
     * @param commandLine 要检查的命令行
     * @return 如果命令匹配返回true，否则返回false
     */
    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("click ");
    }

    /**
     * 执行点击命令
     * 支持直接选择器和变量引用的点击操作
     * @param commandLine 要执行的命令内容
     */
    @Override
    public void execute(String commandLine) {
        String selector = commandLine.substring(6).trim();

        // 检查是否是变量引用（以@开头）
        if (selector.startsWith("@")) {
            String varName = selector.substring(1);
            selector = elementMap.getOrDefault(varName, selector);
            progressLog.appendText("Resolved @" + varName + " to " + selector + "\n");
        }

        // 构建并执行JavaScript点击操作
        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "if (element) {" +
                        "   element.click();" +
                        "   return true;" +
                        "}" +
                        "return false;", selector);

        Boolean clicked = (Boolean) webEngine.executeScript(script);
        if (clicked) {
            progressLog.appendText("Clicked element: " + selector + "\n");
        } else {
            progressLog.appendText("Failed to click element: " + selector + "\n");
        }
    }
}