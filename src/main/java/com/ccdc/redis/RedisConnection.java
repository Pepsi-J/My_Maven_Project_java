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
	 * ��ʼ��redis���ӳ�
	 */
	public static void initPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);// ���������
			config.setMaxIdle(MAX_IDLE);// ������������
			config.setMaxWaitMillis(MAX_WAIT);// ��ȡ�������ӵ����ȴ�ʱ��

			jedisPool = new JedisPool(config, HOST, PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ��ȡjedisʵ��
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool == null) {
				initPool();
			}
			Jedis jedis = jedisPool.getResource();
//			jedis.auth("redis");// ����
			return jedis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
