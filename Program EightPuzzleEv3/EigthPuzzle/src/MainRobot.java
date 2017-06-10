
import java.io.IOException;
import java.util.List;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.hardware.lcd.Font;
import lejos.hardware.Device;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.AnalogSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class MainRobot {
	public static RegulatedMotor m4 = new EV3LargeRegulatedMotor(MotorPort.D);
	public static RegulatedMotor m3 = new EV3LargeRegulatedMotor(MotorPort.C);
	
	public static RegulatedMotor m2 = new EV3MediumRegulatedMotor(MotorPort.B);
	
	public static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4); 
	public static SensorMode color = colorSensor.getRGBMode();
	public static boolean tengoObjeto = false;
	public static EV3 ev3 = (EV3) BrickFinder.getLocal(); 
	public static Keys keys = ev3.getKeys();
	public static int posActual=0;
	public static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	public static int[][] vacio= {{0, 0, 0}, {0, 0, 0}, { 0, 0, 0}};//9 vacio;
	public static Board puzz = new Board(vacio);
	
	 
	public static void main(String[] args) {
		
		/*
		//Declarando IR Sensor (Control inflarojo)
		EV3IRSensor sensorIR = new EV3IRSensor(SensorPort.S2);
		byte[] commands = new byte[EV3IRSensor.IR_CHANNELS];
		float[] distances = new float[sensorIR.sampleSize()];
		*/
		//Declarando Touch sensor (botton naranja)
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		SensorMode touch = touchSensor.getTouchMode();
		float[] sampleTouch= new float[touch.sampleSize()];
		//touch.fetchSample(sample, 0);
		//sampleTouch[0]==0 //presionado
		
		
		
		
		int key, button;
		
		
		m4.setAcceleration(275);
		m4.setSpeed(200);
		
		m3.setAcceleration(112);
		m3.setSpeed(75);
		
		m2.setAcceleration(450);
		m2.setSpeed(300);
		
		moverRobot("arriba");//subir
		Delay.msDelay(3000);
		m2.rotate(70);//cerrar
		Delay.msDelay(2000);
		moverRobot("abrir");//abrir
		
		while(sampleTouch[0]==0){
			m3.rotate(5, true);
			touch.fetchSample(sampleTouch, 0);
			
		}
		
		m3.rotate(-21, true);//ubicar primer bloque
		Delay.msDelay(2000);
		//
		
		int pos=0;
		posActual=1;
		Graph gx = new Graph();
		List<Integer> actions;
		do{
			System.out.println("EightPuzzle-Menu");
			System.out.println("Up-Leer");
			System.out.println("Down-Imprimir");
			System.out.println("Left-ir a pos");
			System.out.println("Right-resolver");
			System.out.println("Back-Salir");
			key=keys.waitForAnyEvent();
			
			if(key==1){
				puzz.tiles=leerTablero();
			}
			if(key==4){
				imprimirTablero();
			}
			if(key==16){
				System.out.println("---Indique posicion---");
				pos=1;
				
				while(key!=2){
					System.out.println("posicion "+pos);
					key=keys.waitForAnyEvent();
					if(key==16){
						if((pos-1)<1){
							pos=1;
						}else{
							pos--;
						}
					}
					if(key==8){
						if((pos+1)>9){
							pos=9;
						}else{
							pos++;
						}
					}
					
				}
				irPos(pos);
				Delay.msDelay(5000);
				actionLeer(posActual-1, puzz.tiles);
				keys.waitForAnyPress();
			}
			if(key==8){
				if(puzz.isSolvable()){
					gx=puzz.TreeSolution();
					actions=gx.chainActions();
					for(int i=0; i<actions.size(); i++){
						moverTablero(actions.get(i));
						if(keys.waitForAnyEvent(5000)==1){
							System.out.println("<---------->");
							System.out.println("Abortado");
							System.out.println("<---------->");
							keys.waitForAnyPress();
							break;
						}
					}
				}else{
					
					System.out.println("<---------->");
					System.out.println("No es solucionable ");
					System.out.println("<---------->");
					keys.waitForAnyPress();
				}
				
				
			}
		}while(key!=32);
		
		}
		
	public static int range_right = 0;
	public static int range_left = 0;
	private static void irPos(int x){
		moverRobot(posActual-x);
		posActual=x;
		
	}
	//todo movimiento dado del tablero se da apartir de donde esta el vacio
	private static void moverTablero(int movimiento){
		int posVacio=1;
		boolean encontrado=false;
		for(int fila=0; fila<3; fila++){
			for(int columna=0; columna<3; columna++){
				 if(puzz.tiles[fila][columna]==9){
					 encontrado=true;
					 break;
				 }else{
					 posVacio++;
				 }
			}
			if(encontrado){
				 break;
			 }
		}
		System.out.println("pos vacio-"+posVacio);
		
		//Arriba
		if(movimiento==0){
			System.out.println("accion-Arriba");
			irPos(posVacio-3);
			Delay.msDelay(5000);
			moverRobot("tomar");
			Delay.msDelay(5000);
			irPos(posVacio);
			Delay.msDelay(5000);
			moverRobot("soltar");
		}
		//Derecha
		if(movimiento==1){
			System.out.println("accion-Derecha");
			irPos(posVacio+1);
			Delay.msDelay(5000);
			moverRobot("tomar");
			Delay.msDelay(5000);
			irPos(posVacio);
			Delay.msDelay(5000);
			moverRobot("soltar");
			
		}
		//Abajo
		if(movimiento==2){
			System.out.println("accion-Abajo");
			irPos(posVacio+3);
			Delay.msDelay(5000);
			moverRobot("tomar");
			Delay.msDelay(5000);
			irPos(posVacio);
			Delay.msDelay(5000);
			moverRobot("soltar");
		}
		//Izquierda
		if(movimiento==3){
			System.out.println("accion-Izquierda");
			irPos(posVacio-1);
			Delay.msDelay(5000);
			moverRobot("tomar");
			Delay.msDelay(5000);
			irPos(posVacio);
			Delay.msDelay(5000);
			moverRobot("soltar");
			
		}
		puzz.moveBoard(movimiento);
		imprimirTablero();
	}
	private static void imprimirTablero(){
		//System.out.println("<------->");
	    String x="";
	    String x1="";
	    String x2="";
	    //char k = (char)34;
	    //x=x.concat(Character.toString(k));
	    for(int f=0; f<3; f++){
	        //System.out.print("   ");
	        
	        for(int c=0; c<3; c++){
	        	if(f==0){
	        		if(puzz.tiles[f][c]==9){
		                x=x.concat("_");
		            }else{
		                x=x.concat(""+puzz.tiles[f][c]);
		            }
	        	}
	        	if(f==1){
	        		if(puzz.tiles[f][c]==9){
		                x1=x1.concat("_");
		            }else{
		                x1=x1.concat(""+puzz.tiles[f][c]);
		            }
	        	}
	        	if(f==2){
	        		if(puzz.tiles[f][c]==9){
		                x2=x2.concat("_");
		            }else{
		                x2=x2.concat(""+puzz.tiles[f][c]);
		            }
	        	}
	            
	        }
	        
	        //System.out.println("");
	    }
	    //k = (char)34;
	    //x=x.concat(Character.toString(k));
	    //System.out.println(GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
	    //System.out.println(Font.getLargeFont());
	    
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    System.out.println("");
	    
	    g.clear();
	    g.refresh();
	    g.setFont(Font.getLargeFont());
	    g.drawString(x, 55, 10, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
	    g.drawString(x1, 55, 50, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
	    g.drawString(x2, 55, 90, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
	    g.refresh();
	    Delay.msDelay(5000);
		//keys.waitForAnyPress();
		g.clear();
	}
	private static void moverRobot(String sentido, int bloques) {
		if(sentido=="izquierda"){//left
			m3.rotate(-30*bloques, true);
			//range_left -= 20;
		}
		if(sentido=="derecha"){//right
			m3.rotate(30*bloques, true);
			//range_right += 20;
		}
		
	}
	private static void moverRobot(int bloques) {
		m3.rotate(30*bloques, true);
	
	}
	private static void moverRobot(String sentido) {
		if(sentido=="arriba"){//up
			m4.rotate(-240, true);
			//range_left -= 20;
		}
		if(sentido=="arribaIni"){//up
			m4.rotate(-285, true);
			//range_left -= 20;
		}
		if(sentido=="abajo"){//down
			m4.rotate(240, true);
			//range_right += 20;
		}
		if(sentido=="cerrar"){//agarrar
			m2.rotate(70, true);
		}
		if(sentido=="abrir"){//Soltar
			m2.rotate(-70);
		}
		
		if(sentido=="tomar"){//down
			m4.rotate(265, true);//abajo
			Delay.msDelay(5000);
			m2.rotate(70, true);//cerrar
			Delay.msDelay(5000);
			m4.rotate(-265, true);//subir
		}
		if(sentido=="soltar"){//down
			m4.rotate(265, true);//abajo
			Delay.msDelay(5000);
			m2.rotate(-70);//abrir
			Delay.msDelay(5000);
			m4.rotate(-265, true);//subir
		}
		
	}
	private static void actionGarraActual(boolean x) {
		tengoObjeto=x;
		//mover
		moverRobot("abajo");//bajar
		Delay.msDelay(3000);
		
		if(x){
			moverRobot("cerrar");
		}else{
			moverRobot("abrir");
		}
		
		
			//volver a posición
		Delay.msDelay(3000);
		moverRobot("arriba");//subir
	}
	
	private static void actionLeer(int step, int[][] tilestemp) {
		//Declarando Color sensor (sensor de color)
		moverRobot("abajo");//bajar
		Delay.msDelay(5000);
		float[] sampleColor = new float[color.sampleSize()]; 
		color.fetchSample(sampleColor, 0);
		System.out.println("--- Paso"+(step)+"---");
		System.out.println("R-"+sampleColor[0]);
		System.out.println("G-"+sampleColor[1]);
		System.out.println("B-"+sampleColor[2]);
		System.out.println("Reflecting-"+isReflecting(sampleColor));
		if(step<3){
			tilestemp[0][step]=interpretarColor(sampleColor);
		}
		
		if(step>=3 && step<6){
			tilestemp[1][step%3]=interpretarColor(sampleColor);
		}
		if(step>=6 && step<9){
			tilestemp[2][step%3]=interpretarColor(sampleColor);
		}
		//volver a posición
		Delay.msDelay(5000);
		moverRobot("arriba");//subir
	}
	private static int interpretarColor(float[] colorRGB) {
		//caso para negro
		//0,0147																0,0117											0,0088	
		//0,0258																0,0176											0,0147
		if(colorRGB[0]>=(0.006) && colorRGB[0]<=(0.0260) && colorRGB[1]>=(0.0090) && colorRGB[1]<=(0.0180) && colorRGB[2]>=(0.0060) && colorRGB[2]<=(0.0150)){
			System.out.println("Negro-1");
			return 1;
		}
		//caso para Verde	
		//0,0205																0,0539												0,0196	
		//0,0343																0,0921												0,0343
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0190) && colorRGB[0]<=(0.0360) && colorRGB[1]>=(0.0520) && colorRGB[1]<=(0.0930) && colorRGB[2]>=(0.0190) && colorRGB[2]<=(0.0350)){
			System.out.println("Verde-2");
			return 2;
		}
		//caso para Azul
		//0,0196														0,0558												0,0626	
		//0,02941														0,1009												0,1245
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0190) && colorRGB[0]<=(0.0300) && colorRGB[1]>=(0.0540) && colorRGB[1]<=(0.1010) && colorRGB[2]>=(0.0600) && colorRGB[2]<=(0.1250)){
			System.out.println("Azul-3");
			return 3;
		}
		//caso para Blanco
		//0,0872															0,0823											0,0745	
		//0,2068															0,1970											0,1539
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0860) && colorRGB[0]<=(0.2100) && colorRGB[1]>=(0.0810) && colorRGB[1]<=(0.2000) && colorRGB[2]>=(0.0730) && colorRGB[2]<=(0.1555)){
			System.out.println("Blanco-4");
			return 4;
		}
		//caso para cafe
		//0,045															    0,0294												0,0147	
		//0,0953															0,0550												0,0314
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.040) && colorRGB[0]<=(0.0960) && colorRGB[1]>=(0.0200) && colorRGB[1]<=(0.0530) && colorRGB[2]>=(0.0100) && colorRGB[2]<=(0.0320)){
			System.out.println("Cafe-5");
			return 5;
		}
		//caso para Amarillo
		//0,0980																0,0774												0,0166	
		//0,2333																0,1666												0,0343
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0980) && colorRGB[0]<=(0.2340) && colorRGB[1]>=(0.0750) && colorRGB[1]<=(0.1670) && colorRGB[2]>=(0.0150) && colorRGB[2]<=(0.0350)){
			System.out.println("Amarillo-6");
			return 6;
		}
		//caso para Magenta
		//0,1174																0,0548													0,0627	
		//0,1911																0,0784													0,0911
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.1260) && colorRGB[0]<=(0.2000) && colorRGB[1]>=(0.0540) && colorRGB[1]<=(0.0810) && colorRGB[2]>=(0.0500) && colorRGB[2]<=(0.0930)){
			System.out.println("Magenta-7");
			return 7;
		}
		//caso para Rojo
		//0,1156															0,0284											0,0186	
		//0,2696															0,0529											0,0294
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.1140) && colorRGB[0]<=(0.2710) && colorRGB[1]>=(0.0270) && colorRGB[1]<=(0.0540) && colorRGB[2]>=(0.0180) && colorRGB[2]<=(0.0310)){
			System.out.println("Rojo-8");
			return 8;
		}
		//caso para vacio
		if(!isReflecting(colorRGB)){
			System.out.println("Vacio-9");
			return 9;
		}
		return 9;
		
	}
	private static boolean isReflecting(float[] colorSample) {
		return colorSample[0]>0.015||colorSample[1]>0.015||colorSample[2]>0.015;
	}
	private static int[][] leerTablero(){
		int[][] tilestemp= {{0, 0, 0}, {0, 0, 0}, { 0, 0, 0}};//9 vacio;
		for(int i=0; i<9; i++){
			if(keys.waitForAnyEvent(5000)==1){
				break;
			}
			irPos(i+1);
			if(keys.waitForAnyEvent(5000)==1){
				break;
			}
			actionLeer((i), tilestemp);
			
			
		}
		
		return tilestemp;
	}
	
	
	
}
