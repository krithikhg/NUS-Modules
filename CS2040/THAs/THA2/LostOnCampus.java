//Krithikh Gopalakrishnan, A0243980Y
import java.util.*;

public class LostOnCampus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int width = sc.nextInt();
        int height = sc.nextInt();
        String garbage = sc.nextLine();

        int[][] map = new int[height][width];
        int[][] distances = new int[height][width];

        int[] start = new int[2];
        ArrayList<Integer[]> exits= new ArrayList<>();

        for(int i=0; i<height; i++){
            String row = sc.nextLine();
            for(int j=0; j<width; j++){
                map[i][j] = row.charAt(j);
                if(row.charAt(j) == '*'){
                    start[0] = i; start[1] = j;
                }
                else if(row.charAt(j) == 'E'){
                    exits.add(new Integer[]{i,j});
                }
                distances[i][j] = 999999;
            }
        }

        distances[start[0]][start[1]] = 0; 

        PriorityQueue<int[]> Dijkstra = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        Dijkstra.add(new int[]{0, (start[0] * width + start[1])}); //(dist[u], u)

        while (!Dijkstra.isEmpty()){
            int[] vertex = Dijkstra.poll();
            int i = vertex[1] / width;
            int j = vertex[1] % width;
            if(vertex[0] == distances[i][j]){
                if((i-1)>=0 && (map[i-1][j] == 'D' || map[i-1][j] == '.' || map[i-1][j] == 'E' || map[i-1][j] == '*')){ //check up
                    int weight = 0;
                    if(map[i-1][j] == 'D') weight = 1;
                    if(distances[i-1][j] > distances[i][j] + weight){
                        distances[i-1][j] = distances[i][j] + weight;
                        Dijkstra.add(new int[]{distances[i-1][j], (i-1)*width + j});
                    }
                }
                if((i+1)<height && (map[i+1][j] == 'D' || map[i+1][j] == '.' || map[i+1][j] == 'E' || map[i+1][j] == '*')){ //check down
                    int weight = 0;
                    if(map[i+1][j] == 'D') weight = 1;
                    if(distances[i+1][j] > distances[i][j] + weight){
                        distances[i+1][j] = distances[i][j] + weight;
                        Dijkstra.add(new int[]{distances[i+1][j], (i+1)*width + j});
                    }
                }
                if((j-1)>0 && (map[i][j-1] == 'D' || map[i][j-1] == '.' || map[i][j-1] == 'E' || map[i][j-1] == '*')){ //check left
                    int weight = 0;
                    if(map[i][j-1] == 'D') weight = 1;
                    if(distances[i][j-1] > distances[i][j] + weight){
                        distances[i][j-1] = distances[i][j] + weight;
                        Dijkstra.add(new int[]{distances[i][j-1], i*width + j-1});
                    }
                }
                if((j+1)<width && (map[i][j+1] == 'D' || map[i][j+1] == '.' || map[i][j+1] == 'E' || map[i][j+1] == '*')){ //check right
                    int weight = 0;
                    if(map[i][j+1] == 'D') weight = 1;
                    if(distances[i][j+1] > distances[i][j] + weight){
                        distances[i][j+1] = distances[i][j] + weight;
                        Dijkstra.add(new int[]{distances[i][j+1], i*width + j+1});
                    }
                }
            }
        }// Dijkstra done

        if(exits.isEmpty()){
            System.out.println("NOT POSSIBLE");
        }

        else{
            int minDist = 999999;
            for(int i=0; i<exits.size(); i++){
                if(distances[exits.get(i)[0]][exits.get(i)[1]] < minDist) 
                    minDist = distances[exits.get(i)[0]][exits.get(i)[1]];
            }

            if(minDist < 999999){
                System.out.println(minDist);
            }
            else{
                System.out.println("NOT POSSIBLE");
            }
        }



    }
}
