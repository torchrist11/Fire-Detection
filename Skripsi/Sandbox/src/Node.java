
//import com.virtenio.preon32.shuttle.Shuttle;
//import com.virtenio.driver.led.LED;
import com.virtenio.preon32.examples.common.RadioInit;
import com.virtenio.radio.ieee_802_15_4.Frame;
import com.virtenio.vm.Time;
//import java.util.Calendar;
import com.virtenio.driver.device.ADT7410;
import com.virtenio.driver.device.MPL115A2;
import com.virtenio.driver.device.SHT21;
import com.virtenio.driver.device.at86rf231.*;
import com.virtenio.driver.gpio.GPIO;
import com.virtenio.driver.gpio.NativeGPIO;
import com.virtenio.driver.i2c.I2C;
import com.virtenio.driver.i2c.NativeI2C;

public class Node {
	private int[] addresses = new int[] { 0xBAFE, 0xAAAF, 0xAADC, 0xCACF, 0xBCAF, 0xDACE };
	private int COMMON_CHANNEL = 24;
	private int COMMON_PANID = 0xCAFE;
	private int ADDR_NODE1 = addresses[0];
	private int ADDR_NODE2 = addresses[3];;
	private NativeI2C i2c;
	private ADT7410 temperatureSensor;
	private SHT21 humiditySensor;
	private MPL115A2 pressureSensor;
	String msg = "";

	public void Sense() throws Exception {
		// final Shuttle shuttle = Shuttle.getInstance();

		final stringFormatTime sf = new stringFormatTime();
//		Calendar cal = Calendar.getInstance();

//		System.out.println("Done(Init)");
//		System.out.println("Start");

		final AT86RF231 radio = RadioInit.initRadio();
		radio.setChannel(COMMON_CHANNEL);
		radio.setPANId(COMMON_PANID);
		radio.setShortAddress(ADDR_NODE2);
		Thread th = new Thread() {
			public void run() {
				try {
					i2c = NativeI2C.getInstance(1);
					i2c.open(I2C.DATA_RATE_400);
					// System.out.println("ADT7410(Init)" + ";" + " SHT21(Init)" + ";" + "
					// MPL115A2(Init)");
					temperatureSensor = new ADT7410(i2c, ADT7410.ADDR_0, null, null);
					temperatureSensor.open();
					temperatureSensor.setMode(ADT7410.CONFIG_MODE_CONTINUOUS);

					humiditySensor = new SHT21(i2c);
					humiditySensor.open();
					humiditySensor.setResolution(SHT21.RESOLUTION_RH12_T14);
					humiditySensor.reset();

					GPIO resetPin = NativeGPIO.getInstance(24);
					GPIO shutDownPin = NativeGPIO.getInstance(12);
					pressureSensor = new MPL115A2(i2c, resetPin, shutDownPin);
					pressureSensor.open();
					pressureSensor.setReset(false);
					pressureSensor.setShutdown(false);

//					while (true) {
//						Frame f = null;
//						try {
//							f = new Frame();
//							radio.setState(AT86RF231.STATE_RX_AACK_ON);
//							radio.waitForFrame(f);
//						} catch (Exception e) {
//
//						}
//						if (f != null) {
//							byte[] x = f.getPayload();
//							String y = new String(x, 0, x.length);
//							long currTimeNow = Long.parseLong(y);
//							Time.setCurrentTimeMillis(currTimeNow);
//							// System.out.println(hasil);
//							Thread.sleep(10);
//						}
//					}
//
				} catch (Exception e) {
				}
				
				int counter = 1;
				while (true) {
					try {
						long currTime = Time.currentTimeMillis();
						String waktu = sf.SFTime(currTime);
						msg += counter + ";" + waktu + ";";

						float celsius = temperatureSensor.getTemperatureCelsius();
						msg += celsius + ";";
						// System.out.print("Temperature: " + celsius + " [°C] ");

						humiditySensor.startRelativeHumidityConversion();
						Thread.sleep(100);
						int rawRH = humiditySensor.getRelativeHumidityRaw();
						float rh = SHT21.convertRawRHToRHw(rawRH);
						msg += rh + ";";
						// System.out.print("Humidity: " + rh + " [Rh] ");

						pressureSensor.startBothConversion();
//						Thread.sleep(MPL115A2.BOTH_CONVERSION_TIME);
						Thread.sleep(100);
						int pressurePr = pressureSensor.getPressureRaw();
						int tempRaw = pressureSensor.getTemperatureRaw();
						float pressure = pressureSensor.compensate(pressurePr, tempRaw);
//						Thread.sleep(1000 - MPL115A2.BOTH_CONVERSION_TIME);
						msg += pressure;
						String message = msg;
						Frame frame = new Frame(Frame.TYPE_DATA | Frame.ACK_REQUEST | Frame.DST_ADDR_16 | Frame.INTRA_PAN
								| Frame.SRC_ADDR_16);
						frame.setSrcAddr(ADDR_NODE2);
						frame.setSrcPanId(COMMON_PANID);
						frame.setDestAddr(ADDR_NODE1);
						frame.setDestPanId(COMMON_PANID);
						radio.setState(AT86RF231.STATE_TX_ARET_ON);
						frame.setPayload(message.getBytes()); // ngasih paket ke frame
						radio.transmitFrame(frame);
						// System.out.println("(" + i + ") : " + msg);
						msg = "";
						counter++;
					}
					catch(Exception e) {
						
					}
			}
		}
		};
		th.start();
	}

	public static void main(String[] args) throws Exception {
		new Node().Sense();
	}
}