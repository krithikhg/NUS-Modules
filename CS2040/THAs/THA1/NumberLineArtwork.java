//Krithikh Gopalakrishnan, A0243980Y

import java.util.*;

public class NumberLineArtwork {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        String garbage = sc.nextLine();

        DoublyLinkedList colours = new DoublyLinkedList();
        DListNode[] arms = new DListNode[n];

        for (int i=0; i<n; i++){
            String init_colour = sc.next();
            colours.addBack(init_colour);
            arms[i] = colours.getTail();
        }

        for(int i=0; i<m; i++){
            int arm = sc.nextInt();
            String operation = sc.next();
            if(operation.equals("L")){
                arms[arm] = arms[arm].getPrev();
            }
            else if(operation.equals("R")){
                arms[arm] = arms[arm].getNext();
            }

            else{
                DListNode prevNode = arms[arm].getPrev();
                DListNode newNode = new DListNode(operation);
                colours.insert(prevNode, newNode);
                arms[arm] = newNode;
            }
        }

        DListNode cur = colours.getHead();

        for(int i=0; i<colours.num_nodes; i++){
            System.out.print(cur.getElement());
            if(i != colours.num_nodes-1) System.out.print(" ");
            else System.out.print("\n");
            cur = cur.getNext();
        }
        sc.close();
    }
}
