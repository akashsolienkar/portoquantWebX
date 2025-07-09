package com.example.portoquant.assets;

import java.util.ArrayList;
import java.util.List;

import com.example.portoquant.analyticalmodels.TimeVaryingVolatility;
import com.example.portoquant.garchmodel.GarchModel;
import com.example.portoquant.historicaldata.StockDataService;

/**
 * Represents a stock asset within the portfolio.
 * <p>
 * Extends the abstract {@link Asset} class, specialized for stocks.
 * </p>
 * <p>
 * Uses GARCH model to estimate volatility based on historical price data.
 * </p>
 * 
 * @author akashsolienkar
 */
public class Stocks extends Asset {

    private ArrayList<String> stocks;

    /**
     * Constructs a Stocks asset with the specified total price.
     * Initializes asset type as STOCK.
     * 
     * @param price the total price of the stock asset
     */
    public Stocks(double price,ArrayList<String> stocks) {
        super(Asset.AssetType.STOCK.toString(), Asset.AssetType.STOCK);
        this.setTotalPrice(price);
        this.stocks = stocks;
    }

    /**
     * Calculates the expected return for this stock asset.
     * <p>
     * Implementation pending.
     * </p>
     */
    @Override
    public void calculateExpectedReturn() {
        // TODO Auto-generated method stub
    }

    /**
     * Calculates the volatility for this stock asset using GARCH model.
     * <p>
     * Fetches historical prices and computes time-varying volatility.
     * </p>
     */
    @Override
    public void calculateVolatility() {
        StockDataService service = new StockDataService();
        TimeVaryingVolatility volatility;
        try {
        	for(String stock:this.stocks) {
            List<Double> prices = service.fetchDailyPrices(stock, "s");
            volatility= new TimeVaryingVolatility(GarchModel.getInstance().runGarch(prices));
            this.setVolatility(volatility);
            //need to add rest of cal
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }

      }

}
