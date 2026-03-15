import java.util.*;

public class StockPrices {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int count=0;count<cases;count++){
            int num_orders = sc.nextInt();
            PriorityQueue<Order> asks = new PriorityQueue<Order>();
            PriorityQueue<Order> bids = new PriorityQueue<Order>(Comparator.reverseOrder());
            int askPrice;
            int stockPrice = 0;
            int bidPrice;
            for (int i=0; i<num_orders; i++){
                //Process order
                String orderType = sc.next();
                int order_number = sc.nextInt();
                String garbage = sc.next();
                garbage = sc.next();
                int order_price = sc.nextInt();
                Order order = new Order(orderType, order_number, order_price);

                if(order.buy){
                    int numStocks = order.number;
                    if(!asks.isEmpty()){
                        askPrice = asks.peek().price;
                    }
                    else{
                        askPrice = Integer.MAX_VALUE;
                    }

                    if(askPrice <= order.price){
                        while(!asks.isEmpty() && asks.peek().price <= order.price && numStocks > 0){
                            if(asks.peek().number > numStocks){
                                Order ask = asks.poll();
                                ask.number = ask.number - numStocks;
                                numStocks = 0;
                                asks.add(ask);
                                stockPrice = ask.price;
                            }
                            else{
                                Order ask = asks.poll();
                                numStocks -= ask.number;
                                stockPrice = ask.price;
                            }
                        }
                        if(numStocks > 0){
                            order.number = numStocks;
                            bids.add(order);
                        }
                    }
                    else{
                        bids.add(order);
                    } 
                }

                else{
                    int numStocks = order.number;
                    if(!bids.isEmpty()){
                        bidPrice = bids.peek().price;
                    }
                    else{
                        bidPrice = Integer.MIN_VALUE;
                    }
                    if(bidPrice >= order.price){
                        while(!bids.isEmpty() && bids.peek().price >= order.price && numStocks > 0){
                            if(bids.peek().number > numStocks){
                                Order bid = bids.poll();
                                bid.number -= numStocks;
                                numStocks = 0;
                                bids.add(bid);
                                stockPrice = order.price;
                            }
                            else{
                                Order bid = bids.poll();
                                numStocks -= bid.number;
                                stockPrice = order.price;
                            }
                        }
                        if(numStocks > 0){
                            order.number = numStocks;
                            asks.add(order);
                        }
                    }
                    else{
                        asks.add(order);
                    }
                }

                String askPrint;
                String bidPrint;
                String stockPrint;

                if(asks.peek() == null){
                    askPrint = "-";
                }
                else{
                    askPrint = Integer.toString(asks.peek().price);
                }

                if(bids.peek() == null){
                    bidPrint = "-";
                }
                else{
                    bidPrint = Integer.toString(bids.peek().price);
                }

                if(stockPrice == 0){
                    stockPrint = "-";
                }
                else{
                    stockPrint = Integer.toString(stockPrice);
                }

                System.out.println(askPrint + " " + bidPrint + " " + stockPrint);
            }
        }
    }
}
