package com.videoadmin.utils.redis;

import redis.clients.jedis.ShardedJedis;

public interface Executor<K> {
	public K execute(ShardedJedis jedis);
}
