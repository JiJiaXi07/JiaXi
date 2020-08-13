package com.xiaoshu.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.Major;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MyMessageListener implements MessageListener{
	@Autowired
	JedisPool jedisPool;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		try {
			TextMessage msg=(TextMessage)message;
			String string;
			string = msg.getText();
			Major major = JSONObject.parseObject(string,Major.class);
			
			Jedis jedis = jedisPool.getResource();
			jedis.hset("Major:",major.getMdname(), major.getMdId()+"");
			
			
			
			System.out.println("信息："+string);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
