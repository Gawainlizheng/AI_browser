package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

/**
 * 输入命令类
 * 实现网页表单元素的文本输入功能
 * 支持向指定选择器的输入框输入文本
 */
public class InputCommand implements ScriptCommand {
    /** 网页引擎实例 */
    private WebEngine webEngine;
    /** 进度日志显示区域 */
    private TextArea progressLog;

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
     * 匹配以"input "开头的命令
     * @param commandLine 要检查的命令行
     * @return 如果命令匹配返回true，否则返回false
     */
    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("input ");
    }

    /**
     * 执行输入命令
     * 向指定选择器的输入框输入文本
     * @param commandLine 要执行的命令内容，格式为"input selector text"
     */
    @Override
    public void execute(String commandLine) {
        // 解析命令，分离选择器和要输入的文本
        String rest = commandLine.substring(6).trim();
        String[] parts = rest.split(" ", 2);
        if (parts.length < 2) return;

        String selector = parts[0];
        String text = parts[1];

        // 构建并执行JavaScript输入操作
        String script = String.format(
                "var element = document.querySelector('%s');" +
                        "if (element) {" +
                        "   element.value = '"+ text +"';" +
                        "   return true;" +
                        "}" +
                        "return false;", selector, text.replace("'", "\\'"));
        System.out.println("script " + script);
        Boolean success = (Boolean) webEngine.executeScript(script);
        if (success) {
            progressLog.appendText("Entered text '" + text + "' into " + selector + "\n");
        } else {
            progressLog.appendText("Failed to enter text into " + selector + "\n");
        }
    }
}