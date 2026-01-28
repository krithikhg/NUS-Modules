import java.util.*;

public class NumberFun{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        for (int i=0; i<count; i++){
            float first = sc.nextInt();
            float second = sc.nextInt();
            float third = sc.nextInt();
            System.out.println(Arithmetic(first,second,third));
        }
    }

    static String Arithmetic(float first, float second, float third){
        if(first + second == third){
            return "Possible";
        }

        else if(first*second == third){
            return "Possible";
        }

        else if(first-second == third || second-first == third){
            return "Possible";
        }

        else if(first/second == third || second/first == third){
            return "Possible";
        }

        else{
            return "Impossible";
        }
    }
}