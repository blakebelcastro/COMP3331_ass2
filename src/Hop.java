
public class Hop {
	private Link link;
	private Hop prevHop;
	
	public Hop(Link l, Hop h) {
		this.link = l;
		this.prevHop = h;
	}

	public Link getLink() {
		return link;
	}

	public Hop getPrevHop() {
		return prevHop;
	}
	
	
}
