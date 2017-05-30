package co.tpcreative.portfolios.model;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Arrays;
import java.util.List;

import co.tpcreative.portfolios.R;
@IgnoreExtraProperties
public class CPortfolios extends CDate {

    public String date ;
    public String amount ;

    public CPortfolios(){

    }

    public CPortfolios(String date,String amount){
        this.date = date ;
        this.amount = amount ;
    }

}
