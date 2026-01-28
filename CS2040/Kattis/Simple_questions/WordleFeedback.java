import java.util.*;

public class WordleFeedback {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String secret = sc.next();
        String guess = sc.next();
        char[] outputs = new char[guess.length()];
        String output = "";
        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i=0; i<guess.length(); i++){
            char guessChar = guess.charAt(i);
            // check for green letter
            if (guessChar == secret.charAt(i)){
                outputs[i] = 'G';
                charCounts.merge(guessChar, 1, (oldValue, newValue) -> oldValue + newValue);
            }
        }
        for (int i=0; i<guess.length(); i++){
            char guessChar = guess.charAt(i);
            //check for yellow letter
            if(secret.indexOf(guessChar) != -1 && outputs[i] != 'G'){
                charCounts.merge(guessChar, 1, (oldValue, newValue) -> oldValue + newValue);
                if(charCounts.get(guessChar) <= inString(guessChar, secret)){
                    outputs[i] = 'Y';
                }
                else{
                    outputs[i] = '-';
                }
            }
            else if(outputs[i] != 'G'){
                outputs[i] = '-';
            }
        }
        System.out.println(outputs);
    }

    static int inString(char c, String secret){
        int count = 0;
        for (int i=0; i<secret.length(); i++){
            if(c == secret.charAt(i)){
                count ++;
            }
        }
        return count;
    }
}
