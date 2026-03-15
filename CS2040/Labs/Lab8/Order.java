public class Order implements Comparable<Order>{
    public boolean buy;
    public int number;
    public int price;

    public Order(String orderType,int order_number, int order_price){
        if(orderType.equals("buy")){
            buy = true;
        }
        else{
            buy = false;
        }
        number = order_number;
        price = order_price;
    }

    public int compareTo(Order other){
        return this.price-other.price;
    }
}
