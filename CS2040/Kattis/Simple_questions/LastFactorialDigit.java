import java.util.*;

public class LastFactorialDigit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        List<Integer> inputs = new ArrayList<>();
        for (int i=0; i<count; i++){
            inputs.add(sc.nextInt());
        }
        for (int i=0; i<inputs.size(); i++){
            System.out.println(lastDigit(inputs.get(i)));
        }
    }

    static int lastDigit(int number){
        int answer = 1;
        if(number>=5){
            return 0;
        }
        else{
            for (int i=0; i<number; i++){
                answer *= i+1;
            }
            return answer%10;
        }
    }
}
