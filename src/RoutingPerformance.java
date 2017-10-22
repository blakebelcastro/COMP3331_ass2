import java.io.*;
import java.util.*;

public class RoutingPerformance {
	
	private String NETWORK_SCHEME;
	private String ROUTING_SCHEME;
	private String TOPOLOGY_FILE;
	private String WORKLOAD_FILE;
	private int PACKET_RATE;
	private Network network;

	
	public RoutingPerformance(String nETWORK_SCHEME, String rOUTING_SCHEME, String tOPOLOGY_FILE, String wORKLOAD_FILE,
			String pACKET_RATE) throws FileNotFoundException {
		
		this.NETWORK_SCHEME = nETWORK_SCHEME;
		this.ROUTING_SCHEME = rOUTING_SCHEME;
		this.TOPOLOGY_FILE = tOPOLOGY_FILE;
		this.WORKLOAD_FILE = wORKLOAD_FILE;
		this.PACKET_RATE = Integer.parseInt(pACKET_RATE);
		this.network = new Network(tOPOLOGY_FILE);
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		if (args.length != 5) {
			System.err.println("Incorrect Usage: <NETWORK_SCHEME> <ROUTING_SCHEME> "
					+ "<TOPOLOGY_FILE> <WORKLOAD_FILE> <PACKET_RATE>");
			
		}
		
		RoutingPerformance rp = new RoutingPerformance(args[0], args[1], args[2], args[3], args[4]);
		
//		createNetwork(rp);
		
		
		setScheme(rp);
		
	}

	private static void setScheme(RoutingPerformance rp) {
		String ns = rp.NETWORK_SCHEME;
		if (ns.equals("PACKET") || ns.equals("CIRCUIT")) {
			System.out.println("The network scheme will follow: " + ns);
		} else {
			System.err.println("Network scheme must be 'PACKET' or 'CIRCUIT'");
		}
		
		String rs = rp.ROUTING_SCHEME;
		if (rs.equals("SHP") || rs.equals("SDP") || rs.equals("LLP")) {
			System.out.println("The routing scheme will follow: " + rs);
		} else {
			System.err.println("Routing scheme must be 'SHP', 'SDP', or 'LLP'");
		}
	}

//	private static void createNetwork(RoutingPerformance rp) throws FileNotFoundException {
//		Scanner in = new Scanner(new FileReader(rp.TOPOLOGY_FILE));
//		while(in.hasNextLine()) {
//		    String n1 = in.next();
//		    String n2 = in.next();
//		    int delay = Integer.parseInt(in.next());
//		    int capacity = Integer.parseInt(in.next());
//		    rp.network.add(n1, n2, delay, capacity);
//		}
//		in.close();
//		rp.network.print();
//		
//	}

}