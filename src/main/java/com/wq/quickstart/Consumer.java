package com.wq.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by wuqingvika on 2018/11/8.
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//1.创建一个ConnectionFactory并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.124");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");//默认是斜杠
        //2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3.通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //4.声明创建一个队列
        String queueName="test001";
        channel.queueDeclare(queueName,true,false,false,null);//第二个参数true代表持久化，第三个是代表是否独占相当于锁
        //5.创建消费者
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //6.设置channel
        channel.basicConsume(queueName,true,consumer);
        //7.获取消息
        QueueingConsumer.Delivery delivery=consumer.nextDelivery();
        String msg=new String(delivery.getBody());
        System.out.println("消费端收到:"+msg);
       // Envelope envelope = delivery.getEnvelope();
        //long deliveryTag = envelope.getDeliveryTag();

    }

}
