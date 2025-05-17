package com.tapwater.browserFunction.command;

import java.util.Map;
import javafx.scene.web.WebEngine;
import javafx.scene.control.TextArea;
import java.nio.charset.StandardCharsets;

/**
 * 搜索文本命令类
 * 实现网页文本内容的搜索功能
 * 支持搜索文本并保存匹配元素的引用
 */
public class SearchTextCommand implements ScriptCommand {
    /** 网页引擎实例 */
    private WebEngine webEngine;
    /** 进度日志显示区域 */
    private TextArea progressLog;
    /** 元素映射表，用于存储搜索到的元素选择器 */
    private final Map<String, String> elementMap;

    /**
     * 构造函数
     * @param elementMap 元素映射表，用于存储搜索到的元素选择器
     */
    public SearchTextCommand(Map<String, String> elementMap) {
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
     * 匹配以"search "或"find "开头的命令
     * @param commandLine 要检查的命令行
     * @return 如果命令匹配返回true，否则返回false
     */
    @Override
    public boolean matches(String commandLine) {
        return commandLine.startsWith("search ") || commandLine.startsWith("find ");
    }

    /**
     * 执行搜索命令
     * 在网页中搜索指定文本，并保存匹配元素的引用
     * @param commandLine 要执行的命令内容，格式为"search text = varname"
     */
    @Override
    public void execute(String commandLine) {
        try {
            // 处理UTF-8编码
            String utf8CommandLine = new String(commandLine.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            // 解析命令，分离搜索文本和变量名
            String[] parts = utf8CommandLine.split("=");
            if (parts.length < 2) {
                progressLog.appendText("Invalid search format. Use: search text = varname\n");
                return;
            }

            String searchPart = parts[0].trim();
            String varName = parts[1].trim();
            String text = searchPart.substring(searchPart.indexOf(" ") + 1).trim();

            // 构建并执行JavaScript搜索和高亮操作
            String highlightScript = String.format(
                    "var elements = document.querySelectorAll('*:not(script):not(style)');" +
                            "var foundIds = [];" +
                            "for (var i = 0; i < elements.length; i++) {" +
                            "    if (elements[i].textContent.includes('%s')) {" +
                            "        elements[i].style.border = '2px solid red';" +
                            "        foundIds.push(elements[i].id || elements[i].getAttribute('data-id') || '');" +
                            "    }" +
                            "}" +
                            "foundIds.length > 0 ? foundIds[0] : '';", text);

            Object result = webEngine.executeScript(highlightScript);
            String elementId = result != null ? result.toString() : "";

            // 保存搜索结果
            if (!elementId.isEmpty()) {
                elementMap.put(varName, "#" + elementId);
                progressLog.appendText("Found element with text '" + text + "' and saved as @" + varName + "\n");
            } else {
                progressLog.appendText("No element found with text '" + text + "'\n");
            }
        } catch (Exception e) {
            progressLog.appendText("Error in search command: " + e.getMessage() + "\n");
        }
    }
}