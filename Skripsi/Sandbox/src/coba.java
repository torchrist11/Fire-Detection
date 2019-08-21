///*
// * Copyright (c) 2011., Virtenio GmbH
// * All rights reserved.
// *
// * Commercial software license.
// * Only for test and evaluation purposes.
// * Use in commercial products prohibited.
// * No distribution without permission by Virtenio.
// * Ask Virtenio for other type of license at info@virtenio.de
// *
// * Kommerzielle Softwarelizenz.
// * Nur zum Test und Evaluierung zu verwenden.
// * Der Einsatz in kommerziellen Produkten ist verboten.
// * Ein Vertrieb oder eine Veröffentlichung in jeglicher Form ist nicht ohne Zustimmung von Virtenio erlaubt.
// * Für andere Formen der Lizenz nehmen Sie bitte Kontakt mit info@virtenio.de auf.
// */
//
//
//
////import java.text.DateFormat;
////import java.text.SimpleDateFormat;
////import java.util.Date;
//
//import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;
//import com.virtenio.driver.device.ADT7410;
//import com.virtenio.driver.i2c.I2C;
//import com.virtenio.driver.i2c.NativeI2C;
//import com.virtenio.driver.device.SHT21;
//
///**
// * Test den Zugriff auf den Temperatursensor ADT7410 von Analog über I2C.
// * <p/>
// * <b> Datenblatt des Temperatursensors: </b> <a href=
// * "http://www.analog.com/static/imported-files/data_sheets/ADT7410.pdf"
// * target="_blank">
// * http://www.analog.com/static/imported-files/data_sheets/ADT7410.pdf</a>
// * (Stand: 29.03.2011)
// */
//public class coba {
//	private NativeI2C i2c;
//	private ADT7410 temperatureSensor;
//	private SHT21 sht21;
//	private float[] tempTemp;
//	private float[] tempHum;
//	int i=0;
//	ArrayList<String> hasilArray = new ArrayList<String>();
//	float hasilTemp=0;
//	float hasilHum=0;
//
//	private void init() throws Exception {
//		System.out.println("I2C(Init)");
//		i2c = NativeI2C.getInstance(1);
//		i2c.open(I2C.DATA_RATE_400);
//		tempTemp = new float[100];
//		tempHum = new float [100];
//
//		System.out.println("ADT7410(Init)" + ";" + "SHT21(Init)");
//		temperatureSensor = new ADT7410(i2c, ADT7410.ADDR_0, null, null);
//		temperatureSensor.open();
//		temperatureSensor.setMode(ADT7410.CONFIG_MODE_CONTINUOUS);
//		
//		sht21 = new SHT21(i2c);
//		sht21.open();
//		sht21.setResolution(SHT21.RESOLUTION_RH12_T14);
//		sht21.reset();
//
//		System.out.println("Done(Init)");
//	}
//
//	public String run() throws Exception {
//		init();
//		while (true) {
//			try {
//				if(i>99) {
//					i=0;
//				}
//				long timeMillis = System.currentTimeMillis();
//				long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
//				int day = (int) TimeUnit.SECONDS.toDays(seconds);        
//				long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
//				long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
//				long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
//				System.out.print(hours+":"+minute+":"+second);
//				
//				System.out.print(" Node 1: ");
//				//System.out.print(now.getTime());
//				//int raw = temperatureSensor.getTemperatureRaw();
//				float celsius = temperatureSensor.getTemperatureCelsius();
//				tempTemp[i]=celsius;
//				System.out.print("Temperature: " + celsius + " [°C] ");
//				//System.out.print("Temperature: raw=" + raw + "; " + celsius + " [°C] ");
//				
//				// humidity conversion
//				sht21.startRelativeHumidityConversion();
//				Thread.sleep(100);
//				int rawRH = sht21.getRelativeHumidityRaw();
//				float rh = SHT21.convertRawRHToRHw(rawRH);
//				tempHum[i]=rh;
//				System.out.println("Humidity: " + rh + " [Rh]");
//				//System.out.println("Humidity: rawRH=" + rawRH + ", RH=" + rh + " [Rh]");
//				
//				if(i==tempTemp.length-1 && i==tempHum.length-1) {
//					for(int j=0;j<tempTemp.length && j<tempHum.length;j++) {
//						hasilTemp+=tempTemp[j];
//						hasilHum+=tempHum[j];
//					}
//					hasilTemp=hasilTemp/tempTemp.length;
//					hasilHum=hasilHum/tempHum.length;
//					String x=hasilTemp + " " + hasilHum;
//					hasilArray.add(x);
//					//System.out.println("\n\n\n\n INI HASILLL: " + hasilArray);
//					for(String str:hasilArray)  {
//				        System.out.println(str);  
//				     }
//				}
//				
//				i++;
//				Thread.sleep(500);
//			} catch (Exception e) {
//				System.out.println("error");
//			}
//		}
//	}
//	
//	public String run1() throws Exception {
//		init();
//		while (true) {
//			try {
//				long timeMillis = System.currentTimeMillis();
//				long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
//				int day = (int) TimeUnit.SECONDS.toDays(seconds);        
//				long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
//				long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
//				long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
//				System.out.print(hours+":"+minute+":"+second);
//				
//				System.out.print(" Node 1: ");
//				//System.out.print(now.getTime());
//				//int raw = temperatureSensor.getTemperatureRaw();
//				float celsius = temperatureSensor.getTemperatureCelsius();
//				System.out.println("Temperature: " + celsius + " [°C] ");
//				//System.out.print("Temperature: raw=" + raw + "; " + celsius + " [°C] ");
//				
//				Thread.sleep(500);
//			} catch (Exception e) {
//				System.out.println("contoh error");
//			}
//		}
//	}
//	
//	public String run2() throws Exception {
//		init();
//		while (true) {
//			try {
//				long timeMillis = System.currentTimeMillis();
//				long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
//				int day = (int) TimeUnit.SECONDS.toDays(seconds);        
//				long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
//				long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
//				long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
//				System.out.print(hours+":"+minute+":"+second);
//				
//				System.out.print(" Node 1: ");
//				// humidity conversion
//				sht21.startRelativeHumidityConversion();
//				Thread.sleep(100);
//				int rawRH = sht21.getRelativeHumidityRaw();
//				float rh = SHT21.convertRawRHToRHw(rawRH);
//				System.out.println("Humidity: " + rh + " [Rh]");
//				//System.out.println("Humidity: rawRH=" + rawRH + ", RH=" + rh + " [Rh]");
//				
//				Thread.sleep(500);
//			} catch (Exception e) {
//				System.out.println("contoh error");
//			}
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		new coba().run();
//	}
//}