public class Player {
    int points;
    String name;

    Player(int points, String name){
        this.points = points;
        this.name = name;
    }

    public static int digSum(int x){
        if (x < 10){
            return x;
        }
        else{
            return digSum(x/10) + x%10;
        }
    }

    public int summer(int x){
        int sum = digSum(x);
        if (sum <= 50){
            return sum;
        }
        else{
            return summer(sum);
        }
    }

    public int getJerseyNumber(){
        return this.summer(this.points);
    }

    public String toString(){
        return this.name + "[" + this.summer(this.points) + "]";
    }
}
