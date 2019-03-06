package com.wq.spring;

import com.wq.spring.adapter.MessageDelegate;
import com.wq.spring.convert.TextMessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@ComponentScan({"com.wq.spring.*"})
public class RabbitMQConfig {

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("123.207.2.127:5757");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setVirtualHost("/");
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
	

	/**
	 * 针对消费者配置
	 * 1. 设置交换机类型
	 * 2. 将队列绑定到交换机
	 FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	 HeadersExchange ：通过添加属性key-value匹配
	 DirectExchange:按照routingkey分发到指定队列
	 TopicExchange:多关键字匹配
	 */
	@Bean
	public TopicExchange exchange001() {
		return new TopicExchange("topic001", true, false);
	}

	@Bean
	public Queue queue001() {
		return new Queue("queue001", true); //队列持久
	}

	@Bean
	public Binding binding001() {
		return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
	}

	@Bean
	public TopicExchange exchange002() {
		return new TopicExchange("topic002", true, false);
	}

	@Bean
	public Queue queue002() {
		return new Queue("queue002", true); //队列持久
	}

	@Bean
	public Binding binding002() {
		return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
	}

	@Bean
	public Queue queue003() {
		return new Queue("queue003", true); //队列持久
	}

	@Bean
	public Binding binding003() {
		return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
	}

	@Bean
	public Queue queue_image() {
		return new Queue("image_queue", true); //队列持久
	}

	@Bean
	public Queue queue_pdf() {
		return new Queue("pdf_queue", true); //队列持久
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}


    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setExposeListenerChannel(true);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue + "_" + UUID.randomUUID().toString();
            }
        });
       /* container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String msg = new String(message.getBody());
                System.err.println("----------消费者: " + msg);
            }
        });*/

		//1 适配器方式. 默认是有自己的方法名字的：handleMessage
		 // 可以自己指定一个方法的名字: consumeMessage
		 // 也可以添加一个转换器: 从字节数组转换为String
		/* MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		 adapter.setDefaultListenerMethod("consumeMessage");
		 adapter.setMessageConverter(new TextMessageConverter());
		 container.setMessageListener(adapter);*/

		/**
		 * 2 适配器方式: 我们的队列名称 和 方法名称 也可以进行一一的匹配
		 */
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		adapter.setMessageConverter(new TextMessageConverter());
		Map<String, String> queueOrTagToMethodName = new HashMap<>();

		queueOrTagToMethodName.put("queue002", "method2");
		queueOrTagToMethodName.put("queue001", "method1");
		adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
		container.setMessageListener(adapter);


        return container;
    }

}
