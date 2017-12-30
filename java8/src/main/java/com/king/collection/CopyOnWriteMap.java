package com.king.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {
	 private volatile Map<K, V> internalMap;
	 
	    public CopyOnWriteMap() {
	        internalMap = new HashMap<K, V>();
	    }
	 
	    public V put(K key, V value) {
	 
	        synchronized (this) {
	            Map<K, V> newMap = new HashMap<K, V>(internalMap);
	            V val = newMap.put(key, value);
	            internalMap = newMap;
	            return val;
	        }
	    }
	 
	    public V get(Object key) {
	        return internalMap.get(key);
	    }
	 
	    public void putAll(Map<? extends K, ? extends V> newData) {
	        synchronized (this) {
	            Map<K, V> newMap = new HashMap<K, V>(internalMap);
	            newMap.putAll(newData);
	            internalMap = newMap;
	        }
	    }

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return internalMap.size();
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return internalMap.isEmpty();
		}

		@Override
		public boolean containsKey(Object key) {
			// TODO Auto-generated method stub
			return internalMap.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			// TODO Auto-generated method stub
			return internalMap.containsValue(value);
		}

		@Override
		public V remove(Object key) {
			// TODO Auto-generated method stub
			return internalMap.remove(key);
		}

		@Override
		public void clear() {
			internalMap.clear();
			
		}

		@Override
		public Set<K> keySet() {
			// TODO Auto-generated method stub
			return internalMap.keySet();
		}

		@Override
		public Collection<V> values() {
			// TODO Auto-generated method stub
			return internalMap.values();
		}

		@Override
		public Set<java.util.Map.Entry<K, V>> entrySet() {
			// TODO Auto-generated method stub
			return internalMap.entrySet();
		}
}
