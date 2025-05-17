# AI Browser

AI Browser 是一个基于 JavaFX 开发的智能浏览器，它集成了自动化脚本执行功能，可以帮助用户实现网页自动化操作。

## 功能特点

1. 现代化的浏览器界面
   - 支持基本的网页浏览功能
   - 可调整的界面布局
   - 实时URL输入和导航

2. 脚本执行功能
   - 支持自定义脚本编写
   - 实时执行日志显示
   - 脚本执行状态反馈

3. 自动化操作支持
   - 支持网页元素定位和操作
   - 支持等待和延时操作
   - 支持表单输入和点击操作

## 使用方法

1. 启动应用
   ```bash
   mvn clean javafx:run
   ```

2. 基本浏览
   - 在顶部URL输入框中输入网址
   - 按回车键访问网页

3. 脚本执行
   - 在右侧脚本区域编写自动化脚本
   - 点击"Execute Script"按钮执行脚本
   - 在日志区域查看执行结果

## 脚本语法

脚本支持以下基本命令：

- `go [url]` - 访问指定URL
- `wait [seconds]` - 等待指定秒数
- `search [selector] = [text]` - 搜索元素
- `input [selector] [text]` - 输入文本
- `click [selector]` - 点击元素

示例脚本：
```
go https://www.google.com/
wait 10s
search Google Search = @baidu
input #APjFqb abd
click @baidu
```

## 技术栈

- Java 8+
- JavaFX
- Maven

## 项目结构

```
src/main/java/com/tapwater/
├── Main.java                 # 应用程序入口
├── browser/
│   └── Browser.java         # 浏览器核心实现
└── browserFunction/
    ├── ScriptEngine.java    # 脚本引擎
    └── command/             # 命令实现
```

## 开发环境设置

1. 确保安装了 JDK 8 或更高版本
2. 安装 Maven
3. 克隆项目
4. 运行 `mvn clean install` 安装依赖

## 贡献指南

欢迎提交 Issue 和 Pull Request 来帮助改进项目。

## 许可证

MIT License