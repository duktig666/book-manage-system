# book-manage-system
## 简介

> 基于Java Swing + MySQL 的图书管理系统。
>
> 初学MySQL时所做的demo实战。

### 技术栈

- Java Swing作为UI
- MySQL作为存储
- JDBC原生方法操作mysql（封装了公共的增删改查方法，参看`src.model.access.Connect.java`）
- MVC作为整体架构
- 密码加密使用MD5

### 参看

需求分析参看：[图书管理系统需求分析](./doc/图书管理系统需求分析.md)

ER图参看：[图书管理系统E-R图](./doc/图书管理系统E-R图.jpeg)

### 操作

普通用户账号：20170755101  密码：123456

管理员默认账号：123123  密码：123123

### 部分示意图

![登录界面](https://gitee.com/koala010/typora/raw/master/img/20210818091144.png)



![管理员端](https://gitee.com/koala010/typora/raw/master/img/20210818091207.png)



## 注意事项

本项目（demo）是初学MySQL时完成的实战案例。当时开发的比较早，存在以下问题：

1. 学习阶段的作品，很多代码不够规范（后来学习《阿里巴巴Java开发手册》后，严格要求自己，写优雅、规范的代码）。
2. 当时使用的Eclipse开发，编码默认使用的GBK，非UTF8，记得在编译器修改编码呦，否则部分代码和注释会有乱码现象。

## 文件夹结构

```
- doc 文档
	-- 图书管理系统需求分析
	-- 图书管理系统E-R图.jpeg
- lib 需引入的jar包
- sql sql文件
- src 源代码
	-- Controller 控制层
	-- Images 图片资源
	-- Model 模型层
		--- access 数据库的增删改查操作（相当于Mybatis的mapper层）
			---- Connect.java 放置了公共的增删改查方法
		--- table 放置表相关的映射
	- Tool 工具层
	- View 视图层
```

入口文件为`src.view.Main.java`

