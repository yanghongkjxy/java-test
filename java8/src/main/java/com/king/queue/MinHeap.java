package com.king.queue;



public class MinHeap<T extends QueueItem> extends Heap<T> {

	@Override
	public void heapify(T[] a, int i, int heapLength) {
		int l = left(i);
		int r = right(i);
		int smallest = -1;
		/**
		 * 下面两个if条件句用来找到三个元素中的最小元素的位置smallest? 
		 * s < heapLength说明l在数组内，i非叶子结点；
		 */
		System.out.println("i:"+i+",heapLength:"+heapLength);
		if (l < heapLength && a[i].compareTo(a[l]) > 0) {
			smallest = l;
		} else {
			smallest = i;
		}
        // r < heapLength说明r在数组内
		if (r < heapLength && a[smallest].compareTo(a[r]) > 0) {
			smallest = r;
		}
		// 如果i处元素不是最小的，就把i处的元素与最小处元素交换，交换会使元素下�?
		if (i != smallest) {
			T temp = a[i];
			a[i] = a[smallest];
			a[smallest] = temp;
			// 交换元素后，以a[i]为根的树可能不在满足大根堆�?�质，于是�?�归调用该方�?
			heapify(a, smallest, heapLength);
		}
	}

}
