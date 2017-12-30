package com.king.queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class TestQueue {

	public static void main(String args[]) {
		Comparator<Item> OrderIsdn = new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				// TODO Auto-generated method stub
				int numbera = o1.getPopulation();
				int numberb = o2.getPopulation();
				if (numberb > numbera) {
					return 1;
				} else if (numberb < numbera) {
					return -1;
				} else {
					return 0;
				}

			}

		};
		Queue<Item> priorityQueue = new PriorityQueue<Item>(11, OrderIsdn);

//		Item t1 = new Item("t1", 1);
//		Item t3 = new Item("t3", 3);
//		Item t2 = new Item("t2", 2);
//		Item t4 = new Item("t4", 0);
//		priorityQueue.add(t1);
//		priorityQueue.add(t3);
//		priorityQueue.add(t2);
//		priorityQueue.add(t4);
		// System.out.println(priorityQueue.poll().toString());
		Runnable runPush = () -> {
			for (int i = 0; i < 50; i++) {
				priorityQueue.add(new Item("t_"+Thread.currentThread().getName() +"_"+ i, i));
			}
		};
	
		new Thread(runPush).start();
		new Thread(runPush).start();
		new Thread(runPush).start();
		Runnable runPop = () -> {
			//for (int i = 0; i < 50; i++) {
			while(priorityQueue.size()>0)
				 System.out.println(priorityQueue.poll().toString());
			//}
		};
		new Thread(runPop).start();
	}
}
