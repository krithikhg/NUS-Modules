//Krithikh Gopalakrishnan, A0243980Y

import java.util.*;

public class BracketMatching {
    public static void main(String[] args) {
        boolean valid = true;

        Scanner sc = new Scanner(System.in);

        int count = sc.nextInt();
        String garbage = sc.nextLine();

        Deque<Character> brackets = new LinkedList<Character>();

        String sequence = sc.nextLine();

        for (int i=0; i<count; i++){
            char nextBracket = sequence.charAt(i);
            if(nextBracket == '(' || nextBracket == '[' || nextBracket == '{'){
                brackets.push(nextBracket);
            }
            else{
                if(!brackets.isEmpty()){
                    char openBracket = brackets.peek();
                    if(!((nextBracket == ')' && openBracket == '(') || (nextBracket == ']' && openBracket == '[') || (nextBracket == '}' && openBracket == '{'))){
                        valid = false;
                        break;
                    }
                    else{
                        brackets.pop();
                    }
                }
                else{
                    valid = false;
                    break;
                }
            }
        }

        if(!valid || !brackets.isEmpty()){
            System.out.println("Invalid");
        }
        else System.out.println("Valid");

    }
}
