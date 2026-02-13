//Krithikh Gopalakrishnan, A0243980Y
import java.util.*;
public class MountainPeeker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        double height = sc.nextInt(); //use double to avoid floating point error

        HashMap<Integer, Integer> mountains = new HashMap<>();
                
        for (int i=0; i<count; i++){
            int curr_x = sc.nextInt();
            int curr_y = sc.nextInt();
            mountains.put(curr_x, curr_y);
        }

        int mountain_count = 0;

        double max_grad = -100000; //iterate through hashmap, for every mountain that exists we check if the gradient is higher than previous highest
        for (int x=1; x<100001; x++){
            if(mountains.get(x) != null){
                double y = mountains.get(x);
                if((y-height)/x > max_grad){
                    max_grad = (y-height)/x;
                    mountain_count ++;
                }
            }
        }

        double min_grad = 100000;
        for(int x=-1; x>-100001; x--){
            if(mountains.get(x)!=null){
                double y = mountains.get(x);
                if((y-height)/x < min_grad){
                    min_grad = (y-height)/x;
                    mountain_count ++;
                }
            }
        }
        
        System.out.println(mountain_count);
    }
}
