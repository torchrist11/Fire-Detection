
public class Semaphore {
	private int value;
	
/*	public Semaphore() 
	{
		value=0;
	}
*/	
	public Semaphore (int value) 
	{
		this.value = value;
	}
	
	public synchronized void waits()  // implementasi down() 
	{
		while (value == 0) {
			try{
				wait();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		value--;
	}
	
	public synchronized void signals() // implementasi up()
	{
		value++;
		notifyAll();
	}
}