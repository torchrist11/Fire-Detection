import java.io.IOException;
import java.io.OutputStream;
import com.virtenio.driver.usart.NativeUSART;
import com.virtenio.driver.usart.USART;
import com.virtenio.driver.usart.USARTException;
import com.virtenio.driver.usart.USARTParams;
import com.virtenio.io.Console;
import com.virtenio.preon32.examples.common.USARTConstants;import com.virtenio.radio.ieee_802_15_4.Frame;
import com.virtenio.driver.device.at86rf231.*;
import com.virtenio.preon32.examples.common.RadioInit;


public class BaseStation {
	private int COMMON_CHANNEL = 24;
	private int COMMON_PANID = 0xCAFE;
	private int[] addresses = new int[] {
			0xDACE, 0xAAAF,0xAADC, 0xCACF, 0xBCAF, 0xDACE
	};
	private int ADDR_NODE1 = addresses[0];
	static USART usart;
	private static OutputStream out;
	static BaseStation bs = new BaseStation();
	static int pilihan;
//	public String[] temp =new String[1];
//	public String akhir;

//	static Console c = new Console();
	
	
	private static USART configUSART() throws Exception{
		int instanceID=0;
		USARTParams params = USARTConstants.PARAMS_115200;
		NativeUSART usart = NativeUSART.getInstance(instanceID);
		try {
			usart.close();
			usart.open(params);
			return usart;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public static void useUSART() throws Exception{
		usart = configUSART();
	}
	
	public static void Run() throws Exception{
		//System.out.println("Start");
		
		do {
			pilihan=BaseStation.usart.read();
//			pilihan =  c.readInt("ASDF");
			switch(pilihan) {
				case 1:{
					//System.out.print("Salah sini");
					bs.Receive();
					break;
				}
			}
		}while(true);
	}
	
	public void Receive() throws Exception {
		//final Shuttle shuttle = Shuttle.getInstance();
		final AT86RF231 radio = RadioInit.initRadio();
		radio.setChannel(COMMON_CHANNEL);
		radio.setPANId(COMMON_PANID);
		radio.setShortAddress(ADDR_NODE1);
		
		Thread reader = new Thread() {
			@Override
			public void run() {
				//System.out.println("Start");
				while(true) {
					Frame f = null;
					try {
						f = new Frame();
						radio.setState(AT86RF231.STATE_RX_AACK_ON);
						radio.waitForFrame(f);
					}
					catch(Exception e) {
						//System.out.print("a");
					}
					try {
						if(f!=null) {
							byte[] dg = f.getPayload();
							String str = new String(dg, 0, dg.length);
							String hex_addr = Integer.toHexString((int) f.getSrcAddr());
							String hasil = "#(" + hex_addr + ");" + str+"#";
//							temp[0] = hasil;
							out.write(hasil.getBytes(), 0, hasil.length());
							usart.flush();
							//System.out.println(hasil);
							Thread.sleep(1);
						}
					} catch (Exception e) {
							//System.out.print("no input");
						}
				}
			}
		};
		reader.start();
	}
	
	public void write() throws USARTException {
		
		switch(pilihan) {
			case 1:{
//				akhir = temp[0];
				try {
					//System.out.println(akhir);
//					out.write(akhir.getBytes(), 0, akhir.length());
				} catch (Exception e) {
					//System.out.print("b");
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		//System.out.println("ASF");
		try {
			useUSART();
			out = usart.getOutputStream();
		}
		catch(Exception e) {
			
		}
		Run();
		//System.out.println("sASF");
	}
}
