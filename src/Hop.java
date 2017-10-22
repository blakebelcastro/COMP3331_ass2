import java.util.ArrayList;
import java.util.Stack;

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

	/*
	 * Reverse the obtained path and so it prints
	 * from start to end correctly
	 */
	public ArrayList<Link> linkPath() {
		Stack<Link> links = new Stack<Link>();
		ArrayList<Link> path = new ArrayList<Link>();
		links.push(this.link);
		Hop h = this.prevHop;
		
		while (h != null) {
			links.push(h.getLink());
			h = h.getPrevHop();
		}
		
		while (!links.isEmpty()) {
			path.add(links.pop());
		}
		
		return path;
	}
	
	public void printPath() {
		ArrayList<Link> list = this.linkPath();
	
		for (Link l: list) {
			System.out.print(l.getStart() + " --> ");
		}
		System.out.println(list.get(list.size()-1).getEnd());
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
