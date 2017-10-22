
public class Hop implements Comparable<Hop>{
	private Link link;
	private Hop prevHop;
	private int numHops;
	private int totalDelay;
	private String networkScheme;
	
	public Hop(Link l, Hop h, int nH) {
		this.link = l;
		this.prevHop = h;
		this.numHops = nH;
		int prevDelay = 0;
		if (h != null) {
			prevDelay = h.getTotalDelay();
		}
		this.totalDelay = l.getDelay() + prevDelay;
	}

	public int getNumHops() {
		return numHops;
	}

	public int getTotalDelay() {
		return totalDelay;
	}

	public Link getLink() {
		return link;
	}

	public Hop getPrevHop() {
		return prevHop;
	}
	
	public String getNetworkScheme() {
		return networkScheme;
	}

	private void printPath() {
		
//		Hop h = this.prevHop;
//		while (this.prevHop != null) {
//			System.out.println(this.link.getStart() + "-->" + this.link.getEnd());
//			
//		}
	}
	
	@Override
	public int compareTo(Hop h) {
		if (h == null) {
			return 0;
		} else if (h.getNetworkScheme().equals("SHP")) {
			return (this.numHops - h.getNumHops());
		} else if (h.getNetworkScheme().equals("SDP")) {
			
			return (this.totalDelay - h.getTotalDelay());
		} else if (h.getNetworkScheme().equals("LLP")) {
			return 0; //TODO: implement LLP
		}
		return 0;
		
	}
	
	
}
