package co.tpcreative.portfolios.model;


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
