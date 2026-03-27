import java.util.*;
public class WeakVertices {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true){
            int n = sc.nextInt();
            if(n == -1) break;
            
            int[] triangle = new int[n];
            int[][] adjacency = new int[n][n];
            
            for (int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    adjacency[i][j] = sc.nextInt();
                }
            }

            for (int i=0; i<n; i++){
                ArrayList<Integer> b = new ArrayList<>();
                for (int j=0; j<n; j++){
                    if(adjacency[i][j] == 1 && j != i){
                        b.add(j);
                    }
                }

                for (int j=0; j<b.size(); j++){
                    int vertexJ = b.get(j); 
                    ArrayList<Integer> c = new ArrayList<>();
                    for (int k=0; k<n; k++){
                        if(adjacency[vertexJ][k] == 1 && k != vertexJ && k != i){
                            c.add(k);
                        }
                    }
                    
                    for (int k=0; k<c.size(); k++){
                        int vertexK = c.get(k); 
                        if(adjacency[i][vertexK] == 1){
                            triangle[i] = 1;
                            triangle[vertexJ] = 1;
                            triangle[vertexK] = 1;
                        }
                    }
                }
            }

            for (int count=0; count<n; count++){
                if (triangle[count] == 0) {
                    System.out.print(count + " ");
                }
            }
            System.out.println("");
        }
        sc.close();
    }
}
