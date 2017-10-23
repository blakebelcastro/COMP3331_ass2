import java.io.*;
import java.util.*;

public class RoutingPerformance {
	
	private String NETWORK_SCHEME;
	private String ROUTING_SCHEME;
	private File TOPOLOGY_FILE;
	private File WORKLOAD_FILE;
	private int PACKET_RATE;
	private Network network;
	
	//statistic variables
	private int totalRequests;		//number of connection requests received
	private int successfulRequests; //successful connections made
	private int totalPackets;		//
	private int successfulPackets;
	private int blockedPackets;
	private int totalHops;
	private int totalPropDelay;

	
	public RoutingPerformance(String nETWORK_SCHEME, String rOUTING_SCHEME, String tOPOLOGY_FILE, String wORKLOAD_FILE,
			String pACKET_RATE) throws FileNotFoundException {
		
		this.NETWORK_SCHEME = nETWORK_SCHEME;
		this.ROUTING_SCHEME = rOUTING_SCHEME;
		this.TOPOLOGY_FILE = new File(tOPOLOGY_FILE);
		this.WORKLOAD_FILE = new File(wORKLOAD_FILE);
		this.PACKET_RATE = Integer.parseInt(pACKET_RATE);
		this.network = new Network(TOPOLOGY_FILE);
		
		this.totalRequests =  0;
		this.successfulRequests = 0;
		this.totalPackets = 0;
		this.successfulPackets = 0;
		this.blockedPackets = 0;
		this.totalHops = 0;
		this.totalPropDelay = 0;
	}

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		
		if (args.length != 5) {
			System.err.println("Incorrect Usage: <NETWORK_SCHEME> <ROUTING_SCHEME> "
					+ "<TOPOLOGY_FILE> <WORKLOAD_FILE> <PACKET_RATE>");
			
		}
		
		RoutingPerformance rp = new RoutingPerformance(args[0], args[1], args[2], args[3], args[4]);
		rp.setScheme();	
		
		//rp.network.print();
		if (rp.NETWORK_SCHEME.equals("CIRCUIT")) rp.circuitSwitch();
		if (rp.NETWORK_SCHEME.equals("PACKET")) rp.packetSwitch();
		rp.printStats();
	}

	private void printStats() {
		System.out.println("total number of virtual connection requests: " + totalRequests);
		System.out.println("total number of packets: " + totalPackets);
		System.out.println("number of successfully routed packets: " + successfulPackets);
		System.out.println("percentage of succesfully routed packets: " + String.format("%.2f", 100*((float)successfulPackets/(float)totalPackets)));
		System.out.println("number of blocked packets: " + blockedPackets);
		System.out.println("percentage of blocked packets: " + String.format("%.2f", 100*((float)blockedPackets/(float)totalPackets)));
		System.out.println("average number of hops per circuit: " + String.format("%.2f", (float)totalHops/(float)successfulRequests));
		System.out.println("average cumulative propogation delay per circuit: " + ((float)totalPropDelay/(float)successfulRequests));
//		System.out.println("number of successful requests: " + successfulRequests);
	}

	private void setScheme() {
		String ns = NETWORK_SCHEME;
		if (ns.equals("PACKET") || ns.equals("CIRCUIT")) {
			System.out.println("The network scheme will follow: " + ns);
		} else {
			System.err.println("Network scheme must be 'PACKET' or 'CIRCUIT'");
		}
		
		String rs = this.ROUTING_SCHEME;
		if (rs.equals("SHP") || rs.equals("SDP") || rs.equals("LLP")) {
			System.out.println("The routing scheme will follow: " + rs);
			
		} else {
			System.err.println("Routing scheme must be 'SHP', 'SDP', or 'LLP'");
		}
	}
	
	//uses info in a text file to create connection events
	//that attempt to connect and teardown according to their timings
	
	private void circuitSwitch() throws FileNotFoundException, InterruptedException {
		
	    PriorityQueue<Action> actions = new PriorityQueue<Action>();
		
		
		Scanner in = new Scanner(new FileReader(WORKLOAD_FILE));
		while(in.hasNextLine()) {
		    double start = Double.parseDouble(in.next());

		    String n1 = in.next();
		    int origin = Network.let2Num(n1);
		    
		    String n2 = in.next();
		    int destination = Network.let2Num(n2);
		    
//		    Hop path = network.pathSearch(origin, destination, ROUTING_SCHEME);

		    double duration = Double.parseDouble(in.next());
		    int nP = (int) Math.floor(duration*PACKET_RATE);

		    Action p = new Action(start + duration, origin, destination, Action.UNLOAD);
		    actions.add(new Action(start, origin, destination, Action.LOAD, nP, p));
		    actions.add(p);
		    totalRequests++;
		    totalPackets += nP;
		}
		in.close();
		
		while (!actions.isEmpty()) {
			Action a = actions.remove();
			//a.print();
			Hop path = network.pathSearch(a.getOrigin(), a.getDestination(), ROUTING_SCHEME);
			//System.out.print(path.getLLPratio() + ":: ");
			//path.printPath();
			if (path == null) {
				System.err.println("No path found!");
			} else if (a.getType() > 0) {
				if (network.hasCapacity(path.linkPath())) {
					a.getPair().setUnloadPath(path);	//prepare the teardown action
					successfulRequests++;
					successfulPackets += a.getNumPackets();
					totalHops += path.getNumHops() + 1;
					totalPropDelay += path.getTotalDelay();
					network.changeLoad(path.linkPath(), a.getType());
				} else {
					blockedPackets += a.getNumPackets();
					actions.remove(a.getPair()); //remove the future unload action from queue, not needed
					System.err.println("Connection blocked: link at capacity.");
				}
				
			} else if (a.getType() < 0) {
				network.changeLoad(a.getUnloadPath().linkPath(), a.getType());
			}
		}
	}
	
