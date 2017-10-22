import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Network {
	
	private Link[][] graph = new Link[26][26];

	public Network(String fileName) throws FileNotFoundException {
		Link[][] network = new Link[26][26];
		Scanner in = new Scanner(new FileReader(fileName));
		while(in.hasNextLine()) {
		    String n1 = in.next();
		    String n2 = in.next();
		    int delay = Integer.parseInt(in.next());
		    int capacity = Integer.parseInt(in.next());
		    network = add(network, n1, n2, delay, capacity);
		}
		in.close();
		this.graph = network; 
		print(this.graph);
	}
	
	private Link[][] add(Link[][] g, String n1, String n2, int delay, int capacity) {
		Link link = new Link(delay, capacity);
		g[let2Num(n1)][let2Num(n2)] = link;
		g[let2Num(n2)][let2Num(n1)] = link;
		return g;
	}
	
	private void print(Link[][] g) {
		for (int i = 0; i < 26; i++) {
			for (Link link : g[i]) {
				if (link == null) {
					System.out.print("[ -1 ,  -1]\t");
				} else {
					String d = String.format("%3d", link.getDelay());
					String c = String.format("%3d", link.getCapacity());
					System.out.print("[" + d + " , " + c + "]\t");
				}
			}
			System.out.println();
		}
		
	}
	
	private void getNeighbours() {
		
	}
	
	public void SHP() {
		
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
	
	private int num2Let (int num) {
		switch (num) {
			case 0:
				return 'A';
			case 1:
				return 'B';
			case 2:
				return 'C';
			case 3:
				return 'D';
			case 4:
				return 'E';
			case 5:
				return 'F';
			case 6:
				return 'G';
			case 7:
				return 'H';
			case 8:
				return 'I';
			case 9:
				return 'J';
			case 10:
				return 'K';
			case 11:
				return 'L';
			case 12:
				return 'M';
			case 13:
				return 'N';
			case 14:
				return 'O';
			case 15:
				return 'P';
			case 16:
				return 'Q';
			case 17:
				return 'R';
			case 18:
				return 'S';
			case 19:
				return 'T';
			case 20:
				return 'U';
			case 21:
				return 'V';
			case 22:
				return 'W';
			case 23:
				return 'X';
			case 24:
				return 'Y';
			case 25:
				return 'Z';
			default:
				return '0';
			
		}
	}
	
	
	
	
	
}
