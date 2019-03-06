package com.wq.spring.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * toMessage 就是 Java 对象转换为 Message，fromMessage 就是 Message 转为 Java 对象
 */
public class TextMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		return new Message(object.toString().getBytes(), messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		System.out.println("来了老弟!!!!");
		String contentType = message.getMessageProperties().getContentType();
		if(null != contentType && contentType.contains("text")) {//如果contentType是text/plain即文本格式的消息 将它转为String
			String result=new String(message.getBody());
			System.out.println("老弟这就走了呀!!!");
			return result;
		}
		return message.getBody();
	}

}
