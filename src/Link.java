
public class Link {
	
	private int load;
	private int start;
	private int end;
	private int delay;
	private int capacity;
	
	public Link(int start, int end, int delay, int capacity) {
		this.load = 0;
		this.start = start;
		this.end = end;
		this.delay = delay;
		this.capacity = capacity;
	}
	
	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getDelay() {
		return delay;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public int getLoad() {
		return load;
	}

	public void setLoad(int i) {
		this.load = i;
	}
	
	
	
	
}
