package co.tpcreative.portfolios.model;

/**
 * Created by Phong on 5/26/17.
 */

public class CPortfolios  {

    public String date ;
    public String amount ;
    public int dayOfMonths ;
    public int monthOfYears ;
    public int years ;
    public int quarterly ;
    public String id ;
    public int group ;

    public CPortfolios(){

    }

    public CPortfolios(String date,String amount){
        this.date = date ;
        this.amount = amount ;
    }

}
