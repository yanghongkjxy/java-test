package com.king.queue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;



/**
 * 分区优先级队列
 * @author yh
 *
 * @param <T>
 */
public class ShardPriorityQueue<T extends QueueItem> {

	private final static String SHARD_NAME_PREFIX = "shard_";	
	//private final Comparator<? super T> comparator;
	private int shardNum = 0;
	// 队列总长度
	private AtomicLong length = new AtomicLong(0);

	//private Map<String, QuickPriorityQueue<T>> shardMap = new HashMap<>();
	//private Map<String, PriorityQueue<T>> shardMap = new HashMap<>();
	private Map<String, List<T>> shardMap = new HashMap<>();
	 

	/**
	 * 分区优先级队列
	 * 
	 * @param shardNum
	 */
	public ShardPriorityQueue(int shardNum) {
		if (shardNum > 10) {
			throw new IllegalArgumentException("shardNum must less then 10");
		}
		this.shardNum = shardNum;		
		init();
		
	}
	/*public ShardPriorityQueue(int shardNum,Comparator<? super T> comparator) {
		if (shardNum > 10) {
			throw new IllegalArgumentException("shardNum must less then 10");
		}
		this.shardNum = shardNum;
		this.comparator= comparator;
		init();
		
	}*/
	
	/**
	 * 初始化队列
	 */
	private void init() {
		for (int i = 0; i < this.shardNum; i++) {
			//初始化
			//shardMap.put(SHARD_NAME_PREFIX + i, new PriorityQueue<T>(1000)); 
			shardMap.put(SHARD_NAME_PREFIX + i, new CopyOnWriteArrayList<T>()); // 高优先级
		}
	}

	/**
	 * 获取优先级最高的元素，并从队列中删除该元素
	 * 
	 * @return
	 */
	public T poll() {
		System.out.println("poll start");
		//QuickPriorityQueue<T> queue = null;
		//PriorityQueue<T> queue = null;
		List<T> queue = null;
		T result = null;
		for (int i = 0; i < this.shardNum; i++) {
			queue = shardMap.get(SHARD_NAME_PREFIX + i);
			if (null != queue && queue.size()>0) {
				//result = queue.heapExtractMin();
				//result=queue.poll();
				result=queue.get(0);
				queue.remove(0);
				System.out.println("shardName: "+SHARD_NAME_PREFIX + i);
				if (null == result) {
					continue;
				}
				decreaseLength();
				break;
			}
		}
		System.out.println("poll end");
		return result;

	}

	/**
	 * 减少队列长度
	 */
	private void decreaseLength() {
		length.decrementAndGet();

	}

	public void add(T obj) {
		System.out.println("add start");
		if (null == obj) {
			System.out.println("obj must be not null");
			return;
		}
		String shardName = getShardName(((QueueItem) obj).getPriority());
		//PriorityQueue<T> queue = shardMap.get(shardName);
		//QuickPriorityQueue<T> queue = shardMap.get(shardName);
		List<T> queue = shardMap.get(shardName);
		if (null != queue) {
			//queue.minHeapInsert(obj);
			//queue.add(obj);
			queue.add(obj);
			Collections.sort(queue);
			/*if(null!=comparator)
			{
			Collections.sort(queue,comparator);
			}*/
			increaseLength();
		}
		System.out.println("add end,current length:" + getLength());
	}

	/**
	 * 增加队列长度
	 */
	private void increaseLength() {
		length.incrementAndGet();

	}

	/**
	 * 获取队列长度
	 * 
	 * @return
	 */
	public long getLength() {
		 return length.get();
		
	}

	/**
	 * 获取分区名称
	 * 
	 * @param priority
	 * @return
	 */
	private String getShardName(int priority) {
		if (priority > 100) {
			priority = 100;
		}
		int num = 100 / this.shardNum;
		return SHARD_NAME_PREFIX + (priority / num);
	}

}
