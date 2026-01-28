import java.util.*;
public class Pot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        List<Integer> inputs = new ArrayList<>();
        for (int i=0; i<count; i++){
            inputs.add(sc.nextInt());
        }
        int answer = 0;
        for (int i=0; i<inputs.size(); i++){
            answer += Math.pow((inputs.get(i) / 10),(inputs.get(i) % 10));
        }
        System.out.println(answer);
    }
}
