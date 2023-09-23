<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Ape Frame</h1>
<h4 align="center">基于SpringBoot开发的轻量级框架</h4>
<p align="center">
<a href='https://gitee.com/classicChickenWings/ape-frame/stargazers'>
<img src='https://gitee.com/classicChickenWings/ape-frame/badge/star.svg?theme=dark' alt='star'>
</a>
<a href="https://gitee.com/classicChickenWings/ape-frame">
<img src="https://img.shields.io/badge/version-v1.0-brightgreen.svg">
</a>
<a href="https://gitee.com/classicChickenWings/ape-frame">
<img src="https://img.shields.io/badge/微信-jingdianjichi-brightgreen.svg">
</a>
</p>

## ✨项目简介
ape-frame，ape是“猿”的意思，正好符合我们程序猿的称号！我希望未来这个框架是专属于我们程序猿的首选开发框架。

一直想做一款适用于中小企业的轻量级快速开发框架，涵盖平时业务开发的常用场景，做到开箱即用。用户可根据自身情况选择组件来进行使用。采取组件化开发模式。

比如用户需要redis，则选择redis组件，需要websocket，则引入websocket组件，用户自身不需要再开发什么，只需要按照组件规则进行使用即可。

同时，有些经典的工具以及经典的设计模式代码，提供了大量实例参考，用户的业务场景一旦用到，就可以直接使用。

项目整体采用maven结构开发，封装了大量的模块，彼此解耦。满足用户日常开发需要。

希望大家可以帮忙点点Star，您的Star就是对我最大的支持。

## 🔥内置项目模块
### ape-cloud
微服务模块更新中，目前具备以下模块
* ape-cloud-eureka：eureka服务注册组件
* ape-cloud-eureka-server：eureka服务端用于服务治理与服务发现
* ape-cloud-home：用于微服务调用案例的首页微服务
* ape-cloud-sku：用于微服务调用案例的sku微服务
### ape-common
* ape-common-job：分布式任务调度组件
* ape-common-log：日志组件，提供日志切面自动记录及异步日志提升性能
* ape-common-mybatisplus：采用Mybatisplus作为与数据库交互
* ape-common-redis：缓存组件，提供基于redis的操作封装，redis分布式锁，guava的cache工具类
* ape-common-starter：启动类组件，与启动类相关的功能，放到此组件处
* ape-common-swagger：swagger组件，提供整体项目访问api的入口及方法文档
* ape-common-test：测试组件，集成springboot-test，及代码单元测试，代码覆盖率，行覆盖率检测
* ape-common-tool：常用的工具类组件，满足业务日常开发的各种需要，保障安全性，低入侵性
* ape-common-web：web组件，提供统一异常处理，web模块转换，统一返回值
* ape-common-websocket：websocket组件，提供一套带鉴权的websocket，引入即用，简单方便
### ape-demo
demo里提供了大量的实例，教大家如何直接使用这个项目框架，大家在开发中，可以直接参考这个模块来建立自己的项目进行使用。
目前已经提供的示例功能
* redis缓存使用
* 链路追踪
* 基于redis的延时任务实现方式
* 基于guava的本地二级缓存封装
* 分布式锁封装
* 完整的一套crud流程
* mapstruct的使用方式
* 各种lambda常用表达式的用法
* 设计模式的实际案例，工厂、建造者、策略、模板等
* 项目启动预热，可预热缓存，单例对象，线程池等
* 分布式任务的job的编写
* websocket的使用
* 线上，预发，测试等开发环境隔离
* linux下打包部署
* freemarker导出复杂word表单
* 自定义banner
### ape-dependencies
该模块为一个父pom模块，提供项目整体的maven包的锁定及规范，统一升级，统一引入。

## 💡内置功能
* 做脚手架的意义
* linux下安装mysql
* 如何涉及一个项目模块，如何拆分
* lombok演示及集成
* 集成druid连接池及数据库连接监控
* 集成Mybatisplus的优化器及拦截器
* 统一数据库基础实体封装
* 集成swagger，并自定义信息
* easycode自动生成代码
* 自定义banner
* linux下安装redis
* redistemplate集成
* 封装redisutil
* 项目启动自预热缓存
* 手动封装一个分布式锁
* 注解缓存集成
* log4j集成
* 基于disruptor的异步日志
* freemarker实现复杂表单导出
* 设计模式实战demo，模板模式，工厂模式，策略模式，构造者模式，过滤器模式
* websocket集成+鉴权
* 线上、预发、开发、测试环境的隔离
* springboot-maven插件打包部署
* httpclient的优化
* 集成springbootTest
* 服务预热
* 分布式任务调度xxljob集成

## ☀️添砖加瓦
欢迎大家提issue一起完善，以及提供各种宝贵建议，持续做成商业化开发框架。
如果您感觉我们的代码有需要优化的地方或者有更好的方案欢迎随时提pr。

[//]: # (可添加微信进行交流，鸡翅老哥会拉你进入项目群。)

[//]: # (如果您想看完整的教学视频，可以加入粉丝群了解学习。)

[//]: # (<div>)

[//]: # (<div style="display: inline-block">)

[//]: # (<p>)

[//]: # (<img height="300px" src="https://s3.bmp.ovh/imgs/2023/01/08/1c97491276151891.jpg">)

[//]: # (</p>)

[//]: # (</div>)

[//]: # (</div>)


# 🐾贡献代码的步骤
1. 在Gitee上fork项目到自己的repo
2. 把fork过去的项目也就是你的项目clone到你的本地
3. 修改代码
4. commit后push到自己的库
5. 登录Gitee在你仓库首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。 等待维护者合并