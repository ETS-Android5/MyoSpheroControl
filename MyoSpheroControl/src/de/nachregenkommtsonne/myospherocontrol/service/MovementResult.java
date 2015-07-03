package de.nachregenkommtsonne.myospherocontrol.service;

public class MovementResult{
	private float _direction;
	private float _speed;
	private int _red;
	private int _green;
	private int _blue;
	
	public int get_red() {
		return _red;
	}

	public int get_green() {
		return _green;
	}

	public int get_blue() {
		return _blue;
	}

	public MovementResult(float direction, float speed, int red, int green, int blue) {
		super();
		_direction = direction;
		_speed = speed;
		_red = red;
		_green = green;
		_blue = blue;
	}
	
	public float get_direction() {
		return _direction;
	}
	
	public float get_speed() {
		return _speed;
	}
}