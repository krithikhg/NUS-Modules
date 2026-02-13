import java.util.*;
public class MountainPeeker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int height = sc.nextInt();

        HashMap<Integer, Integer> mountains = new HashMap<>();
        Integer[] x_filled = new Integer[2*100000 + 1];
        
        for (int i=0; i<count; i++){
            int curr_x = sc.nextInt();
            int curr_y = sc.nextInt();
            mountains.put(curr_x, curr_y);
            x_filled[curr_x + 100000] = 1;
        }

        
    }
}
