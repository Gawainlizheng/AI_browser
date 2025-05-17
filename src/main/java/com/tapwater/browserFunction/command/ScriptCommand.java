package com.tapwater.browserFunction.command;

import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;

/**
 * 脚本命令接口
 * 定义了所有脚本命令必须实现的基本方法
 * 用于实现不同的自动化操作命令
 */
public interface ScriptCommand {
    /**
     * 设置网页引擎
     * @param webEngine 用于执行网页操作的引擎实例
     */
    void setWebEngine(WebEngine webEngine);

    /**
     * 设置进度日志
     * @param progressLog 用于显示命令执行过程的日志区域
     */
    void setProgressLog(TextArea progressLog);

    /**
     * 检查命令是否匹配
     * @param commandLine 要检查的命令行
     * @return 如果命令匹配返回true，否则返回false
     */
    boolean matches(String commandLine);

    /**
     * 执行命令
     * @param commandLine 要执行的命令内容
     */
    void execute(String commandLine);
}