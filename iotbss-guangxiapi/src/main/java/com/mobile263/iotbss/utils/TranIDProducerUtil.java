package com.mobile263.iotbss.utils;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.springframework.stereotype.Component;

public class TranIDProducerUtil {
	
	
	public TranIDProducerUtil() {
		
	}
	/*
	 * 获取当前时间 ，时间格式:年月日时分秒毫秒
	 */
	public static  String getTimestamp() {
		Long time = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String now =  formatter.format(time);
		System.out.println(now);;
		return now;
	}
	
	/*
	 * 获取7位随机数
	 */
	public static String get7RandomNum() {
		int min = 1000000;
		int max = 9999999;
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return Integer.toString(tmp % (max - min + 1) + min);
	}
	
	/**
	 * 生成tranID
	 * @throws InterruptedException 
	 */
	public synchronized static  String getTranID() throws InterruptedException {
		Thread.sleep(1);
		return getTimestamp() + get7RandomNum();
	}

	public static void main(String[] args){
		try {
			System.out.println(getTranID());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
