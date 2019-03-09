Hello大家好，This is 小吴学习Rabbitmq之路!
rabbitmq-api 为基础api与高级特性源码
rabbitmq-spring 为整合springamqp框架源码
rabbitmq-springboot-producer & rabbitmq-springboot-consumer 为整合springboot框架源码
rabbitmq-springcloudstream-producer & rabbitmq-springcloudstream-consumer 为整合spring cloud stream源码

### 一、下载安装（初学者绿色安装）
- 准备:

> yum install build-essential openssl openssl-devel unixODBC unixODBC- devel make gcc gcc-c++ kernel-devel m4 ncurses-devel tk tc xz

- 下载
> wget http://www.rabbitmq.com/releases/erlang/erlang-18.3-1.el7.centos.x86_64.rpm 
wget http://repo.iotti.biz/CentOS/7/x86_64/socat-1.7.3.2-5.el7.lux.x86_64.rpm
wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm
- 修改主机名
> vim /etc/hostname 
uname -a 查看hostname
hostname newname 修改下，让hostname立刻生效。
vi /etc/hosts 修改原hostname为 newname
vi /etc/sysconfig/network 修改原hostname为 
newname , reboot重启后也生效
--------------------- 

- 安装
> 第1步：rpm -ivh [erlang-18.3-1.el7.centos.x86_64.rpm](http://www.rabbitmq.com/releases/erlang/erlang-18.3-1.el7.centos.x86_64.rpm)
> 第2步：rpm -ivh socat....rpm
> 第3步：rpm -ivh rabbitmq....rpm
![安装步骤](https://upload-images.jianshu.io/upload_images/2836779-d7c7f09b2fef709e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 修改配置文件
> vim /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.5/ebin/rabbit.app
比如修改密码、配置等等，例如: loopback_ users中的<< "guest">>,只保留guest
![修改配置文件](https://upload-images.jianshu.io/upload_images/2836779-9a27f41ab4281909.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

-  服务启动和停止
> 启动: rabbitmq-server start & (注：&表示后台启动，需要注意 修改hostname不然默认在localhost.log)
![服务启动](https://upload-images.jianshu.io/upload_images/2836779-fa94db42abc5864d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 输入lsof -i:5672验证是否启动成功。
![启动成功验证](https://upload-images.jianshu.io/upload_images/2836779-b7693debaf7f3126.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
停止: rabbitmqctl app_stop
查看已安装的插件列表：rabbitmq-plugins list
使用管理插件: rabbitmq-plugins enable rabbitmq_management
登录通过一开始设定的账号密码guest/guest登录即可。![管控台](https://upload-images.jianshu.io/upload_images/2836779-c956157e49cbdfac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 二、简单命令
- 2.1关闭应用
rabbitmqctl stop_app
- 2.2启动应用
rabbitmqctl start_app
- 2.3查看节点状态
rabbitmqctl status
- 2.4其他
>![用户相关1](https://upload-images.jianshu.io/upload_images/2836779-0df874f1a055872f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![用户相关2](https://upload-images.jianshu.io/upload_images/2836779-ec7deb92884335e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![虚拟主机](https://upload-images.jianshu.io/upload_images/2836779-124c162b6dde8368.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![队列](https://upload-images.jianshu.io/upload_images/2836779-2f8e4f9efc9252b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![集群](https://upload-images.jianshu.io/upload_images/2836779-c7bdb805a42df35a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![节点](https://upload-images.jianshu.io/upload_images/2836779-a66ee2fb41cc5705.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 三、基本组件
> ![Exchange](https://upload-images.jianshu.io/upload_images/2836779-1a64cb8448addc90.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![Topic方式](https://upload-images.jianshu.io/upload_images/2836779-1d7e438ca0ad081b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![Topic方式2](https://upload-images.jianshu.io/upload_images/2836779-403042d1d6821ed2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![Fanout方式](https://upload-images.jianshu.io/upload_images/2836779-d0d569b6ee41a9ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![Fanout方式2](https://upload-images.jianshu.io/upload_images/2836779-b259a90b29b65f12.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 四、补充
![补充1](https://upload-images.jianshu.io/upload_images/2836779-481b5be65515a107.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

