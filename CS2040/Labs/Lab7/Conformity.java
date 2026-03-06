import java.util.*;

public class Conformity {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int froshs = sc.nextInt();
        HashMap<Set<Integer>, Integer> sets = new HashMap<>();

        int max_freq = 0;
        int curr_count = 1;
        int curr_freq = 1;
        for (int i=0; i<froshs; i++){
            HashSet<Integer> curr_set = new HashSet<>();
            for (int j=0; j<5; j++){
                int next = sc.nextInt();
                curr_set.add(next);
            }
            if(!sets.containsKey(curr_set)){
                sets.put(curr_set, 1);
                curr_freq = 1;
            }
            else{
                curr_freq = sets.get(curr_set);
                curr_freq += 1;
                sets.put(curr_set, curr_freq);
            }
            if(curr_freq == max_freq){
                curr_count += curr_freq;
            }
            else if(curr_freq > max_freq){
                max_freq = curr_freq;
                curr_count = max_freq;
            }
        }
        System.out.println(curr_count);
    }   
}
