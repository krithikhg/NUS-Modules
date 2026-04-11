//Krithikh Gopalakrishnan, A0243980Y
//Used UFDS code from Lecture Materials, Kruskal's algorithm code slightly modified from Lecture code
import java.util.*;

public class LostMap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int villages = sc.nextInt();

        ArrayList<int[]> EdgeList = new ArrayList<>();

        for(int i=0; i<villages; i++){
            for(int j=0; j<villages; j++){
                int weight = sc.nextInt();
                if(i<j){
                    EdgeList.add(new int[]{i,j,weight});
                }
            }
        }

        EdgeList.sort(Comparator.comparing(a -> a[2])); //sort EdgeList in ascending order

        //Run Kruskal's using EdgeList
        UnionFind UF = new UnionFind(villages); 
        ArrayList<int[]> roads = new ArrayList<>();
        for (int i = 0; i < EdgeList.size(); i++) {
            int[] e = EdgeList.get(i);
            int u = e[0], v = e[1]
            if (!UF.isSameSet(u, v)) { 
                UF.unionSet(u, v);
                roads.add(e);
            }
        }

        for (int i=0; i<roads.size(); i++){
            System.out.println((roads.get(i)[0] + 1) + " " + (roads.get(i)[1] + 1));
        }

        sc.close();

    }
}




// Union-Find Disjoint Sets Library, using both path compression and union by rank heuristics, from Lecture materials
class UnionFind {
  public int[] p;
  public int[] rank;
  public int[] setSize;
  public int numSets;

  public UnionFind(int N) {
    p = new int[N];
    rank = new int[N];
    setSize = new int[N];
    numSets = N;
    for (int i = 0; i < N; i++) {
      p[i] = i;
      rank[i] = 0;
      setSize[i] = 1;
    }
  }

  public int findSet(int i) { 
    if (p[i] == i) return i;
    else {
      p[i] = findSet(p[i]);
      return p[i]; 
    } 
  }

  public Boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

  public void unionSet(int i, int j) { 
    if (!isSameSet(i, j)) { 
      numSets--; 
      int x = findSet(i), y = findSet(j);
      // rank is used to keep the tree short
      if (rank[x] > rank[y]) { 
      	p[y] = x; 
      	setSize[x] = setSize[x] + setSize[y]; 
      }
      else { 
      	p[x] = y; 
      	setSize[y] = setSize[x] + setSize[y];
        if (rank[x] == rank[y]) 
          rank[y] = rank[y]+1; 
      } 
    } 
  }

  public int numDisjointSets() { return numSets; }

  public int sizeOfSet(int i) { return setSize[findSet(i)]; }
}