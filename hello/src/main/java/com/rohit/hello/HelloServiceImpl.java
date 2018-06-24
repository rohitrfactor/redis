package com.rohit.hello;

import java.util.ArrayList;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.rohit.hello.queue.RedisMessagePublisher;

@Service("helloService")
public class HelloServiceImpl implements HelloService,MessageListener{
	
	private RedisMessagePublisher redisMessagePublisher;
	
	private static ArrayList<String> insurerList = new ArrayList<>();
	
	@Override
	public String getAllInsurer() {
		if(insurerList == null||insurerList.isEmpty()) {
			//fetch insurer list from db
			ArrayList<String> insurerListFromDB = getInsurerListFromDB();
			insurerList.clear();
			insurerList.addAll(insurerListFromDB);
		}
		return insurerList.toString();
	}
	
	private ArrayList<String> getInsurerListFromDB(){
		ArrayList<String> insurerListFromDB = new ArrayList<String>();
		insurerListFromDB.add("MAX");
		insurerListFromDB.add("HDFC");
		insurerListFromDB.add("LIC");
		return insurerListFromDB;
	}
	private void addInsurerInCache(String insurer) {
		insurerList.add(insurer);
	}
	
	private void removeInsurerInCache(String insurer) {
		insurerList.remove(insurer);
	}
	
	private void clearAllInsurerInCache() {
		insurerList.clear();
	}
	
	
	@Override
	public String addInsurer(String insurer) {
		// add insurer in db
		//update the local cache
		addInsurerInCache(insurer);
		// broadcast event to all nodes to update or invalidate cache
		redisMessagePublisher.publish("invalidate cache");
		return "{\"status\":\"true\"}";
	}

	@Override
	public String removeInsurer(String insurer) {
		// remove insurer in db
		// update the local cache
		removeInsurerInCache(insurer);
		// broadcast event to all nodes to update or invalidate cache
		redisMessagePublisher.publish("invalidate cache");
		return "{\"status\":\"true\"}";
	}

	@Override
	public String clearInsurerCache() {
		clearAllInsurerInCache();
		//broadcast event to all nodes to clear or invalidate cache
		redisMessagePublisher.publish("invalidate cache");
		return "{\"status\":\"true\"}";
	}

	@Override
	public void onMessage(Message arg0, byte[] arg1) {
			System.out.println("Event Listened");
			clearAllInsurerInCache();
	}
}