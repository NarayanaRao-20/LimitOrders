package org.afob.limit;

import java.math.BigDecimal;

public class Order {
    private boolean buy;
	private String productId;
    private int quantity;
    private BigDecimal limit;

    public Order(boolean buy, String productId, int quantity, BigDecimal limit) {
        this.buy = buy;
        this.productId = productId;
        this.quantity = quantity;
        this.limit = limit;
    }
    
    public boolean isBuy() {
		return buy;
	}

	public void setBuy(boolean buy) {
		this.buy = buy;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
    
    
    
}