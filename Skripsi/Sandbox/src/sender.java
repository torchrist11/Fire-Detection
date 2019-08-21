//import com.virtenio.preon32.examples.common.RadioInit;
//import com.virtenio.preon32.shuttle.Shuttle;
//import com.virtenio.radio.ieee_802_15_4.Frame;
//import com.virtenio.driver.device.at86rf231.*;
//import com.virtenio.driver.led.LED;
//import com.virtenio.io.Console;
//
//public class sender {
//
//	private int broadcast = 0xFFFF;
//	private int COMMON_CHANNEL = 24;
//	private int COMMON_PANID = 0xCAFE;
//	private int ADDR_NODE1 = 0xAFFE; //sender
//	private int ADDR_NODE2 = 0xBABE; //receiver
//	coba hasil=new coba();
//	
//	public void pSender() throws Exception{
//		//final Shuttle shuttle = Shuttle.getInstance();
//		
//		final AT86RF231 radio = RadioInit.initRadio();
//		radio.setChannel(COMMON_CHANNEL);
//		radio.setPANId(COMMON_PANID);
//		radio.setShortAddress(ADDR_NODE1);
//		
//		Console console = new Console();
//		
//		int i = 1;
//		while(true) {
//			String msg="";
//			//String msg = console.readLine("Please ENTER your message");
//			String awal = console.readLine("Enter 1. for Temperature 2. for Humidity 3. for All");
//			if(awal.equalsIgnoreCase("1")) {
//				msg=hasil.run1();
//			}
//			else if(awal.equalsIgnoreCase("2")) {
//				msg=hasil.run2();
//			}
//			else {
//				msg=hasil.run();
//			}
//			//String msg = hasil.run();
//			boolean isOK = false;
//			while(!isOK) {
//				try {
//					String message = i + "=" + msg;
//					Frame frame = new Frame(Frame.TYPE_DATA | Frame.ACK_REQUEST
//							| Frame.DST_ADDR_16 | Frame.INTRA_PAN | Frame.SRC_ADDR_16);
//					frame.setSrcAddr(ADDR_NODE1);
//					frame.setSrcPanId(COMMON_PANID);
//					frame.setDestAddr(ADDR_NODE2);
//					frame.setDestPanId(COMMON_PANID);
//					radio.setState(AT86RF231.STATE_TX_ARET_ON);
//					frame.setPayload(message.getBytes()); //ngasih paket ke frame
//					radio.transmitFrame(frame);
//					System.out.println("(" + i + ") SEND: " + msg);
//					isOK = true;
//				}
//				catch(Exception e) {
//					System.out.println("(" + i + ") ERROR: no receiver");
//				}
//			}
//			
//			Frame f = null;
//			try {
//				f = new Frame();
//				radio.setState(AT86RF231.STATE_RX_AACK_ON);
//				radio.waitForFrame(f);
//			}
//			catch(Exception e) {
//				
//			}
//			if(f!=null) {
//				byte[] dg = f.getPayload();
//				String str = new String(dg, 0, dg.length);
//				String hex_addr = Integer.toHexString((int) f.getSrcAddr());
//				System.out.println("FROM(" + hex_addr + "): " + str);
//			}
//			i++;
//		}
//	}
//	
//	public static void main(String[] args) throws Exception{
//		new sender().pSender();
//	}
//	
//	
//	
//}
