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
		// 最大连接数
		poolConfig.setMaxTotal(1);
		// 最大空闲数
		poolConfig.setMaxIdle(1);
		// 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
		// Could not get a resource from the pool
		poolConfig.setMaxWaitMillis(1000);
		// 集群设置
		nodes.add(new HostAndPort("192.168.43.69", 7000));
		nodes.add(new HostAndPort("192.168.43.69", 7001));
		nodes.add(new HostAndPort("192.168.43.69", 7002));
		nodes.add(new HostAndPort("192.168.43.69", 7003));
		nodes.add(new HostAndPort("192.168.43.69", 7004));
		nodes.add(new HostAndPort("192.168.43.69", 7005));
		jediscluster = new JedisCluster(nodes, poolConfig);
	}

	/**
	 * 操作String
	 */
	@Test
	public void operStr() {

		// 添加一条数据
		jediscluster.set("username", "jianghf");
		// 获取一条数据
		String username = jediscluster.get("username");
		System.out.println("用户名：" + username);

		// 删除
//		jedis.del("username");
	}

	/**
	 * 操作hash
	 */
	@Test
	public void operHash() {
		// 添加一条
		jediscluster.hset("goodsInfo", "goodsName", "Vivo Neo3");
		// 获取一条
		String goodsName = jediscluster.hget("goodsInfo", "goodsName");
		System.out.println("商品名称" + goodsName);

		Map<String, String> hash = new HashMap<String, String>();
		hash.put("orderSn", "20171226122301");
		hash.put("orderStatus", "提交预订单");

		// 添加多条
		jediscluster.hmset("orderInfo", hash);
		System.out.println("---------------");
		// 获取多条
		List<String> strList = jediscluster.hmget("orderInfo", "orderSn", "orderStatus");
		for (String string : strList) {
			System.out.println(string);
		}
		System.out.println("---------------");
		// 获取全部

		Map<String, String> orderInfoMap = jediscluster.hgetAll("orderInfo");
		for (Entry<String, String> entry : orderInfoMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		// 删除
//		jedis.hdel("orderInfo", "orderStatus");
	}

	/**
	 * 操作list
	 */
	@Test
	public void operList() {
		// 添加
		for (int i = 0; i < 10; i++) {
			jediscluster.lpush("animals", "dog" + i, "cat" + i, "fish" + i);
		}

		// 获取
		String reString = jediscluster.rpop("animals");
		System.out.println(reString);
		// 分页查询 start：起始条数 end :结束条数
		List<String> strList = jediscluster.lrange("animals", 0, 9);
		for (String string : strList) {
			System.out.println(string);
		}
		System.out.println("----------------");
		// 获取总条数
		Long total = jediscluster.llen("animals");
		System.out.println("总条数" + total);
		// 删除
//		jedis.lrem("animals", 1, "dog0");
	}

	/**
	 * 操作set
	 */
	@Test
	public void operSet() {
		// 添加
		jediscluster.sadd("members", "xiaoming", "xiaohua", "xiaohui", "xiaochen");
		// 获取
		Set<String> members = jediscluster.smembers("members");
		for (String string : members) {
			System.out.println(string);
		}
		// 删除
//		jedis.srem("members", "xiaohui");
	}

	/**
	 * 操作sorted set-自动排序
	 */
	@Test
	public void operSortedSet() {
		Map<String, Double> scoreMembers = new HashMap<String, Double>();
		scoreMembers.put("小明", 89D);
		scoreMembers.put("小李", 93D);
		scoreMembers.put("小胡", 88D);
		// 添加
		jediscluster.zadd("score", scoreMembers);
		// 获取start ：起始条数 end：结束条数 按分数升序查询
		Set<String> strSet = jediscluster.zrange("score", 0, 1);
		for (String string : strSet) {
			System.out.println(string);
		}
		System.out.println("-------------");
		// 降序查询，并获取成员的分数
		Set<Tuple> tupleSet = jediscluster.zrevrangeWithScores("score", 0, 1);
		for (Tuple tuple : tupleSet) {
			// 成员
			String element = tuple.getElement();
			Double score = tuple.getScore();
			System.out.println(element + ":" + score);
		}
		System.out.println("-----------------");
		// 获取总条数
		Long total = jediscluster.zcard("score");
		System.out.println("总条数：" + total);
		// 删除
//		jedis.zrem("score", "xiaopeng");
	}

	@After
	public void close() {
		try {
//			jediscluster.close();
//			System.out.println("关闭集群");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}