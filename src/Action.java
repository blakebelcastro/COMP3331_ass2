
public class Action implements Comparable<Action> {
	
	public static final int LOAD = 1;
	public static final int UNLOAD = -1;
	private double time;
	private int type;
	private Hop path;
	private int numPackets = 0;
	
	
	
	public Action(double time, int type, Hop path, int numPackets) {
		this.time = time;
		this.type = type;
		this.path = path;
		this.numPackets = numPackets;
	}
	
	public Action(double time, int type, Hop path) {
		this.time = time;
		this.type = type;
		this.path = path;
	}
	
	public double getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public Hop getPath() {
		return path;
	}

	public int getNumPackets() {
		return numPackets;
	}
	
	public void print() {
		System.out.println(type + "\t" + time + "\t");
//		path.printPath();
	}

	@Override
	public int compareTo(Action a) {
		if ((this.time - a.getTime()) < 0) return -1;
		else if ((this.time - a.getTime()) > 0) return 1;
		else return 0;	
	}
	
}