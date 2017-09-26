import java.io.*;
import java.util.*;

public class RoutingPerformance {
	
	private String NETWORK_SCHEME;
	private String ROUTING_SCHEME;
	private File TOPOLOGY_FILE;
	private File WORKLOAD_FILE;
	private int PACKET_RATE;

	
	public RoutingPerformance(String nETWORK_SCHEME, String rOUTING_SCHEME, String tOPOLOGY_FILE, String wORKLOAD_FILE,
			String pACKET_RATE) {
		
		this.NETWORK_SCHEME = nETWORK_SCHEME;
		this.ROUTING_SCHEME = rOUTING_SCHEME;
		this.TOPOLOGY_FILE = new File(tOPOLOGY_FILE);
		this.WORKLOAD_FILE = new File(wORKLOAD_FILE);
		this.PACKET_RATE = Integer.parseInt(pACKET_RATE);
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		if (args.length != 5) {
			System.err.println("Incorrect Usage: <NETWORK_SCHEME> <ROUTING_SCHEME> "
					+ "<TOPOLOGY_FILE> <WORKLOAD_FILE> <PACKET_RATE>");
			
		}
		
		RoutingPerformance rp = new RoutingPerformance(args[0], args[1], args[2], args[3], args[4]);
		
		Scanner read = new Scanner(rp.TOPOLOGY_FILE);
		read.useDelimiter(" ");
		while (read.hasNext()) {
			System.out.println(read.next());
		}

	}
}