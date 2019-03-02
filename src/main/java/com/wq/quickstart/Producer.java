package com.wq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by wuqingvika on 2018/11/8.
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个ConnectionFactory并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        /*connectionFactory.setHost("192.168.1.124");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");//默认是斜杠*/
        connectionFactory.setHost("123.207.2.127");
        connectionFactory.setPort(5757);
        connectionFactory.setVirtualHost("/");
        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3.通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //4.通过channel发送数据
        String msg="hello wuqingvika,hello rabbitmq！";
        //   1--exchange 2---routingkey
        // 如果是exchange为空 则使用default
        channel.basicPublish("","test001",null,msg.getBytes());
        //5.记得要关闭相关的连接
        channel.close();
        connection.close();
    }
}
