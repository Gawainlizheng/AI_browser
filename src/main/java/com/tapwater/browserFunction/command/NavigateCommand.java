package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

/**
 * 导航命令类
 * 实现网页导航功能
 * 支持使用"go"或"navigate"命令访问指定URL
 */
public class NavigateCommand implements ScriptCommand {
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
     * 匹配以"go "或"navigate "开头的命令
     * @param commandLine 要检查的命令行
     * @return 如果命令匹配返回true，否则返回false
     */
    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("go ") || commandLine.startsWith("navigate ");
    }

    /**
     * 执行导航命令
     * 从命令中提取URL并加载对应网页
     * @param commandLine 要执行的命令内容
     */
    @Override
    public void execute(String commandLine) {
        String url = commandLine.substring(commandLine.indexOf(" ") + 1).trim();
        progressLog.appendText("Navigating to: " + url + "\n");
        webEngine.load(url);
    }
}