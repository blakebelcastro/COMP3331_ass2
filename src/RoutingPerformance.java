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
	private static int successfulRequests; //successful connections made
	private int totalPackets;		//
	private static int successfulPackets;
	private static int blockedPackets;
	private static int totalHops;
	private static int totalPropDelay;

	
	public RoutingPerformance(String nETWORK_SCHEME, String rOUTING_SCHEME, String tOPOLOGY_FILE, String wORKLOAD_FILE,
			String pACKET_RATE) throws FileNotFoundException {
		
		this.NETWORK_SCHEME = nETWORK_SCHEME;
		this.ROUTING_SCHEME = rOUTING_SCHEME;
		this.TOPOLOGY_FILE = new File(tOPOLOGY_FILE);
		this.WORKLOAD_FILE = new File(wORKLOAD_FILE);
		this.PACKET_RATE = Integer.parseInt(pACKET_RATE);
		this.network = new Network(tOPOLOGY_FILE);
		
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
		
		
		rp.network.print();
		rp.createConnections();
		rp.printStats();
	}

	private void printStats() {
		System.out.println("total number of virtual connection requests: " + totalRequests);
		System.out.println("total number of packets: " + totalPackets);
		System.out.println("number of successfully routed packets: " + successfulPackets);
		System.out.println("percentage of succesfully routed packets: " + 100*((float)successfulPackets/(float)totalPackets));
		System.out.println("number of blocked packets: " + blockedPackets);
		System.out.println("percentage of blocked packets: " + 100*((float)blockedPackets/(float)totalPackets));
		System.out.println("average number of hops per circuit: " + ((float)totalHops/(float)successfulRequests));
		System.out.println("average cumulative propogation delay per circuit: " + ((float)totalPropDelay/(float)successfulRequests));
		
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
	
	private void createConnections() throws FileNotFoundException, InterruptedException {
		ArrayList<Connection> cList = new ArrayList<Connection>();
		Scanner in = new Scanner(new FileReader(WORKLOAD_FILE));
		long startTime = System.nanoTime(); //zero time workload begins
		while(in.hasNextLine()) {
		    double start = Double.parseDouble(in.next());
		    String n1 = in.next();
		    String n2 = in.next();
		    double duration = Double.parseDouble(in.next());
		    //create a connection with above info
		    Connection c = new Connection(network, start, duration, n1, n2, 
		    		startTime, ROUTING_SCHEME, PACKET_RATE);
		    c.start();
		    cList.add(c);
		    totalRequests++;
		    totalPackets += (int) Math.floor(duration*PACKET_RATE);
		}
		in.close();
		for (Connection c : cList) {
			c.join();
		}
		
	}

	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

	public static void incSuccessfulRequests() {
		successfulRequests++;
	}

	public void setTotalPackets(int totalPackets) {
		this.totalPackets = totalPackets;
	}

	public static void incSuccessfulPackets(int num) {
		successfulPackets+=num;
	}

	public static void incTotalHops(int num) {
		totalHops+=num;
	}

	public void setTotalPropDelay(int totalPropDelay) {
		this.totalPropDelay = totalPropDelay;
	}

	public static void incBlockedPackets(int numPackets) {
		blockedPackets+=numPackets;
		
	}

	public static void incTotalDelay(int totalDelay) {
		totalPropDelay+=totalDelay;
	}
}