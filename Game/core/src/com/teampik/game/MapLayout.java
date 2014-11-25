package com.teampik.game;

public class MapLayout {
	
	public final static int width = 45;
	public final static int height = 30;
	
	public final static int WATER = 1000;
	public final static int LAND = 2000;
	public final static int TRACK = 3000;
	public final static int ZOO  = 4000;
	
	public final static int[][] testLayout = new int[][]{
		{WATER, WATER, WATER, WATER, WATER, WATER, WATER, WATER, WATER, WATER},
		{WATER, WATER, WATER, LAND, LAND, LAND, LAND, WATER, WATER, WATER},
		{WATER, WATER, WATER, WATER, LAND, WATER, WATER, WATER, WATER, WATER},
		{WATER, WATER, WATER, WATER, LAND, WATER, WATER, WATER, WATER, WATER},
		{WATER, WATER, WATER, WATER, LAND, WATER, WATER, WATER, WATER, WATER},
		{WATER, WATER, LAND, LAND, LAND, WATER, WATER, WATER, WATER, WATER},
		{WATER, WATER, WATER, WATER, WATER, WATER, WATER, WATER, WATER, WATER},
		
	};
	

}
