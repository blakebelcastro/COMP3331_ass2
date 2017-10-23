
public class Action implements Comparable<Action> {
	
	public static final int LOAD = 1;
	public static final int UNLOAD = -1;
	private double time;
	private int type;
	private int numPackets = 0;
	private int origin;
	private int destination;
	private Action pair = null;
	
	
	
	public Action(double time, int origin, int destination, int type, int numPackets, Action pair) {
		this.time = time;
		this.origin = origin;
		this.destination = destination;
		this.type = type;
		this.numPackets = numPackets;
		this.pair = pair;
	}
	
	public Action(double time, int origin, int destination, int type) {
		this.time = time;
		this.origin = origin;
		this.destination = destination;
		this.type = type;
	}
	
	public double getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public int getNumPackets() {
		return numPackets;
	}
	
	public int getOrigin() {
		return origin;
	}

	public int getDestination() {
		return destination;
	}

	public Action getPair() {
		return pair;
	}

	public void print() {
		System.out.println(type + "\t" + time + "\t");
	}

	@Override
	public int compareTo(Action a) {
		if ((this.time - a.getTime()) < 0) return -1;
		else if ((this.time - a.getTime()) > 0) return 1;
		else return 0;	
	}
	
}