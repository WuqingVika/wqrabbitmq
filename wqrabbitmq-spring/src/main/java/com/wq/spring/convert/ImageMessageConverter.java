package com.wq.spring.convert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class ImageMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		throw new MessageConversionException(" convert error ! ");
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		System.err.println("-----------Image MessageConverter----------");
		
		Object _extName = message.getMessageProperties().getHeaders().get("extName");
		String extName = _extName == null ? "png" : _extName.toString();
		
		byte[] body = message.getBody();
		String fileName = UUID.randomUUID().toString();//D:\wqsoftware\wqdoc 是已经存在的文件夹
		String path = "d:/wqsoftware/wqdoc/" + fileName + "." + extName;
		File f = new File(path);
		try {
			Files.copy(new ByteArrayInputStream(body), f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

}
