package co.tpcreative.portfolios.model;


import java.util.Arrays;
import java.util.List;

import co.tpcreative.portfolios.R;

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
