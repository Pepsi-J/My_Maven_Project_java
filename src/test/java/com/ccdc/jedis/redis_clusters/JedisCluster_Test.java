package com.ccdc.jedis.redis_clusters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

public class JedisCluster_Test {
	JedisCluster jediscluster = null;
	JedisPoolConfig poolConfig = new JedisPoolConfig();
	Set<HostAndPort> nodes = new HashSet<HostAndPort>();

	@Before
	public void conn() {
		// ���������
		poolConfig.setMaxTotal(1);
		// ��������
		poolConfig.setMaxIdle(1);
		// �������ȴ�ʱ�䣬����������ʱ�仹δ��ȡ�����ӣ���ᱨJedisException�쳣��
		// Could not get a resource from the pool
		poolConfig.setMaxWaitMillis(1000);
		// ��Ⱥ����
		nodes.add(new HostAndPort("192.168.43.69", 7000));
		nodes.add(new HostAndPort("192.168.43.69", 7001));
		nodes.add(new HostAndPort("192.168.43.69", 7002));
		nodes.add(new HostAndPort("192.168.43.69", 7003));
		nodes.add(new HostAndPort("192.168.43.69", 7004));
		nodes.add(new HostAndPort("192.168.43.69", 7005));
		jediscluster = new JedisCluster(nodes, poolConfig);
	}

	/**
	 * ����String
	 */
	@Test
	public void operStr() {

		// ���һ������
		jediscluster.set("username", "jianghf");
		// ��ȡһ������
		String username = jediscluster.get("username");
		System.out.println("�û�����" + username);

		// ɾ��
//		jedis.del("username");
	}

	/**
	 * ����hash
	 */
	@Test
	public void operHash() {
		// ���һ��
		jediscluster.hset("goodsInfo", "goodsName", "Vivo Neo3");
		// ��ȡһ��
		String goodsName = jediscluster.hget("goodsInfo", "goodsName");
		System.out.println("��Ʒ����" + goodsName);

		Map<String, String> hash = new HashMap<String, String>();
		hash.put("orderSn", "20171226122301");
		hash.put("orderStatus", "�ύԤ����");

		// ��Ӷ���
		jediscluster.hmset("orderInfo", hash);
		System.out.println("---------------");
		// ��ȡ����
		List<String> strList = jediscluster.hmget("orderInfo", "orderSn", "orderStatus");
		for (String string : strList) {
			System.out.println(string);
		}
		System.out.println("---------------");
		// ��ȡȫ��

		Map<String, String> orderInfoMap = jediscluster.hgetAll("orderInfo");
		for (Entry<String, String> entry : orderInfoMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		// ɾ��
//		jedis.hdel("orderInfo", "orderStatus");
	}

	/**
	 * ����list
	 */
	@Test
	public void operList() {
		// ���
		for (int i = 0; i < 10; i++) {
			jediscluster.lpush("animals", "dog" + i, "cat" + i, "fish" + i);
		}

		// ��ȡ
		String reString = jediscluster.rpop("animals");
		System.out.println(reString);
		// ��ҳ��ѯ start����ʼ���� end :��������
		List<String> strList = jediscluster.lrange("animals", 0, 9);
		for (String string : strList) {
			System.out.println(string);
		}
		System.out.println("----------------");
		// ��ȡ������
		Long total = jediscluster.llen("animals");
		System.out.println("������" + total);
		// ɾ��
//		jedis.lrem("animals", 1, "dog0");
	}

	/**
	 * ����set
	 */
	@Test
	public void operSet() {
		// ���
		jediscluster.sadd("members", "xiaoming", "xiaohua", "xiaohui", "xiaochen");
		// ��ȡ
		Set<String> members = jediscluster.smembers("members");
		for (String string : members) {
			System.out.println(string);
		}
		// ɾ��
//		jedis.srem("members", "xiaohui");
	}

	/**
	 * ����sorted set-�Զ�����
	 */
	@Test
	public void operSortedSet() {
		Map<String, Double> scoreMembers = new HashMap<String, Double>();
		scoreMembers.put("С��", 89D);
		scoreMembers.put("С��", 93D);
		scoreMembers.put("С��", 88D);
		// ���
		jediscluster.zadd("score", scoreMembers);
		// ��ȡstart ����ʼ���� end���������� �����������ѯ
		Set<String> strSet = jediscluster.zrange("score", 0, 1);
		for (String string : strSet) {
			System.out.println(string);
		}
		System.out.println("-------------");
		// �����ѯ������ȡ��Ա�ķ���
		Set<Tuple> tupleSet = jediscluster.zrevrangeWithScores("score", 0, 1);
		for (Tuple tuple : tupleSet) {
			// ��Ա
			String element = tuple.getElement();
			Double score = tuple.getScore();
			System.out.println(element + ":" + score);
		}
		System.out.println("-----------------");
		// ��ȡ������
		Long total = jediscluster.zcard("score");
		System.out.println("��������" + total);
		// ɾ��
//		jedis.zrem("score", "xiaopeng");
	}

	@After
	public void close() {
		try {
//			jediscluster.close();
//			System.out.println("�رռ�Ⱥ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}