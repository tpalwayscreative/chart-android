package co.tpcreative.portfolios.model;

import java.util.ArrayList;

import co.tpcreative.portfolios.R;


public class CObject {

    public String portfolioId ;
    public ArrayList<CPortfolios> navs ;
    public int position ;

    public CObject(){

    }
    public CObject(String portfolioId,int position){
        this.portfolioId = portfolioId ;
        this.position = position ;
    }

    public CObject(String portfolioId,ArrayList<CPortfolios> navs){
        this.portfolioId = portfolioId ;
        this.navs = navs ;
    }



}
