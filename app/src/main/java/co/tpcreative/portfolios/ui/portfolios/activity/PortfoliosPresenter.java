package co.tpcreative.portfolios.ui.portfolios.activity;

import android.util.Log;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import co.tpcreative.portfolios.Constant;
import co.tpcreative.portfolios.common.presenter.Presenter;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;

/**
 * Created by Phong on 5/26/17.
 */

public class PortfoliosPresenter extends Presenter<PortfoliosView>{

    private List<CObject> list ;
    private HashMap<String,List<CPortfolios>> monthsofYear ;

    public PortfoliosPresenter(){
        list = new ArrayList<>();
        monthsofYear = new HashMap<>();
    }

    public void loadJSONFromAsset() {
        final PortfoliosView view = view();
        ArrayList<CObject> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = view.getContext().getAssets().open("data_json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            view.hideLoading();
            ex.printStackTrace();
            return ;
        }
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String portfolioId = jsonObject.getString(Constant.TAG_PORTFOLOOID);
                JSONArray subArray = jsonObject.getJSONArray(Constant.TAG_NAVS);
                ArrayList<CPortfolios> lPortfolios = new ArrayList<>();
                for (int j = 0 ; j < subArray.length() ; j++){
                    JSONObject subJsonObject = subArray.getJSONObject(j);
                    String date = subJsonObject.getString(Constant.TAG_DATE) ;
                    String amount = subJsonObject.getString(Constant.TAG_AMOUNT).toString().trim() ;
                    Log.d("action","Id : "+portfolioId + " j : " + j);
                    CPortfolios cPortfolios = new CPortfolios();
                    if (amount != null && !amount.equals("null")){
                         cPortfolios = new CPortfolios(date,amount);
                    }
                    else{
                        cPortfolios = new CPortfolios(date,"0");
                    }
                    HashMap<Integer,String> hashMap = getSpecificTime(date);
                    cPortfolios.dayOfMonths = Integer.valueOf(hashMap.get(Calendar.DAY_OF_MONTH));
                    cPortfolios.monthOfYears = Integer.valueOf(hashMap.get(Calendar.MONTH));
                    cPortfolios.years = Integer.valueOf(hashMap.get(Calendar.YEAR));
                    cPortfolios.id = portfolioId ;

                    if (cPortfolios.monthOfYears <=3){
                        cPortfolios.quarterly = 1 ;
                    }
                    else if (cPortfolios.monthOfYears <= 6){
                        cPortfolios.quarterly = 2;
                    }
                    else if(cPortfolios.monthOfYears <=9){
                        cPortfolios.quarterly = 3 ;
                    }
                    else {
                        cPortfolios.quarterly = 4;
                    }

                    lPortfolios.add(cPortfolios);
                }
                locList.add(new CObject(portfolioId,lPortfolios));
            }
        } catch (JSONException e) {
            view.hideLoading();
            e.printStackTrace();
        }
        if (locList != null){
            if (locList.size()>0){
                list = new ArrayList<>();
                list.addAll(locList);
                view.addCardSuccess(locList);
            }
        }
        view.hideLoading();
    }


    public void printTime(){
        for (CPortfolios index : getData().get(0).getNavs()){

            Log.d("action","Full date : " + index.date);
            Calendar calendar2 = Calendar.getInstance();
            DateTime date  = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            calendar2.setTime(date.parse(index.date,formatter).toDate());
            Log.d("action","Day : " + (calendar2.get(Calendar.DAY_OF_MONTH ) > 9 ? calendar2.get(Calendar.DAY_OF_MONTH) : "0"+calendar2.get(Calendar.DAY_OF_MONTH )));
            Log.d("action","Month : " + ((calendar2.get(Calendar.MONTH) + 1) > 9 ? (calendar2.get(Calendar.MONTH) + 1) : "0"+(calendar2.get(Calendar.MONTH) + 1)));
            Log.d("action","Year : " + calendar2.get(Calendar.YEAR) );
        }
    }
    

    public HashMap<Integer,String> getSpecificTime(String date){
        HashMap<Integer,String> hashMap = new HashMap<>();
        Calendar calendar2 = Calendar.getInstance();
        DateTime dates  = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        calendar2.setTime(dates.parse(date,formatter).toDate());
        hashMap.put (Calendar.DAY_OF_MONTH,(calendar2.get(Calendar.DAY_OF_MONTH ) > 9 ? calendar2.get(Calendar.DAY_OF_MONTH) : "0"+calendar2.get(Calendar.DAY_OF_MONTH )).toString());
        hashMap.put (Calendar.MONTH, ((calendar2.get(Calendar.MONTH) + 1) > 9 ? (calendar2.get(Calendar.MONTH) + 1) : "0"+(calendar2.get(Calendar.MONTH) + 1)).toString());
        hashMap.put (Calendar.YEAR, calendar2.get(Calendar.YEAR)+"");
        return hashMap ;
    }

    public void showLoading(){
        final PortfoliosView view = view();
        view.showLoading();
    }

    public void hideLoading(){
        final PortfoliosView view = view();
        view.hideLoading();
    }

    public List<CObject> getData(){
        return list ;
    }
}
