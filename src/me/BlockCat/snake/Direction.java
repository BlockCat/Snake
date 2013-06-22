package me.BlockCat.snake;


public enum Direction {
	NORTH(0),EAST(1),SOUTH(2),WEST(3);
	
	public int dir;
	
	Direction(int dir) {
		this.dir = dir;
	}
	
	public int getDirection() {
		return dir;
	}
	public double getDirectionRadian() {
		return (dir*90)*(Math.PI/180);
	}
}
