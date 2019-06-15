# TwitterCrawler Introduction
数据来源于 [Twitter Search](https://twitter.com/search-home) ,而没有用官方twitter api,可以不受api的限制.   
WARNING: please be polite and follow the crawler's politeness policy.

# TwitterCrawler Installation
基于spring boot 2.0  
[webmagic](http://webmagic.io/)爬虫框架  
Maven 3.6  
JDK 1.8  
需要翻墙，可设置翻墙代理ip、端口  
存储:oracle/mysql  

# TwitterCrawler Usage
resources/application.properties 配置数据库连接.  
resources/twi.properties 配置本地代理端口，twitter用户名或关键字.  
具体抓取数据，可根据url不同设置规则，可以搜索时间范围，包含与或条件等，本项目目前只做了根据 用户名+时间范围 获取数据.  


数据示例  
![image](https://github.com/casolxia/TwitterCrawler/blob/master/images/sample2.png)

数据示例  
![image](https://github.com/casolxia/TwitterCrawler/blob/master/images/sample1.png)

数据示例    
![image](https://github.com/casolxia/TwitterCrawler/blob/master/images/sample.png)



# Lisence
Lisenced under [Apache 2.0 lisence](http://opensource.org/licenses/Apache-2.0)


