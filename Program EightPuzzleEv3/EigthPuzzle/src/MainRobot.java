//library of java
import java.util.List;

//library of LEGO lejos
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.hardware.lcd.Font;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class MainRobot {
	//motors (control about robot movements)
	//Movement Y
	public static RegulatedMotor m4 = new EV3LargeRegulatedMotor(MotorPort.D);
	//Movement X (Rotate)
	public static RegulatedMotor m3 = new EV3LargeRegulatedMotor(MotorPort.C);
	//claw
	public static RegulatedMotor m2 = new EV3MediumRegulatedMotor(MotorPort.B);
	
	//Sensors (way that robot can read and set physical puzzle on memory)
	public static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
	public static SensorMode color = colorSensor.getRGBMode();
	
	//Instance of Brick (unit control of robot)
	public static EV3 ev3 = (EV3) BrickFinder.getLocal();
	//Instance buttons from brick
	public static Keys keys = ev3.getKeys();
	//Instance Display
	public static GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	
	//control values
	public static boolean tengoObjeto = false;
	public static int posActual=0;
	public static int[][] vacio= {{0, 0, 0}, {0, 0, 0}, { 0, 0, 0}};//Clean board
	//initial puzzle object
	public static Board puzz = new Board(vacio);
	
	 
	public static void main(String[] args) {
		//Button sensor (limit about negative rotate X)
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		SensorMode touch = touchSensor.getTouchMode();
		float[] sampleTouch= new float[touch.sampleSize()];

		int key;
		//set max speed and acceleration (interval speed change in time)
		m4.setAcceleration(275);
		m4.setSpeed(200);
		
		m3.setAcceleration(112);
		m3.setSpeed(75);
		//claw
		m2.setAcceleration(450);
		m2.setSpeed(300);
		
		//set robot in initial position (start in position that can take items)
		moverRobot("arriba");//a good distance up
		Delay.msDelay(3000);
		//set claw in initial position
		m2.rotate(70);//close claw
		Delay.msDelay(2000);
		moverRobot("abrir");//open claw
		
		//move right until touchsensor is pressed (limit in positive X)
		while(sampleTouch[0]==0){
			m3.rotate(5, true);
			touch.fetchSample(sampleTouch, 0);
		}
		//first cell of board-puzzle (when touch button limit positive X)
		m3.rotate(-21, true);
		Delay.msDelay(2000);
		
		//values initial state puzzle
		int pos=0;
		posActual=1;//cell position
		Graph gx = new Graph();
		List<Integer> actions;
		do{
			//print Menu
			System.out.println("EightPuzzle-Menu");
			System.out.println("Up-Leer");
			System.out.println("Down-Imprimir");
			System.out.println("Left-ir a pos");
			System.out.println("Right-resolver");
			System.out.println("Back-Salir");
			key=keys.waitForAnyEvent();
			
			if(key==1){//Button Up
				puzz.tiles=leerTablero();//read all board
			}
			if(key==4){//Button Down
				imprimirTablero();//print current board (memory)
			}
			if(key==16){//Button Left
				System.out.println("---Indique posicion---");
				pos=1;
				
				while(key!=2){//waiting for receive position (end with middle button)
					System.out.println("posicion "+pos);
					key=keys.waitForAnyEvent();
					if(key==16){//Button Left
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
				irPos(pos);//go to position
				Delay.msDelay(5000);
				actionLeer(posActual-1, puzz.tiles);//read current position (postActual-1=step position)
				keys.waitForAnyPress();//back to menu when any button is pressed
			}
			if(key==8){//Button Right
				if(puzz.isSolvable()){//check possible solver
					gx=puzz.TreeSolution();//build solution tree
					actions=gx.chainActions();//take actions to solve
					for(int i=0; i<actions.size(); i++){//do actions
						moverTablero(actions.get(i));
						if(keys.waitForAnyEvent(5000)==1){//any button pressed for stop
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
					keys.waitForAnyPress();//back to menu when any key is pressed
				}
				
				
			}
		}while(key!=32);//Button Back to end program
		
		}
		
	/*
	 * Board positions
	 * <pre>
     *  123
     *  456
     *  789
     * </pre> 
	 */
	private static void irPos(int x){//move robot in position 1 to 9
		/*
		 * number of moves should be do for go to position
		 * Negative value is right movement (see matrix how vector 123456789 if go 5 to 9
		 * input for moverRobot is -4)
		 * Positive value is Left movement see matrix how vector 123456789 if go 7 to 2
		 * input for moverRobot is 5)
		 */
		moverRobot(posActual-x);
		posActual=x;
		
	}
	//move board with value movement reference point is empty cell (value 9, this cell moves)
	private static void moverTablero(int movimiento){
		int posVacio=1;
		boolean encontrado=false;
		for(int fila=0; fila<3; fila++){//try to find empty cell (value 9)
			for(int columna=0; columna<3; columna++){
				 if(puzz.tiles[fila][columna]==9){
					 encontrado=true;
					 break;
				 }else{
					 posVacio++;
				 }
			}
			if(encontrado){//end loop when empty cell is found
				 break;
			 }
		}
		System.out.println("pos vacio-"+posVacio);
		
		//Case Move up
		/*
		 * <pre>
         *  ###
         *  X&&
         *  *##
         * </pre>
		 * up is 3 left movement (if see matrix how vector ###X&&*## 
		 * where && moves, X pos objetive, * start position (empty cell) 
		 */
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
		//Case Move Right
		/*
		 * <pre>
         *  ###
         *  *X#
         *  ###
         * </pre>
		 * right is 1 right movement (if see matrix how vector ###*X#### 
		 * where X pos objetive, * start position (empty cell) 
		 */
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
		//Case Move Down
		/*
		 * <pre>
         *  ##*
         *  &&X
         *  ###
         * </pre>
		 * down is 3 right movement (if see matrix how vector ##*&&X### 
		 * where & moves, X pos objetive, * start position (empty cell) 
		 */
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
		//Case Move Left
		/*
		 * <pre>
         *  ###
         *  X*#
         *  ###
         * </pre>
		 * Left is 1 movement left (if see matrix how vector ###X*#### 
		 * where X pos objetive, * start position (empty cell) 
		 */
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
		puzz.moveBoard(movimiento);//Update in memory the board when end movement
		imprimirTablero();//print updated board
	}
	private static void imprimirTablero(){
	    String x="";//row 0
	    String x1="";//row 1
	    String x2="";//row 2
	    for(int f=0; f<3; f++){
	        
	        for(int c=0; c<3; c++){
	        	if(f==0){//case row 0
	        		if(puzz.tiles[f][c]==9){
		                x=x.concat("_");
		            }else{
		                x=x.concat(""+puzz.tiles[f][c]);
		            }
	        	}
	        	if(f==1){//case row 1
	        		if(puzz.tiles[f][c]==9){
		                x1=x1.concat("_");
		            }else{
		                x1=x1.concat(""+puzz.tiles[f][c]);
		            }
	        	}
	        	if(f==2){//case row 2
	        		if(puzz.tiles[f][c]==9){
		                x2=x2.concat("_");
		            }else{
		                x2=x2.concat(""+puzz.tiles[f][c]);
		            }
	        	}
	            
	        }
	        
	        
	    }
	    
	    //clean Brick Display
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
		//keys.waitForAnyPress();//wait when end movement until button is pressed
		g.clear();
	}
	/*
	 * Rotate robot (Movement X) with number of cell's (bloques) that move 
	 * positive value is Right move, negative value is Left
	 */
	private static void moverRobot(int bloques) {
		m3.rotate(30*bloques, true);
	}
	
	private static void moverRobot(String sentido) {
		if(sentido=="arriba"){//up
			m4.rotate(-265, true);
		}
		if(sentido=="abajo"){//down
			m4.rotate(265, true);
		}
		if(sentido=="cerrar"){//close claw
			m2.rotate(70, true);
		}
		if(sentido=="abrir"){//open claw
			m2.rotate(-70);
		}
		
		if(sentido=="tomar"){//take cell
			moverRobot("abajo");
			Delay.msDelay(5000);
			moverRobot("cerrar");
			Delay.msDelay(5000);
			moverRobot("arriba");
		}
		if(sentido=="soltar"){//drop cell
			moverRobot("abajo");
			Delay.msDelay(5000);
			moverRobot("abrir");
			Delay.msDelay(5000);
			moverRobot("arriba");
		}
	}
	/*
	 * step positions
	 * <pre>
     *  012
     *  345
     *  678
     * </pre> 
	 * Read color and replace value of tilestemp in step position see how vector 012345678
	 */
	private static void actionLeer(int step, int[][] tilestemp){
		moverRobot("abajo");//down
		Delay.msDelay(5000);
		float[] sampleColor = new float[color.sampleSize()]; 
		color.fetchSample(sampleColor, 0);
		System.out.println("--- Paso"+(step)+"---");
		System.out.println("R-"+sampleColor[0]);
		System.out.println("G-"+sampleColor[1]);
		System.out.println("B-"+sampleColor[2]);
		System.out.println("Reflecting-"+isReflecting(sampleColor));
		if(step<3){//in row 0
			tilestemp[0][step]=interpretarColor(sampleColor);
		}
		
		if(step>=3 && step<6){//in row 1
			tilestemp[1][step%3]=interpretarColor(sampleColor);
		}
		if(step>=6 && step<9){//in row 2
			tilestemp[2][step%3]=interpretarColor(sampleColor);
		}
		//back to up position
		Delay.msDelay(5000);
		moverRobot("arriba");//subir
	}
	/*
	 * Color decoder (range in RGB values)
	 * every time that robot is start need calibration values (test read)
	 * because values depends of environment (illumination), position of cell, 
	 * cell material (reflective) 
	 * 
	 */
	private static int interpretarColor(float[] colorRGB) {
		/*
		 * Case Black
		 * 0.0260 >= R >= 0.0060
		 * 0.0180 >= G >= 0.0090
		 * 0.0060 >= B >= 0.0150
		 * isReflecting = True or False
		 */
		if(colorRGB[0]>=(0.0060) && colorRGB[0]<=(0.0260) && colorRGB[1]>=(0.0090) && colorRGB[1]<=(0.0180) && colorRGB[2]>=(0.0060) && colorRGB[2]<=(0.0150)){
			System.out.println("Negro-1");
			return 1;
		}
		/*
		 * Case Green
		 * 0.0190 >= R >= 0.0360
		 * 0.0520 >= G >= 0.0930
		 * 0.0190 >= B >= 0.0350
		 * isReflecting = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0190) && colorRGB[0]<=(0.0360) && colorRGB[1]>=(0.0520) && colorRGB[1]<=(0.0930) && colorRGB[2]>=(0.0190) && colorRGB[2]<=(0.0350)){
			System.out.println("Verde-2");
			return 2;
		}
		/*
		 * Case Blue
		 * 0.0300 >= R >= 0.0190
		 * 0.1010 >= G >= 0.0540
		 * 0.1250 >= B >= 0.0600
		 * isReflectiong = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0190) && colorRGB[0]<=(0.0300) && colorRGB[1]>=(0.0540) && colorRGB[1]<=(0.1010) && colorRGB[2]>=(0.0600) && colorRGB[2]<=(0.1250)){
			System.out.println("Azul-3");
			return 3;
		}
		/*
		 * Case White
		 * 0.2100 >= R >= 0.0860
		 * 0.2000 >= G >= 0.0810
		 * 0.1555 >= B >= 0.0730
		 * isReflecting = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0860) && colorRGB[0]<=(0.2100) && colorRGB[1]>=(0.0810) && colorRGB[1]<=(0.2000) && colorRGB[2]>=(0.0730) && colorRGB[2]<=(0.1555)){
			System.out.println("Blanco-4");
			return 4;
		}
		/*
		 * Case Brown
		 * 0.0960 >= R >= 0.0400
		 * 0.0530 >= G >= 0.0200
		 * 0.0320 >= B >= 0.0100
		 * isReflecting = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0400) && colorRGB[0]<=(0.0960) && colorRGB[1]>=(0.0200) && colorRGB[1]<=(0.0530) && colorRGB[2]>=(0.0100) && colorRGB[2]<=(0.0320)){
			System.out.println("Cafe-5");
			return 5;
		}
		/*
		 * Case Yellow
		 * 0.2340 >= R >= 0.0980
		 * 0.1670 >= G >= 0.0750
		 * 0.0350 >= B >= 0.0150
		 * isReflecting = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.0980) && colorRGB[0]<=(0.2340) && colorRGB[1]>=(0.0750) && colorRGB[1]<=(0.1670) && colorRGB[2]>=(0.0150) && colorRGB[2]<=(0.0350)){
			System.out.println("Amarillo-6");
			return 6;
		}
		/*
		 * Case Magenta
		 * 0.2000 >= R >= 0.1260
		 * 0.0810 >= G >= 0.0540
		 * 0.0930 >= B >= 0.0500
		 * isReflecting = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.1260) && colorRGB[0]<=(0.2000) && colorRGB[1]>=(0.0540) && colorRGB[1]<=(0.0810) && colorRGB[2]>=(0.0500) && colorRGB[2]<=(0.0930)){
			System.out.println("Magenta-7");
			return 7;
		}
		/*
		 * Case Red
		 * 0.2710 >= R >= 0.1140
		 * 0.0540 >= G >= 0.0270
		 * 0.0310 >= B >= 0.0180
		 * isReflecting = True
		 */
		if(isReflecting(colorRGB) && colorRGB[0]>=(0.1140) && colorRGB[0]<=(0.2710) && colorRGB[1]>=(0.0270) && colorRGB[1]<=(0.0540) && colorRGB[2]>=(0.0180) && colorRGB[2]<=(0.0310)){
			System.out.println("Rojo-8");
			return 8;
		}
		/*
		 * Case Empty (cell None)
		 * isReflecting = False
		 */
		if(!isReflecting(colorRGB)){
			System.out.println("Vacio-9");
			return 9;
		}
		return 9;//Others take empty value (out range)
	}

	//Reflecting report if read some item or empty (value R or G or B >0.015)
	private static boolean isReflecting(float[] sampleColor) {
		return sampleColor[0]>0.015||sampleColor[1]>0.015||sampleColor[2]>0.015;
	}
	/*
     * read all board and return
	 */
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
