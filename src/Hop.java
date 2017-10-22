
public class Hop implements Comparable<Hop>{
	private Link link;
	private Hop prevHop;
	private int numHops;
	private int totalDelay;
	private String routingScheme;
	
	public Hop(Link l, Hop h, int n, String rs) {
		this.link = l;
		this.prevHop = h;
		this.numHops = n;
		int prevDelay = 0;
		if (h != null) {
			prevDelay = h.getTotalDelay();
		}
		this.totalDelay = l.getDelay() + prevDelay;
		this.routingScheme = rs;
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
	
	public String getRoutingScheme() {
		return routingScheme;
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
		} else if (h.getRoutingScheme().equals("SHP")) {
			return (this.numHops - h.getNumHops());
		} else if (h.getRoutingScheme().equals("SDP")) {
			return (this.totalDelay - h.getTotalDelay());
		} else if (h.getRoutingScheme().equals("LLP")) {
			return 0; //TODO: implement LLP
		}
		return 0;
		
	}
	
	
}
