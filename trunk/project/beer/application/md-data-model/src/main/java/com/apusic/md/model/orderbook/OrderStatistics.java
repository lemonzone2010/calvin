package com.apusic.md.model.orderbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatistics {

    private long totalOrder = 0;

    private Map<OrderState, Long> statisticsMap = new HashMap<OrderState, Long>();

    public OrderStatistics() {
        initMap();
    }

    public long getTotalOrder() {
        for (Long count : statisticsMap.values()) {
            totalOrder += count.longValue();
        }
        return totalOrder;
    }

    public Map<OrderState, Long> getStatisticsMap() {
        return statisticsMap;
    }

    public void addMap(OrderState key, Long value) {
        this.statisticsMap.put(key, value);
    }

    private void initMap() {
        if (statisticsMap.size() > 0) {
            return;
        }
        List<OrderState> states = OrderState.getList();
        for (OrderState state : states) {
            statisticsMap.put(state, 0L);
        }
    }
}
