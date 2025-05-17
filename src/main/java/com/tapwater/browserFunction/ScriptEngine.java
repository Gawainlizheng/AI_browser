package com.tapwater.browserFunction;

import com.tapwater.browserFunction.command.*;
import javafx.scene.web.WebEngine;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;

/**
 * 脚本引擎类
 * 负责解析和执行自动化脚本命令
 * 管理命令注册和执行流程
 */
public class ScriptEngine {
    /** 网页引擎实例，用于执行网页操作 */
    private final WebEngine webEngine;
    /** 进度日志区域，用于显示执行过程 */
    private final TextArea progressLog;
    /** 已注册的命令列表 */
    private final List<ScriptCommand> commands;
    /** 元素映射表，用于存储搜索到的元素 */
    private final Map<String, String> elementMap;

    /**
     * 脚本引擎构造函数
     * @param webEngine 网页引擎实例
     * @param progressLog 进度日志显示区域
     */
    public ScriptEngine(WebEngine webEngine, TextArea progressLog) {
        this.webEngine = webEngine;
        this.progressLog = progressLog;
        this.commands = new ArrayList<>();
        this.elementMap = new HashMap<>();
        registerDefaultCommands();
    }

    /**
     * 注册默认命令
     * 初始化所有内置的脚本命令
     */
    private void registerDefaultCommands() {
        registerCommand(new SearchTextCommand(elementMap));
        registerCommand(new ClickElementCommand(elementMap));
        registerCommand(new ExtractTextCommand());
        registerCommand(new NavigateCommand());
        registerCommand(new WaitCommand());
        registerCommand(new InputCommand());
    }

    /**
     * 注册新的脚本命令
     * @param command 要注册的命令实例
     */
    public void registerCommand(ScriptCommand command) {
        commands.add(command);
        command.setWebEngine(webEngine);
        command.setProgressLog(progressLog);
    }

    /**
     * 执行脚本
     * 解析脚本内容并执行相应的命令
     * @param script 要执行的脚本内容
     */
    public void executeScript(String script) {
        String[] lines = script.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            for (ScriptCommand cmd : commands) {
                if (cmd.matches(line)) {
                    progressLog.appendText("Executing: " + line + "\n");
                    cmd.execute(line);
                    break;
                }
            }
        }
    }
}