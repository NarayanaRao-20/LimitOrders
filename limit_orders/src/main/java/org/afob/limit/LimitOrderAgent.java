package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LimitOrderAgent implements PriceListener {
	
	private final  ExecutionClient ec ;
	private final List<Order> orders = new ArrayList<>();

    public LimitOrderAgent(final ExecutionClient ec) {
    	this.ec = ec;
    }


    @Override
    public void priceTick(String productId, BigDecimal price) {
    	Iterator<Order> iterator = orders.iterator();
    	while (iterator.hasNext()) {
    		Order order = iterator.next();
    		if (order.getProductId().equals(productId)) {
    		try{
    				if (order.isBuy() && price.compareTo(order.getLimit()) <= 0) {
    					ec.buy(order.getProductId(), order.getQuantity());
    					// Removing the order after execution
    					iterator.remove(); 
    				} else if (!order.isBuy() && price.compareTo(order.getLimit()) >= 0) {
    					ec.sell(order.getProductId(), order.getQuantity());
    					// Removing the order after execution
    					iterator.remove(); 
    				}
    			}  catch (ExecutionClient.ExecutionException e) {
    				System.err.println("Failed to execute order: " + order + ": " + e.getMessage());
    			}
    		}
    	}
    }

    public void addOrder(boolean buy, String productId, int quantity, BigDecimal limit) {
    	Order order = new Order(buy, productId, quantity, limit);
    	orders.add(order);
    }
    
    

}
