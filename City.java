package assign;
import java.util.*;
class Node {
    String source;
    String destination;
    int weight;
    Node(String src, String dest, int wt) {
        this.source = src;
        this.destination = dest;
        this.weight = wt;
    }
}
class Explore {
    List<List<Node>> graph;//graph
    HashMap<String, List<String>> map = new HashMap<>();//hashmap
    ArrayList<String> list = new ArrayList<>();
    public Explore()
    {//significance--null pointer

        graph = new ArrayList<>(); // Initialize the graph List

    }
    public void addPlace(String key, String value) {
        if (!map.containsKey(key))
        {
            map.put(key, new ArrayList<>());
        }
        Collections.addAll(map.get(key), value);//
        graph.add(new ArrayList<>());
        list.add(value);
    }
    public List<String> getPlace(String key)
    {

        return map.get(key);
    }
    public void connectPlaces(String source, String destination, int distance)
    {
        if (list.contains(source) && list.contains(destination))
        {
            int sourceIndex = list.indexOf(source);
            int destinationIndex = list.indexOf(destination);
            if (sourceIndex != -1 && destinationIndex != -1)
            {
                graph.get(sourceIndex).add(new Node(source, destination, distance));
                graph.get(destinationIndex).add(new Node(destination, source, distance));
            }
        }
        else
        {
            System.out.println("Places does not exist");
        }
    }
    static class Pair  implements Comparable<Pair>{//static nested class which implements the 'Comparable' interface
//       static kiya kyuki class can be used without creating an instance of the outer class.
//        The Comparable interface hai which will be used to compare one instace with other instance.
//        Mtlb tum log ek pair class ke object ko dusre object ke sath compare kr rahe ho based on some criteria
        int src;
        int path;
        public Pair(int src,int Path){
            this.src=src;
            this.path=path;
        }
        @Override//this is to show that meri neche ki method(compare To) is override method written in interfface(Comparable)
        public int compareTo(Pair p2){//This is the overridden compareTo method
//       it compares the path values of two Pair objects (this and p2) and returns the result of the comparison.
            return this.path-p2.path;
            //agr this.path less than p2 toh return -ve no
            //agr this.path equal to p2 to 0
            //agr this.path grater than p2 toh +ve no
        }
    }
    public int shortPath(String source,String dest){
        int dist[]=new int [graph.size()];
        int parent[] = new int[graph.size()]; // Array to store parent nodes
        int srcIndex= list.indexOf(source);
        int destIndex=list.indexOf(dest);
        Arrays.fill(parent, -1); // Initialize parent nodes to -1
        for(int i=0;i<graph.size();i++){//initializing sbke values to infinity except src
            if(i!=srcIndex){
                dist[i]=Integer.MAX_VALUE;//+infinity
            }
        }
        boolean vis[]=new boolean[graph.size()];
        PriorityQueue<Pair> pq=new PriorityQueue<>();
        pq.add(new Pair(srcIndex,0));//source to source ka distance 0
        //loop
        while(!pq.isEmpty()){
            Pair curr =pq.remove();
            if(!vis[curr.src]){
                vis[curr.src]=true;
                //neighbour
                for(int i=0;i<graph.get(curr.src).size();i++){
                    Node n=graph.get(curr.src).get(i);
                    String u=n.source;
                    int srcI= list.indexOf(u);
                    String v=n.destination;
                    int destI= list.indexOf(v);
                    int w=n.weight;
                    if(dist[srcI]+w<dist[destI]){
                        dist[destI]=dist[srcI]+w;
                        parent[destI] = curr.src;
                        pq.add(new Pair(destI,dist[destI]));
                    }

                }
            }
        }
        // Reconstruct the shortest path and print it
        printShortestPath(parent, srcIndex, destIndex);
        return dist[destIndex];
    }
    private void printShortestPath(int[] parent, int src, int dest) {
        ArrayList<String> path = new ArrayList<>();
        int current = dest;
        while (current != src) {
            path.add(0, list.get(current));
            current = parent[current];
        }
        path.add(0, list.get(src));
        System.out.println("Shortest Path: " + String.join(" -> ", path));
    }
    public void showAdjacencyList()
    {
        for (int i = 0; i < graph.size(); i++) {
            String placeName = list.get(i);
            List<Node> neighbors = graph.get(i);
            System.out.print("Adjacency list for " + placeName + ": ");
            for (Node neighbor : neighbors)
            {
                System.out.print("(" + neighbor.destination + ", " + neighbor.weight + ")--> ");
            }
            System.out.println();
        }
    }
}

