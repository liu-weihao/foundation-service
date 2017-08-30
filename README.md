foundation-service工程包含了项目所有的基础服务：config server、eureka server和zuul server。 

项目使用了spring全家桶，以spring boot和spring cloud为主。 


先上一张架构图：

![foundation-service架构图](https://github.com/liu-weihao/foundation-service/blob/master/architecture.png?raw=true)

1、config server，顾名思义，配置服务器。配置信息量较小，而且通常是一次性的，所以就不做HA了。

这个配置服务管理的是包括foundation-service在内的所有基于spring boot项目的配置信息，可以理解为简化（甚至替换）了原先的application.yml(properties)。

如有项目(config client)需要接入配置中心，在maven项目的resources目录下新建bootstrap.yml,添加一下内容（<font color=red>请替换 {} 中的内容，注意调整yml缩进格式</font>）：

	spring:
	  application:
	    name: {app_name}
	  cloud:
	    config:
	      uri: http://{config_server_ip}:{port}
	      fail-fast: true

添加Maven依赖：

	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>

## 最佳实践： ##
	spring:
	  application:
	    name: user
	  cloud:
	    config:
	      uri: http://127.0.0.1:8888
	      fail-fast: true
	
	---
	spring:
	  profiles:
	    active: dev

以上为config client项目的bootstrap.yml配置文件的最佳实践，没有特殊情况，不应该在文件中出现出配置中心以外的配置项。

项目的配置文件统一托管在gitlab上，采用http协议。由于spring cloud config做不到指定子文件夹的功能，如果要维护多个部署环境的配置文件，建议使用branch区分，当然，master分支必须是对应生产环境。

gitlab上的配置文件需要按照一定的规则创建，命名规则如下：
	
	{app_name}-{profile}.yml

2、eureka server，用于微服务架构中的服务治理工作，主要作为服务的注册与发现者。spring cloud对于Eureka的支持简直就是与生俱来的，所以常常被作为首选。其次是Consul，最差的是Zookeeper。区别于Zookeeper，Eureka被设计成为了一个去中心化的架构，对于CAP三要素，实现了AP，而Zookeeper实现的是CP。

除了config server和一些公共的项目依赖，不出意外，每个服务（后面讲到的Zuul Server也不例外）都将成为一个Eureka Client，注册到Eureka Server中。这就需要在bootstrap.yml中进行配置，而这部分配置被放在了config server中进行管理，所以，项目中所涉及到的配置信息还是上述所讲到的。

添加Maven依赖：

	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>