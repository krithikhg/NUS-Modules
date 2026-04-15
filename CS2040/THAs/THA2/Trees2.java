//Krithikh Gopalakrishnan, A0243980Y

import java.util.*;

public class Trees2{
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        int queries = sc.nextInt();

        AVL tree = new AVL();

        for(int i=0; i<queries; i++){
            String op = sc.next();
            if(op.equals("u")){ //uproot tree
                int P = sc.nextInt();
                int V = sc.nextInt();
                BSTVertex node = tree.search(tree.root, P);
                if(node == null){
                    System.out.println("0");
                    tree.insert(P, V);
                }
                else{
                    System.out.println(node.vol);
                    tree.delete(P);
                    tree.insert(P,V);
                }
            }

            else{ //query
                int L = sc.nextInt();
                int R = sc.nextInt();
                System.out.println(findMaxVol(tree.root, L, R));
            }
        }
        sc.close();

    }

    public static int findMaxVol(BSTVertex node, int L, int R){
        int maxVol = 0;

        if(node == null || node.minPos > R || node.maxPos < L) return 0;

        if(node.minPos >= L && node.maxPos <= R){
            return node.maxVol;
        }

        if(node.pos >= L && node.pos<= R){
            maxVol = node.vol;
        }

        maxVol = Math.max(maxVol, findMaxVol(node.right, L, R));
        maxVol = Math.max(maxVol, findMaxVol(node.left, L, R));

        return maxVol;
    }
}
