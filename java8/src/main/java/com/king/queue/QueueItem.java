package com.king.queue;



public abstract class QueueItem implements Comparable<QueueItem> {

	    protected int priority=50;  
	    
	    public QueueItem(int priority)  
	    {         
	    	setPriority(priority);
	    }  
	     
	  
	    public int getPriority()  
	    {  
	         return this.priority;  
	    } 
	    /**
	     * 优先级,范围1-100 数字越大，优先级越低
	     * @param priority
	     * @return
	     */
	    public int setPriority(int priority)  
	    {  
	    	if(priority>100)
	    	{
	    		priority=100;
	    	}
	    	this.priority=priority;
	         return this.priority;  
	    }
	   
	@Override
	public int compareTo(QueueItem o) {
		
		if(null==o)
		{
			return 0;
		}
			int numbera = this.getPriority();
			int numberb = o.getPriority();
			if (numberb > numbera) {
				return -1;
			} else if (numberb < numbera) {
				return 1;
			} else {
				return 0;
			}

		}
	}

