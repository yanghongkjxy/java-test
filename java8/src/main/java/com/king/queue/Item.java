package com.king.queue;



public class Item implements Comparable<Item> {

	    private String name;  
	    private int population;  
	    public Item(String name, int population)  
	    {  
	        this.name = name;  
	        this.population = population;  
	    }  
	    public String getName()  
	    {  
	         return this.name;  
	    }  
	  
	    public int getPopulation()  
	    {  
	         return this.population;  
	    }  
	    public String toString()  
	    {  
	         return getName() + " - " + getPopulation();  
	    }  
	@Override
	public int compareTo(Item o) {
		
			// TODO Auto-generated method stub
			int numbera = this.getPopulation();
			int numberb = o.getPopulation();
			if (numberb > numbera) {
				return 1;
			} else if (numberb < numbera) {
				return -1;
			} else {
				return 0;
			}

		}
	}