private void packetSwitch() throws FileNotFoundException, InterruptedException {
		
	    PriorityQueue<Action> actions = new PriorityQueue<Action>();
		Scanner in = new Scanner(new FileReader(WORKLOAD_FILE));
		
		while(in.hasNextLine()) {
		    double start = Double.parseDouble(in.next());
		    String n1 = in.next();
		    int origin = Network.let2Num(n1);
		    String n2 = in.next();
		    int destination = Network.let2Num(n2);
		    double duration = Double.parseDouble(in.next()); //duration of each connection 
		    		    
		    int numPackets = (int) Math.floor(duration*PACKET_RATE);
		    double interval = 1.0/(double)PACKET_RATE;

		    for (int i = 0; i < numPackets; i++) {
			    Action p = new Action( (start + ((i+1)*interval) ), origin, destination, Action.UNLOAD);
			    actions.add(new Action( (start + (i*interval)) , origin, destination, Action.LOAD, 1, p));
			    actions.add(p);
		    }
		    totalRequests++;
		    totalPackets += numPackets;
		}
		in.close();
		
		//int i = 0;
		while (!actions.isEmpty()) {
			Action a = actions.remove();
			Hop path = network.pathSearch(a.getOrigin(), a.getDestination(), ROUTING_SCHEME);
			if (path == null) {
				System.err.println("No path found!");
			} else if (a.getType() > 0) {
				if (network.hasCapacity(path.linkPath())) {
					a.getPair().setUnloadPath(path);	//prepare the teardown action
					successfulRequests++;
					successfulPackets += a.getNumPackets();
					totalHops += path.getNumHops() + 1;
					totalPropDelay += path.getTotalDelay();
					network.changeLoad(path.linkPath(), a.getType());
				} else {
					blockedPackets += a.getNumPackets();
					actions.remove(a.getPair()); //remove the future unload action from queue, not needed
				}
				
			} else if (a.getType() < 0) {
				//a.print();
				network.changeLoad(a.getUnloadPath().linkPath(), a.getType());
			}
		}
	}

	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

	public void incSuccessfulRequests() {
		successfulRequests++;
	}

	public void setTotalPackets(int totalPackets) {
		this.totalPackets = totalPackets;
	}

	public void incSuccessfulPackets(int num) {
		successfulPackets+=num;
	}

	public void incTotalHops(int num) {
		totalHops+=num;
	}

	public void setTotalPropDelay(int totalPropDelay) {
		this.totalPropDelay = totalPropDelay;
	}

	public void incBlockedPackets(int numPackets) {
		blockedPackets+=numPackets;
	}

	public void incTotalDelay(int totalDelay) {
		totalPropDelay+=totalDelay;
	}
}