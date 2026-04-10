//Krithikh Gopalakrishnan, A0243980Y
import java.util.*;

public class Islands3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        char[][] map = new char[rows][cols];
        
        String garbage = sc.nextLine();

        //populate map from scanner input
        for (int i=0; i<rows; i++){
            String row = sc.nextLine();
            for (int j=0; j<cols; j++){
                map[i][j] = row.charAt(j);
            }
        }

        int islands = 0;
        int[][] visited = new int[rows][cols];

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(map[i][j] == 'L' && visited[i][j] == 0){
                    islands ++;
                    //start BFS from this cell
                    Deque<int[]> bfs = new ArrayDeque<>();
                    bfs.offerLast(new int[]{i,j});
                    visited[i][j] = 1;
                    while (!bfs.isEmpty()){
                        //check up, down, left and right if they are land/cloud and if they are within bounds, then add to queue (also check if they've been visited)
                        int[] curr = bfs.pollFirst();
                        int r = curr[0];
                        int c = curr[1];
                        if((r-1) >= 0 && visited[r-1][c] == 0 && (map[r-1][c] == 'L' || map[r-1][c] == 'C')){ // check up
                            bfs.offerLast(new int[]{r-1,c});
                            visited[r-1][c] = 1;
                        }
                        if((r+1) < rows && visited[r+1][c] == 0 && (map[r+1][c] == 'L' || map[r+1][c] == 'C')){ // check down
                            bfs.offerLast(new int[]{r+1,c});
                            visited[r+1][c] = 1;
                        }
                        if((c-1) >= 0 && visited[r][c-1] == 0 && (map[r][c-1] == 'L' || map[r][c-1] == 'C')){ // check left
                            bfs.offerLast(new int[]{r,c-1});
                            visited[r][c-1] = 1;
                        }
                        if((c+1) < cols && visited[r][c+1] == 0 && (map[r][c+1] == 'L' || map[r][c+1] == 'C')){ // check right
                            bfs.offerLast(new int[]{r,c+1});
                            visited[r][c+1] = 1;
                        }
                    }
                }
            }
        }

        System.out.println(islands);

    }
}
