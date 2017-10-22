import java.util.*;

public class Connection extends Thread {
	
	long time;
	Hop lastHop;
	ArrayList<Link> path;
	
	
	
	
	public Connection(Hop lastHop) {
		this.lastHop = lastHop;
		this.path = lastHop.linkPath();
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void run() {
		this.setTime(System.nanoTime());
		for (Link l : this.path) {
			try {
				Thread.sleep(l.getDelay());
				System.out.println((System.nanoTime() - this.time)/1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
		
}
