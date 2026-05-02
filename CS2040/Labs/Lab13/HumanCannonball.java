//Krithikh Gopalakrishnan, A0243980Y
import java.util.*;
public class HumanCannonball {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double start_x = sc.nextDouble();
        double start_y = sc.nextDouble();
        double end_x = sc.nextDouble();
        double end_y = sc.nextDouble();

        int numcannons = sc.nextInt();

        double[][] cannons = new double[numcannons+2][2];
        for(int i=1; i<=numcannons; i++){
            cannons[i][0] = sc.nextDouble();
            cannons[i][1] = sc.nextDouble();
        }

        cannons[0][0] = start_x;
        cannons[0][1] = start_y;
        cannons[numcannons+1][0] = end_x;
        cannons[numcannons+1][1] = end_y;

        PriorityQueue<Vertex> Dijkstra = new PriorityQueue<>();
        double[] distances = new double[numcannons+2];
        for(int i=1; i<numcannons+2; i++){
            distances[i] = Double.POSITIVE_INFINITY;
        }
        distances[0] = 0;

        //We represent each cannon as an vertex with ID from 1 to numcannons,
        //and the end point is represented as a vertex with ID numcannons + 1.
        //The start point is represented by a vertex with index 0. The edges of the graph represent the time taken between each node.

        Dijkstra.add(new Vertex(0,0)); //(dist[u], u)

        while(!Dijkstra.isEmpty()){
            Vertex v = Dijkstra.poll();

            if(v.distance == distances[v.index]){
                for(int i=0; i< numcannons+2; i++){
                    double currtoNext = Math.hypot((cannons[v.index][0] - cannons[i][0]), (cannons[v.index][1] - cannons[i][1]));
                    double time = 0;
                    if(v.index!=0 && v.index!= numcannons+1){
                        time = Math.min(Math.abs(currtoNext-50) / 5 + 2, currtoNext / 5);
                    }
                    else{
                        time = currtoNext / 5;
                    }
                    if(distances[i] > distances[v.index] + time){
                        distances[i] = distances[v.index] + time;
                        Dijkstra.add(new Vertex(distances[i], i));
                    }
                }
            }
        } // Dijkstra done

        System.out.println(distances[numcannons + 1]);

    }
}

class Vertex implements Comparable<Vertex>{
    public double distance;
    public int index;
   Vertex(double d, int i){
        distance = d;
        index = i;
    }

    public int compareTo(Vertex v){
        if(this.distance-v.distance < 0) return -1;
        else if (this.distance-v.distance == 0) return 0;
        else return 1;
    }
}
