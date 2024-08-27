package org.afob.limit;

import java.math.BigDecimal;

import org.afob.execution.ExecutionClient;
import org.afob.execution.ExecutionClient.ExecutionException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LimitOrderAgentTest {


    
    private ExecutionClient executionClient;
    private LimitOrderAgent limitOrderAgent;

    @Before
    public void initialize() {
    	
        executionClient = new ExecutionClient();
        limitOrderAgent = new LimitOrderAgent(executionClient);
    }

    @Test
    public void whenPriceBelow100Test() throws ExecutionClient.ExecutionException {
        limitOrderAgent.addOrder(true, "IBM", 1000, new BigDecimal("100"));
        // Price tick that should trigger execution
        limitOrderAgent.priceTick("IBM", new BigDecimal(95.50));

        // Verify that the order was executed
        executionClient.buy(anyString(), anyInt());
    }


    @Test
    public void whenPriceAbove100Test() throws ExecutionClient.ExecutionException {
        limitOrderAgent.addOrder(true, "IBM", 1000, new BigDecimal("100"));
        // Price tick that should not trigger execution
        limitOrderAgent.priceTick("IBM", new BigDecimal(101.50));

        // Verify that the order was not executed
        executionClient.buy(anyString(), anyInt());
    }
    
    @Test
    public void testMultipleOrdersExecution() throws ExecutionException {
        // Add multiple orders
    	limitOrderAgent.addOrder(true, "IBM", 1000, new BigDecimal("100.00"));
    	limitOrderAgent.addOrder(false, "AAPL", 500, new BigDecimal("150.00"));

        // Simulate price ticks that should trigger both orders
    	limitOrderAgent.priceTick("IBM", new BigDecimal("99.00"));
    	limitOrderAgent.priceTick("AAPL", new BigDecimal("151.00"));

        // Verify both orders were executed
        verify(executionClient, times(1)).buy("IBM", 1000);
        verify(executionClient, times(1)).buy("AAPL", 500);
    }
}