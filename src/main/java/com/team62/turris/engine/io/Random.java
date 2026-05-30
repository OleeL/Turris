package com.team62.turris.engine.io;

public class Random {

	public static int integer(int min, int max)
	{
		return (int) (Math.random() * ((max - min) + 1)) + min;
	}
	
	public static double decimal(double min, double max)
	{
		if (min < 1 && max <= 1)
			return (Math.random() * ( max - min )) + min;
		else
			return (Math.random() * (( max - min ) +1 )) + min;
	}
}
