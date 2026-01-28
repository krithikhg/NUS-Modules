import java.util.*;

public class LessonTwo {
    static final int MAX_BAN = 5;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int points = sc.nextInt();
        List<Player> inputs = new ArrayList<>();
        for (int i = 0; i < points; i++){
            int playerPoints = sc.nextInt();
            String playerName = sc.next();
            inputs.add(new Player(playerPoints, playerName));
        }
        
        int banned[] = new int[5];
        for (int i = 0; i<MAX_BAN; i++){
            int in = sc.nextInt();
            if (in == -1){
                break;
            }
            banned[i] = in;
        }

        for (int i=0; i<inputs.size();i++){
            System.out.println(inputs.get(i));
        }

        for (int i=0; i<inputs.size();i++){
            for (int num : banned){
                if (inputs.get(i).getJerseyNumber() == num){
                    System.out.println(inputs.get(i) + " will be affected by the ban.");
                }
            }
        }
        sc.close();
    }
}
