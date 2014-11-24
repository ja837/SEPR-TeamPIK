package com.teampik.game;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class CoordinateGenerator {
	
	public static Vector2 GetCoord(int id)
	{
		String s = Integer.toString(id);
		String x = s.substring(0,1);
		String y = s.substring(2, 3);
		
		Vector2 coords = new Vector2();
		coords.x = Integer.parseInt(x);
		coords.y = Integer.parseInt(y);
		
		return coords;
	}
	
	
	public static int GenerateTileID(int x, int y)
	{
		String sx = Integer.toString(x);
		String sy = Integer.toString(y);
		if (x < 10){
			sx = "0" + sx;
		}
		if (y < 10){
			sy = "0" + sy;
		}
		
		String s = sx + sy;
		int id = Integer.parseInt(s);
		
		return id;
	}
	
	

}
