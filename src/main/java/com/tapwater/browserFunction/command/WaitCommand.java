package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

/**
 * 等待命令类
 * 实现脚本执行过程中的延时功能
 * 支持指定秒数的等待操作
 */
public class WaitCommand implements ScriptCommand {
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
     * 匹配以"wait "开头的命令
     * @param commandLine 要检查的命令行
     * @return 如果命令匹配返回true，否则返回false
     */
    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("wait ");
    }

    /**
     * 执行等待命令
     * 从命令中提取等待时间并执行延时操作
     * @param commandLine 要执行的命令内容
     */
    @Override
    public void execute(String commandLine) {
        // 提取等待时间，移除所有非数字字符
        String timeStr = commandLine.substring(5).trim().replaceAll("[^0-9]", "");
        try {
            int seconds = Integer.parseInt(timeStr);
            progressLog.appendText("Waiting for " + seconds + " seconds...\n");
            Thread.sleep(seconds * 1000);
            progressLog.appendText("Wait completed\n");
        } catch (InterruptedException | NumberFormatException e) {
            progressLog.appendText("Invalid wait time: " + timeStr + "\n");
        }
    }
}