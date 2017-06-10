
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.hardware.lcd.Font;
import lejos.hardware.Device;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.AnalogSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class MainPc {
	public static void main(String[] args) {
		int[][] tiles= {{ 1, 9, 3 }, { 7, 2, 6 }, { 5, 4, 8 }};//9 vacio;
	       
	       Board puzz = new Board(tiles);       
	       List<Integer> actions;
	       List<String> actions2;
	       
	       Graph gx = new Graph();
	       
	       if(puzz.isSolvable()){
	    	   gx=puzz.TreeSolution();
	    	   actions=gx.chainActions();
		       actions2=gx.showActions(actions);
		       //gx.printDOT();
		       System.out.println(actions2);
	       }
	       
	       //System.out.println(actions);
	       //Delay.msDelay(5000);
	       
	       //Delay.msDelay(5000);
	       //puzz.displayRobot();
	       gx.printDOT();
		
		
		
		}

	



}