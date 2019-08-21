// *
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
//import com.virtenio.driver.device.ADT7410;
//import com.virtenio.driver.device.MPL115A2;
//import com.virtenio.driver.i2c.I2C;
//import com.virtenio.driver.i2c.NativeI2C;
//import com.virtenio.driver.device.SHT21;
//import com.virtenio.driver.gpio.GPIO;
//import com.virtenio.driver.gpio.NativeGPIO;

///**
// * Test den Zugriff auf den Temperatursensor ADT7410 von Analog über I2C.
// * <p/>
// * <b> Datenblatt des Temperatursensors: </b> <a href=
// * "http://www.analog.com/static/imported-files/data_sheets/ADT7410.pdf"
// * target="_blank">
// * http://www.analog.com/static/imported-files/data_sheets/ADT7410.pdf</a>
// * (Stand: 29.03.2011)
// */
//public class Initiator {
//	private NativeI2C i2c;
//	private ADT7410 temperatureSensor;
//	private SHT21 sht21;
//	private MPL115A2 pressureSensor;
//
//	public void init() throws Exception {
//		//System.out.println("I2C(Init)");
//		i2c = NativeI2C.getInstance(1);
//		i2c.open(I2C.DATA_RATE_400);
//
//		//System.out.println("ADT7410(Init)" + ";" + " SHT21(Init)" + ";" + " MPL115A2(Init)");
//		temperatureSensor = new ADT7410(i2c, ADT7410.ADDR_0, null, null);
//		temperatureSensor.open();
//		temperatureSensor.setMode(ADT7410.CONFIG_MODE_CONTINUOUS);
//		
//		sht21 = new SHT21(i2c);
//		sht21.open();
//		sht21.setResolution(SHT21.RESOLUTION_RH12_T14);
//		sht21.reset();
//	
//		GPIO resetPin = NativeGPIO.getInstance(24);
//		GPIO shutDownPin = NativeGPIO.getInstance(12);
//		pressureSensor = new MPL115A2(i2c, resetPin, shutDownPin);
//		pressureSensor.open();
//		pressureSensor.setReset(false);
//		pressureSensor.setShutdown(false);
//
//		//System.out.println("Done(Init)");
//		System.out.println("Start");
//	}
//
////	public String run() throws Exception {
////		init();
////		while (true) {
////			try {
////				//System.out.print(now.getTime());
////				//int raw = temperatureSensor.getTemperatureRaw();
////				float celsius = temperatureSensor.getTemperatureCelsius();
////				tempTemp[i]=celsius;
////				System.out.print("Temperature: " + celsius + " [°C] ");
////				//System.out.print("Temperature: raw=" + raw + "; " + celsius + " [°C] ");
////				
////				// humidity conversion
////				sht21.startRelativeHumidityConversion();
////				Thread.sleep(100);
////				int rawRH = sht21.getRelativeHumidityRaw();
////				float rh = SHT21.convertRawRHToRHw(rawRH);
////				tempHum[i]=rh;
////				System.out.print("Humidity: " + rh + " [Rh] ");
////				//System.out.println("Humidity: rawRH=" + rawRH + ", RH=" + rh + " [Rh]");
////				
////				pressureSensor.startBothConversion();
////				Thread.sleep(MPL115A2.BOTH_CONVERSION_TIME);
////				int pressurePr = pressureSensor.getPressureRaw();
////				int tempRaw = pressureSensor.getTemperatureRaw();
////				float pressure = pressureSensor.compensate(pressurePr, tempRaw);
////				System.out.println("Pressure: " + pressure);
////				Thread.sleep(1000 - MPL115A2.BOTH_CONVERSION_TIME);
////				
////				Thread.sleep(500);
////			} catch (Exception e) {
////				System.out.println("error");
////			}
////		}
////	}
//
////	public static void main(String[] args) throws Exception {
////		new coba().run();
////	}
//}