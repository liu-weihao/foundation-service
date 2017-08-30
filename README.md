foundation-service工程包含了项目所有的基础服务：config server、eureka server和zuul server。 

项目使用了spring全家桶，以spring boot和spring cloud为主。 


先上一张架构图：

![foundation-service架构图](https://github.com/liu-weihao/foundation-service/blob/master/architecture.png?raw=true)

1、config server，顾名思义，配置服务器。配置信息量较小，而且通常是一次性的，所以就不做HA了。

这个配置服务管理的是包括foundation-service在内的所有基于spring boot项目的配置信息，可以理解为简化（甚至替换）了原先的application.yml(properties)。

如有项目需要接入配置中心，在maven项目的resources目录下新建bootstrap.yml,添加一下内容（<font color=red>请替换 {} 中的内容</font>）：

	spring:
  		application:
			name: {app_name}
  		cloud:
			config:
  				uri: http://{config_server_ip}:{port}
  				fail-fast: true

项目的配置文件统一托管在gitlab上，由于spring cloud config做不到指定子文件夹的功能，如果要维护多个部署环境的配置文件，建议使用branch区分，当然，master分支必须是对应生产环境。

gitlab上的配置文件需要按照一定的规则创建，命名规则如下：
	
	{app_name}-{profile}.yml