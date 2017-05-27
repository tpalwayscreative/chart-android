package co.tpcreative.portfolios.model;

import java.util.ArrayList;

/**
 * Created by Phong on 5/26/17.
 */

public class CObject {

    private String portfolioId ;
    private ArrayList<CPortfolios> navs ;

    public CObject(){

    }

    public CObject(String portfolioId,ArrayList<CPortfolios> navs){
        this.portfolioId = portfolioId ;
        this.navs = navs ;
    }

    public void setPortfolioId(String portfolioId){
        this.portfolioId = portfolioId ;
    }

    public String getPortfolioId(){
        return portfolioId ;
    }

    public void setNavs(ArrayList<CPortfolios>navs){
        this.navs = navs ;
    }

    public ArrayList<CPortfolios> getNavs(){
        return navs ;
    }

}