public class City {
	//public class City
    private static void adminMenu(Explore explorer, Scanner sc)
    {
        Explore obj=new Explore();
        int adminChoice;
        do {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Place");
            System.out.println("2. Connect Places");
            System.out.println("3. Display the connections");
            System.out.println("4. Back to Main Menu");
            adminChoice = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            switch (adminChoice)
            {
                case 1:
                    System.out.println("Enter place type(key) ");
                    //System.out.println("Available keys:");
                    System.out.println("1)College\n2)Hospital\n3)Tourist Place");
                    String key = sc.nextLine();
                    System.out.print("Enter place name (value): ");
                    String value = sc.nextLine();
                    explorer.addPlace(key, value);
                    System.out.println("Place added successfully!");
                    break;
                case 2:
                    System.out.print("Enter source place: ");
                    
                    String source = sc.nextLine();
                    System.out.print("Enter destination place: ");
                    String destination = sc.nextLine();
                    System.out.print("Enter distance between places: ");
                    int distance = sc.nextInt();
                    explorer.connectPlaces(source, destination, distance);
                    System.out.println("Places connected successfully!");
                    break;
                case 3:
                    explorer.showAdjacencyList();
                    break;
                case 4:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
        while (adminChoice != 4);
    }
    private static void userMenu(Explore explorer, Scanner sc) {
// Implement user functionality here
        int userchoice;
        do {
            System.out.println("\nUser Menu:");
            System.out.println("1.Search for Places by Type");
            System.out.println("2.Find Shortest Path");
            System.out.println("3.Back to Main Menu");
            userchoice = sc.nextInt();
            sc.nextLine();
            switch (userchoice) {
                case 1:
                    System.out.println("Enter the place type to search for(College/Hospital/Tourist Place):");
                    String placeType = sc.nextLine();
                    List<String> places = explorer.getPlace(placeType);
                    if (places != null && !places.isEmpty())
                    {
                        System.out.println("Places for type " + placeType + " in the city");
                        for (String place : places)
                        {
                            System.out.println(place);
                        }
                    }
                    else
                    {
                        System.out.println("No places found of this type!");
                    }
                    break;
                case 2:
                    System.out.println("Enter the starting place:");
                    String start = sc.nextLine();
                    System.out.println("Enter the destination place:");
                    String end = sc.nextLine();
                    int shortestPath = explorer.shortPath(start, end);
                    if (shortestPath!=0)
                    {
                        System.out.println("Total Distance: "+shortestPath+" kilometers");
                        System.out.println("Total Fare: ");
                        System.out.println("Bus: "+shortestPath+" rupees");
                        System.out.println("Rickshaw: "+shortestPath*10+" rupees");
                        System.out.println("Taxi: "+shortestPath*15+" rupees");
                    }
                    else {
                        System.out.println("No path found between " + start + " to " + end);
                    }
                    break;
                case 3:
                    System.out.println("Returning to the Main menu");
                    break;
                default:
                    System.out.println("Invalid choice!Please select 1,2 or 3.");
            }
        } while (userchoice != 3);

    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
        Explore explorer = new Explore();
        explorer.addPlace("College", "Cummins");
        explorer.addPlace("College", "MIT");
        explorer.addPlace("College", "COEP");
        explorer.addPlace("Hospital", "Sanhyadri Hospital");
        explorer.addPlace("Hospital", "Jupiter Hospital");
        explorer.addPlace("Hospital", "Ruby Hospital");
        explorer.addPlace("Tourist Place", "Singad Fort");
        explorer.addPlace("Tourist Place", "Shaniwar Wada");
        explorer.addPlace("Tourist Place", "ARAI");
        explorer.connectPlaces("Cummins", "MIT", 10);
        explorer.connectPlaces("Cummins", "COEP", 20);
        explorer.connectPlaces("Cummins", "Jupiter Hospital", 40);
        explorer.connectPlaces("MIT", "Singad Fort", 28);
        explorer.connectPlaces("COEP", "Sanhyadri Hospital", 25);
        explorer.connectPlaces("Singad Fort", "Shaniwar Wada", 45);
        explorer.connectPlaces("Singad Fort", "Ruby Hospital", 20);
        explorer.connectPlaces("Shaniwar Wada", "ARAI", 17);
        explorer.connectPlaces("ARAI", "Sanhyadri Hospital", 42);
        explorer.connectPlaces("Ruby Hospital", "ARAI", 2);
        explorer.connectPlaces("Ruby Hospital", "Jupiter Hospital", 37);
        explorer.connectPlaces("Jupiter Hospital", "Sanhyadri Hospital", 37);
        System.out.println("****************************************");
        System.out.println("       Welcome to CITY EXPLORER");
        System.out.println("****************************************");
        int ch;
        do
        {
            System.out.println("How do you wish to sign in as:");
            System.out.println("1.Admin");
            System.out.println("2.User");
            System.out.println("3. Exit");
            ch = sc.nextInt();
            switch (ch)

            {
                case 1:
                    adminMenu(explorer, sc);//userMenu(explorer, sc);
                    break;
                case 2:
                    userMenu(explorer, sc);
                    break;
                case 3:
                    System.out.println("Exiting City Explorer. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }while (ch != 3);

	}

}
/*OUTPUT:
 * ****************************************
       Welcome to CITY EXPLORER
****************************************
How do you wish to sign in as:
1.Admin
2.User
3. Exit
1
Admin Menu:
1. Add Place
2. Connect Places
3. Display the connections
4. Back to Main Menu
1
Enter place type(key) 
1)College
2)Hospital
3)Tourist Place
College
Enter place name (value): Symbiosis
Place added successfully!
Admin Menu:
1. Add Place
2. Connect Places
3. Display the connections
4. Back to Main Menu
2
Enter source place: Symbiosis
Enter destination place: MIT
Enter distance between places: 45
Places connected successfully!
Admin Menu:
1. Add Place
2. Connect Places
3. Display the connections
4. Back to Main Menu
3
Adjacency list for Cummins: (MIT, 10)--> (COEP, 20)--> (Jupiter Hospital, 40)--> 
Adjacency list for MIT: (Cummins, 10)--> (Singad Fort, 28)--> (Symbiosis, 45)--> 
Adjacency list for COEP: (Cummins, 20)--> (Sanhyadri Hospital, 25)--> 
Adjacency list for Sanhyadri Hospital: (COEP, 25)--> (ARAI, 42)--> (Jupiter Hospital, 37)--> 
Adjacency list for Jupiter Hospital: (Cummins, 40)--> (Ruby Hospital, 37)--> (Sanhyadri Hospital, 37)--> 
Adjacency list for Ruby Hospital: (Singad Fort, 20)--> (ARAI, 2)--> (Jupiter Hospital, 37)--> 
Adjacency list for Singad Fort: (MIT, 28)--> (Shaniwar Wada, 45)--> (Ruby Hospital, 20)--> 
Adjacency list for Shaniwar Wada: (Singad Fort, 45)--> (ARAI, 17)--> 
Adjacency list for ARAI: (Shaniwar Wada, 17)--> (Sanhyadri Hospital, 42)--> (Ruby Hospital, 2)--> 
Adjacency list for Symbiosis: (MIT, 45)--> 
Admin Menu:
1. Add Place
2. Connect Places
3. Display the connections
4. Back to Main Menu
4
Returning to the main menu.
How do you wish to sign in as:
1.Admin
2.User
3. Exit
2

User Menu:
1.Search for Places by Type
2.Find Shortest Path
3.Back to Main Menu
1
Enter the place type to search for(College/Hospital/Tourist Place):
College
Places for type College in the city
Cummins
MIT
COEP
Symbiosis

User Menu:
1.Search for Places by Type
2.Find Shortest Path
3.Back to Main Menu
2
Enter the starting place:
MIT
Enter the destination place:
COEP
Shortest Path: MIT -> Cummins -> COEP
Total Distance: 30 kilometers
Total Fare: 
Bus: 30 rupees
Rickshaw: 300 rupees
Taxi: 450 rupees

User Menu:
1.Search for Places by Type
2.Find Shortest Path
3.Back to Main Menu
3
Returning to the Main menu
How do you wish to sign in as:
1.Admin
2.User
3. Exit
3
Exiting City Explorer. Goodbye!
*/
