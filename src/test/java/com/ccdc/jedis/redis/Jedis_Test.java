package com.ccdc.jedis.redis;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.ccdc.redis.RedisConnection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

/**
 * @author jhf
 *
 */
public class Jedis_Test {

	Jedis jedis = RedisConnection.getJedis();

	@Test
	public void KeyOperate() {
		System.out.println("======================key==========================");
		// �������
		System.out.println("��տ����������ݣ�" + jedis.flushDB());
		// �ж�key�����
		System.out.println("�ж�key999���Ƿ���ڣ�" + jedis.exists("key999"));
		System.out.println("����key001,value001��ֵ�ԣ�" + jedis.set("key001", "value001"));
		System.out.println("�ж�key001�Ƿ���ڣ�" + jedis.exists("key001"));
		// ���ϵͳ�����е�key
		System.out.println("����key002,value002��ֵ�ԣ�" + jedis.set("key002", "value002"));
		System.out.println("ϵͳ�����м����£�");
		Set<String> keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
		}
		// ɾ��ĳ��key,��key�����ڣ�����Ը����
		System.out.println("ϵͳ��ɾ��key002: " + jedis.del("key002"));
		System.out.println("�ж�key002�Ƿ���ڣ�" + jedis.exists("key002"));
		// ���� key001�Ĺ���ʱ��
		System.out.println("���� key001�Ĺ���ʱ��Ϊ5��:" + jedis.expire("key001", 5));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// �鿴ĳ��key��ʣ������ʱ��,��λ���롿.����������߲����ڵĶ�����-1
		System.out.println("�鿴key001��ʣ������ʱ�䣺" + jedis.ttl("key001"));
		// �Ƴ�ĳ��key������ʱ��
		System.out.println("�Ƴ�key001������ʱ�䣺" + jedis.persist("key001"));
		System.out.println("�鿴key001��ʣ������ʱ�䣺" + jedis.ttl("key001"));
		// �鿴key�������ֵ������
		System.out.println("�鿴key�������ֵ�����ͣ�" + jedis.type("key001"));
		/*
		 * һЩ����������1���޸ļ�����jedis.rename("key6", "key0");
		 * 2������ǰdb��key�ƶ���������db���У�jedis.move("foo", 1)
		 */
	}

	@Test
	public void StringOperate() {
		System.out.println("======================String_1==========================");
		// �������
		System.out.println("��տ����������ݣ�" + jedis.flushDB());

		System.out.println("=============��=============");
		jedis.set("key001", "value001");
		jedis.set("key002", "value002");
		jedis.set("key003", "value003");
		System.out.println("��������3����ֵ�����£�");
		System.out.println(jedis.get("key001"));
		System.out.println(jedis.get("key002"));
		System.out.println(jedis.get("key003"));

		System.out.println("=============ɾ=============");
		System.out.println("ɾ��key003��ֵ�ԣ�" + jedis.del("key003"));
		System.out.println("��ȡkey003����Ӧ��ֵ��" + jedis.get("key003"));

		System.out.println("=============��=============");
		// 1��ֱ�Ӹ���ԭ��������
		System.out.println("ֱ�Ӹ���key001ԭ�������ݣ�" + jedis.set("key001", "value001-update"));
		System.out.println("��ȡkey001��Ӧ����ֵ��" + jedis.get("key001"));
		// 2��ֱ�Ӹ���ԭ��������
		System.out.println("��key002ԭ��ֵ����׷�ӣ�" + jedis.append("key002", "+appendString"));
		System.out.println("��ȡkey002��Ӧ����ֵ" + jedis.get("key002"));

		System.out.println("=============����ɾ���飨�����=============");
		/**
		 * mset,mgetͬʱ�������޸ģ���ѯ�����ֵ�� �ȼ��ڣ� jedis.set("name","ssss");
		 * jedis.set("jarorwar","xxxx");
		 */
		System.out.println("һ��������key201,key202,key203,key204�����Ӧֵ��"
				+ jedis.mset("key201", "value201", "key202", "value202", "key203", "value203", "key204", "value204"));
		System.out.println(
				"һ���Ի�ȡkey201,key202,key203,key204���Զ�Ӧ��ֵ��" + jedis.mget("key201", "key202", "key203", "key204"));
		System.out.println("һ����ɾ��key201,key202��" + jedis.del(new String[] { "key201", "key202" }));
		System.out.println(
				"һ���Ի�ȡkey201,key202,key203,key204���Զ�Ӧ��ֵ��" + jedis.mget("key201", "key202", "key203", "key204"));
		System.out.println();

		// jedis�߱��Ĺ���shardedJedis��Ҳ��ֱ��ʹ�ã��������һЩǰ��û�ù��ķ���
		System.out.println("======================String_2==========================");
		// �������
		System.out.println("��տ����������ݣ�" + jedis.flushDB());

		System.out.println("=============������ֵ��ʱ��ֹ����ԭ��ֵ=============");
		System.out.println("ԭ��key301������ʱ������key301��" + jedis.setnx("key301", "value301"));
		System.out.println("ԭ��key302������ʱ������key302��" + jedis.setnx("key302", "value302"));
		System.out.println("��key302����ʱ����������key302��" + jedis.setnx("key302", "value302_new"));
		System.out.println("��ȡkey301��Ӧ��ֵ��" + jedis.get("key301"));
		System.out.println("��ȡkey302��Ӧ��ֵ��" + jedis.get("key302"));

		System.out.println("=============������Ч�ڼ�ֵ�Ա�ɾ��=============");
		// ����key����Ч�ڣ����洢����
		System.out.println("����key303����ָ������ʱ��Ϊ2��" + jedis.setex("key303", 2, "key303-2second"));
		System.out.println("��ȡkey303��Ӧ��ֵ��" + jedis.get("key303"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println("3��֮�󣬻�ȡkey303��Ӧ��ֵ��" + jedis.get("key303"));

		System.out.println("=============��ȡԭֵ������Ϊ��ֵһ�����=============");
		System.out.println("key302ԭֵ��" + jedis.getSet("key302", "value302-after-getset"));
		System.out.println("key302��ֵ��" + jedis.get("key302"));

		System.out.println("=============��ȡ�Ӵ�=============");
		System.out.println("��ȡkey302��Ӧֵ�е��Ӵ���" + jedis.getrange("key302", 5, 7));
	}

	@Test
	public void ListOperate() {
		System.out.println("======================list==========================");
		// �������
		System.out.println("��տ����������ݣ�" + jedis.flushDB());

		System.out.println("=============��=============");
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "ArrayList");
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "vector");
		jedis.lpush("stringlists", "LinkedList");
		jedis.lpush("stringlists", "MapList");
		jedis.lpush("stringlists", "SerialList");
		jedis.lpush("stringlists", "HashList");
		jedis.lpush("numberlists", "3");
		jedis.lpush("numberlists", "1");
		jedis.lpush("numberlists", "5");
		jedis.lpush("numberlists", "2");
		System.out.println("����Ԫ��-stringlists��" + jedis.lrange("stringlists", 0, -1));
		System.out.println("����Ԫ��-numberlists��" + jedis.lrange("numberlists", 0, -1));

		System.out.println("=============ɾ=============");
		// ɾ���б�ָ����ֵ ���ڶ�������Ϊɾ���ĸ��������ظ�ʱ������add��ȥ��ֵ�ȱ�ɾ�������ڳ�ջ
		System.out.println("�ɹ�ɾ��ָ��Ԫ�ظ���-stringlists��" + jedis.lrem("stringlists", 2, "vector"));
		System.out.println("ɾ��ָ��Ԫ��֮��-stringlists��" + jedis.lrange("stringlists", 0, -1));
		// ɾ���������������
		System.out.println("ɾ���±�0-3����֮���Ԫ�أ�" + jedis.ltrim("stringlists", 0, 3));
		System.out.println("ɾ��ָ������֮��Ԫ�غ�-stringlists��" + jedis.lrange("stringlists", 0, -1));
		// �б�Ԫ�س�ջ
		System.out.println("��ջԪ�أ�" + jedis.lpop("stringlists"));
		System.out.println("Ԫ�س�ջ��-stringlists��" + jedis.lrange("stringlists", 0, -1));

		System.out.println("=============��=============");
		// �޸��б���ָ���±��ֵ
		jedis.lset("stringlists", 0, "hello list!");
		System.out.println("�±�Ϊ0��ֵ�޸ĺ�-stringlists��" + jedis.lrange("stringlists", 0, -1));
		System.out.println("=============��=============");
		// ���鳤��
		System.out.println("����-stringlists��" + jedis.llen("stringlists"));
		System.out.println("����-numberlists��" + jedis.llen("numberlists"));
		// ����
		/*
		 * list�д��ַ���ʱ����ָ������Ϊalpha�������ʹ��SortingParams������ֱ��ʹ��sort("list")��
		 * �����"ERR One or more scores can't be converted into double"
		 */
		SortingParams sortingParameters = new SortingParams();
		sortingParameters.alpha();
		sortingParameters.limit(0, 3);
		System.out.println("���������Ľ��-stringlists��" + jedis.sort("stringlists", sortingParameters));
		System.out.println("���������Ľ��-numberlists��" + jedis.sort("numberlists"));
		// �Ӵ��� startΪԪ���±꣬endҲΪԪ���±ꣻ-1������һ��Ԫ�أ�-2�������ڶ���Ԫ��
		System.out.println("�Ӵ�-�ڶ�����ʼ��������" + jedis.lrange("stringlists", 1, -1));
		// ��ȡ�б�ָ���±��ֵ
		System.out.println("��ȡ�±�Ϊ2��Ԫ�أ�" + jedis.lindex("stringlists", 2) + "\n");
	}

	@Test
	public void SetOperate() {

		System.out.println("======================set==========================");
		// �������
		System.out.println("��տ����������ݣ�" + jedis.flushDB());

		System.out.println("=============��=============");
		System.out.println("��sets�����м���Ԫ��element001��" + jedis.sadd("sets", "element001"));
		System.out.println("��sets�����м���Ԫ��element002��" + jedis.sadd("sets", "element002"));
		System.out.println("��sets�����м���Ԫ��element003��" + jedis.sadd("sets", "element003"));
		System.out.println("��sets�����м���Ԫ��element004��" + jedis.sadd("sets", "element004"));
		System.out.println("�鿴sets�����е�����Ԫ��:" + jedis.smembers("sets"));
		System.out.println();

		System.out.println("=============ɾ=============");
		System.out.println("����sets��ɾ��Ԫ��element003��" + jedis.srem("sets", "element003"));
		System.out.println("�鿴sets�����е�����Ԫ��:" + jedis.smembers("sets"));
		/*
		 * System.out.println("sets����������λ�õ�Ԫ�س�ջ��"+jedis.spop("sets"));//ע����ջԪ��λ�þ�Ȼ������--
		 * ��ʵ������ System.out.println("�鿴sets�����е�����Ԫ��:"+jedis.smembers("sets"));
		 */
		System.out.println();

		System.out.println("=============��=============");
		System.out.println();

		System.out.println("=============��=============");
		System.out.println("�ж�element001�Ƿ��ڼ���sets�У�" + jedis.sismember("sets", "element001"));
		System.out.println("ѭ����ѯ��ȡsets�е�ÿ��Ԫ�أ�");
		Set<String> set = jedis.smembers("sets");
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			System.out.println(obj);
		}
		System.out.println();

		System.out.println("=============��������=============");
		System.out.println("sets1�����Ԫ��element001��" + jedis.sadd("sets1", "element001"));
		System.out.println("sets1�����Ԫ��element002��" + jedis.sadd("sets1", "element002"));
		System.out.println("sets1�����Ԫ��element003��" + jedis.sadd("sets1", "element003"));
		System.out.println("sets1�����Ԫ��element002��" + jedis.sadd("sets2", "element002"));
		System.out.println("sets1�����Ԫ��element003��" + jedis.sadd("sets2", "element003"));
		System.out.println("sets1�����Ԫ��element004��" + jedis.sadd("sets2", "element004"));
		System.out.println("�鿴sets1�����е�����Ԫ��:" + jedis.smembers("sets1"));
		System.out.println("�鿴sets2�����е�����Ԫ��:" + jedis.smembers("sets2"));
		System.out.println("sets1��sets2������" + jedis.sinter("sets1", "sets2"));
		System.out.println("sets1��sets2������" + jedis.sunion("sets1", "sets2"));
		System.out.println("sets1��sets2���" + jedis.sdiff("sets1", "sets2"));// ���set1���У�set2��û�е�Ԫ��

	}

	@Test
	public void SortedSetOperate() {
		System.out.println("======================zset==========================");
		// �������
		System.out.println(jedis.flushDB());

		System.out.println("=============��=============");
		System.out.println("zset�����Ԫ��element001��" + jedis.zadd("zset", 7.0, "element001"));
		System.out.println("zset�����Ԫ��element002��" + jedis.zadd("zset", 8.0, "element002"));
		System.out.println("zset�����Ԫ��element003��" + jedis.zadd("zset", 2.0, "element003"));
		System.out.println("zset�����Ԫ��element004��" + jedis.zadd("zset", 3.0, "element004"));
		System.out.println("zset�����е�����Ԫ�أ�" + jedis.zrange("zset", 0, -1));// ����Ȩ��ֵ����
		System.out.println();

		System.out.println("=============ɾ=============");
		System.out.println("zset��ɾ��Ԫ��element002��" + jedis.zrem("zset", "element002"));
		System.out.println("zset�����е�����Ԫ�أ�" + jedis.zrange("zset", 0, -1));
		System.out.println();

		System.out.println("=============��=============");
		System.out.println();

		System.out.println("=============��=============");
		System.out.println("ͳ��zset�����е�Ԫ���и�����" + jedis.zcard("zset"));
		System.out.println("ͳ��zset������Ȩ��ĳ����Χ�ڣ�1.0����5.0����Ԫ�صĸ�����" + jedis.zcount("zset", 1.0, 5.0));
		System.out.println("�鿴zset������element004��Ȩ�أ�" + jedis.zscore("zset", "element004"));
		System.out.println("�鿴�±�1��2��Χ�ڵ�Ԫ��ֵ��" + jedis.zrange("zset", 1, 2));

	}

	@Test
	public void HashOperate() {
		System.out.println("======================hash==========================");
		// �������
		System.out.println(jedis.flushDB());

		System.out.println("=============��=============");
		System.out.println("hashs�����key001��value001��ֵ�ԣ�" + jedis.hset("hashs", "key001", "value001"));
		System.out.println("hashs�����key002��value002��ֵ�ԣ�" + jedis.hset("hashs", "key002", "value002"));
		System.out.println("hashs�����key003��value003��ֵ�ԣ�" + jedis.hset("hashs", "key003", "value003"));
		System.out.println("����key004��4�����ͼ�ֵ�ԣ�" + jedis.hincrBy("hashs", "key004", 4l));
		System.out.println("hashs�е�����ֵ��" + jedis.hvals("hashs"));
		System.out.println();

		System.out.println("=============ɾ=============");
		System.out.println("hashs��ɾ��key002��ֵ�ԣ�" + jedis.hdel("hashs", "key002"));
		System.out.println("hashs�е�����ֵ��" + jedis.hvals("hashs"));
		System.out.println();

		System.out.println("=============��=============");
		System.out.println("key004���ͼ�ֵ��ֵ����100��" + jedis.hincrBy("hashs", "key004", 100l));
		System.out.println("hashs�е�����ֵ��" + jedis.hvals("hashs"));
		System.out.println();

		System.out.println("=============��=============");
		System.out.println("�ж�key003�Ƿ���ڣ�" + jedis.hexists("hashs", "key003"));
		System.out.println("��ȡkey004��Ӧ��ֵ��" + jedis.hget("hashs", "key004"));
		System.out.println("������ȡkey001��key003��Ӧ��ֵ��" + jedis.hmget("hashs", "key001", "key003"));
		System.out.println("��ȡhashs�����е�key��" + jedis.hkeys("hashs"));
		System.out.println("��ȡhashs�����е�value��" + jedis.hvals("hashs"));
		System.out.println();

	}

}
