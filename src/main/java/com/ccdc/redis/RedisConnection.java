package com.ccdc.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {
	public static String HOST = "192.168.43.69";
	public static int PORT = 6380;
	public static int MAX_ACTIVE = 1024;
	public static int MAX_IDLE = 200;
	public static int MAX_WAIT = 10000;

	public static JedisPool jedisPool = null;

	/*
	 * 初始化redis连接池
	 */
	public static void initPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);// 最大连接数
			config.setMaxIdle(MAX_IDLE);// 最大空闲连接数
			config.setMaxWaitMillis(MAX_WAIT);// 获取可用连接的最大等待时间

			jedisPool = new JedisPool(config, HOST, PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取jedis实例
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool == null) {
				initPool();
			}
			Jedis jedis = jedisPool.getResource();
//			jedis.auth("redis");// 密码
			return jedis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
