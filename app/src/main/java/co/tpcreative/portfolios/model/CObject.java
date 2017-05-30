package co.tpcreative.portfolios.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

import co.tpcreative.portfolios.R;

@IgnoreExtraProperties
public class CObject {

    public String portfolioId ;
    public List<CPortfolios> navs ;

    public CObject(){

    }
    public CObject(String portfolioId){
        this.portfolioId = portfolioId ;
    }

    public CObject(String portfolioId,List<CPortfolios> navs){
        this.portfolioId = portfolioId ;
        this.navs = navs ;
    }


}
