import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Hop implements Comparable<Hop>{
	private Link link;
	private Hop prevHop;
	private int numHops;
	private int totalDelay;
	private String routingScheme;
	private float LLPratio;
	
	public Hop(Link l, Hop h, int n, String rs) {
		this.link = l;
		System.out.println("Load: " + l.getLoad());
		this.prevHop = h;
		this.numHops = n;
		int prevDelay = 0;
		float llpr = (float)l.getLoad()/(float)l.getCapacity();
		if (h != null) {
			prevDelay = h.getTotalDelay();
			if (llpr < h.getLLPratio()) {
				System.out.println("From " + llpr +" to " + h.getLLPratio());
				llpr = h.getLLPratio();
			}
		}
		this.totalDelay = l.getDelay() + prevDelay;
		this.routingScheme = rs;
		this.LLPratio = llpr;
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

	public float getLLPratio() {
		return LLPratio;
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
			System.out.print(Network.num2Let(l.getStart()) + "(" + this.LLPratio + ")" + "->");
		}
		System.out.println(Network.num2Let(list.get(list.size()-1).getEnd()));
		
	}
	
	@Override
	public int compareTo(Hop h) {
		int retval = 0;
		if (h == null) {
			retval = 0;
		} else if (h.getRoutingScheme().equals("SHP")) {
			retval = (this.numHops - h.getNumHops());
		} else if (h.getRoutingScheme().equals("SDP")) {
			retval = (this.totalDelay - h.getTotalDelay());
		} else if (h.getRoutingScheme().equals("LLP")) {
			if ((this.LLPratio - h.getLLPratio()) < 0) retval = -1;
			if ((this.LLPratio - h.getLLPratio()) > 0) retval = 1;
			if ((this.LLPratio - h.getLLPratio()) == 0) retval = 0;
		}
		if (retval == 0) {
			Random rand = new Random(System.currentTimeMillis());
			if (rand.nextInt() > 0.5) retval = 1;
			else retval = -1;
		}
		return retval;
	}
	
	
}
