
# my community
## 部署
### 依赖
- Git
- JDK
- Maven
- MySQL
### 步骤
- yum update
- yum install git
- mkdir Blog
- cd Blog
- git clone https://github.com/debugkillsme/mycommunity.git
- yum install mvn(提前安装maven,jdk8)
- cd mycommunity
- mvn clean compile
- mvn package -DskipTests
- java -jar -Dspring.profiles.active=production target/peanutbutter-0.0.1-SNAPSHOT.jar --jasypt.encryptor.password=(自己的密钥密码)

## 技术栈
|  技术   |  链接   |
| --- | --- |
|  Spring Boot   |  http://spring.io/guides|
|   MyBatis  |  https://mybatis.org/mybatis-3/zh/index.html   |
|   MyBatis Generator  |  http://mybatis.org/generator/   |
| Spring Boot+Mybatis|http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/|
|   H2  |   http://www.h2database.com/html/main.html  |
|   Flyway  |   https://flywaydb.org/getstarted/firststeps/maven  |
|Lombok| https://www.projectlombok.org |
|Bootstrap|https://v3.bootcss.com/getting-started/|
|Github OAuth|https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/|
|UFile|https://github.com/ucloud/ufile-sdk-java|
|Bootstrap|https://v3.bootcss.com/getting-started/|
|jQuery|https://api.jquery.com/|
|maven|https://mvnrepository.com/|
|markdown|https://pandao.github.io/editor.md/|

## 扩展资料 
[Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)   
[es](https://elasticsearch.cn/explore)    
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
[Bootstrap](https://v3.bootcss.com/getting-started/)    
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
[菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)    
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
[Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[Markdown 插件](http://editor.md.ipandao.com/)   
[UFfile SDK](https://github.com/ucloud/ufile-sdk-java)  
[Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)  
[Git](https://git-scm.com/download)   
[Visual Paradigm](https://www.visual-paradigm.com)    
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://www.projectlombok.org)    
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
[云主机mysql安装](https://www.bilibili.com/video/BV19s411G784/?spm_id_from=333.337.top_right_bar_window_custom_collection.content.click&vd_source=7c1c4730def06400bdcbdb2945d4eb7f)

##常用快捷键
mvn(mybatis生成命令):mvn '-Dmybatis.generator.overwrite=true' mybatis-generator:generate
查看方法参数:ctrl+p\
跳转到方法使用处:ctrl+B \
查看方法具体实现:ctrl+alt+B\
切换文件:alt+->/<-\
搜索文件:shift+ctrl+N\
窗口切换:alt+tab\
在网页中查找:ctrl+F\
调整代码格式:ctrl+alt+L\
最大化编辑页面:ctrl+shift+F12\
生成setter and getter:alt+insert\
生成实例对象:ctrl+alt+V\
重构方法或变量:ctrl+F6\
查看到方法依赖处:alt+F7\
将段落代码抽成方法:ctrl+alt+M\
将变量设置成方法参数:ctrl+alt+p\
构造方法:alt+shift+enter\
代码块缩进:tab或者shift+tab\
optimize imports:ctrl+alt+O\
xml文件注释快捷键:ctrl+/

##Linux常用命令
cp:复制文件\
ctrl+c:强制终端程序\
ssh -o ServerAliveInterval=60 user@sshserver(ssh登录保活)\
ps -aux | grep java(显示用户启动进程)
rm -rf dirname(删除文件(夹))

##git常用命令
git add .\
git commit -m "提交说明"\
推到远程仓库:git push\
从远程仓库更新本地仓库:git pull(或者 git fetch + git merge)\
本地仓库版本回滚: git reset --hard


##PageInfo
一些参数:
private int pageNum;   			//当前页
private int pageSize;			//每页显示数据条数
private int size;				//当前页的数量
private int startRow; 			//始页首行行号
private int endRow;				//尾页尾行行号
private long total;				//总记录数
private int pages;				//总页数
private List<T> list;			//查询结果的数据
private int firstPage;			//首页
private int prePage;			//上一页
private int nextPage;			// 下一页
private int lastPage;			//最后一页
private boolean isFirstPage;	//是不是第一页
private boolean isLastPage;		//是不是最后一页
private boolean hasPreviousPage;//有没有上一页
private boolean hasNextPage;	//有没有下一页
private int navigatePages;		//所有导航页号
private int[] navigatepageNums;	//导航页码数



##可改进
- 添加redis实现限流防刷
- 压力测试+并发场景