package com.apusic.md.model.orderbook;

public interface TotalPriceStrategy {
	double getTotalPrice(Order order);
}
