import java.util.ArrayList;
import java.util.PriorityQueue;

public class Network {
	
	private Link[][] graph;

	public Network() {
		this.graph = new Link[26][26];
	}
	
	//adds a node
	public void add(String n1, String n2, int delay, int capacity) {
		Link link = new Link(delay, capacity);
		this.graph[let2Num(n1)][let2Num(n2)] = link;
		this.graph[let2Num(n2)][let2Num(n1)] = link;
	}
	
	public void print() {
		for (int i = 0; i < 26; i++) {
			for (Link node : this.graph[i]) {
				if (node == null) {
					System.out.print("[ -1 ,  -1]\t");
				} else {
					String d = String.format("%3d", node.getDelay());
					String c = String.format("%3d", node.getCapacity());
					System.out.print("[" + d + " , " + c + "]\t");
				}
			}
			System.out.println();
		}
		
	}
	
	//finds all neighbouring nodes
	private ArrayList<Integer> getNeighbours(int node) {
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		
		for (int n = 0; n < 26; n++) {
			if (this.graph[node][n] != null) {
				neighbours.add(n);
			}
		}		
		return neighbours;
	}
	
	//Uniform cost searc/BFS/Djikstras
	
	public void SHP() {
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
 	}
	
	
	private int let2Num (String let) {
		switch (let.charAt(0)) {
			case 'A':
				return 0;
			case 'B':
				return 1;
			case 'C':
				return 2;
			case 'D':
				return 3;
			case 'E':
				return 4;
			case 'F':
				return 5;
			case 'G':
				return 6;
			case 'H':
				return 7;
			case 'I':
				return 8;
			case 'J':
				return 9;
			case 'K':
				return 10;
			case 'L':
				return 11;
			case 'M':
				return 12;
			case 'N':
				return 13;
			case 'O':
				return 14;
			case 'P':
				return 15;
			case 'Q':
				return 16;
			case 'R':
				return 17;
			case 'S':
				return 18;
			case 'T':
				return 19;
			case 'U':
				return 20;
			case 'V':
				return 21;
			case 'W':
				return 22;
			case 'X':
				return 23;
			case 'Y':
				return 24;
			case 'Z':
				return 25;
			default:
				return -1;
			
		}
	}
	
	
	
	
	
}
