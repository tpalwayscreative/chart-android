package co.tpcreative.portfolios.ui.portfolios.activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import co.tpcreative.portfolios.Constant;
import co.tpcreative.portfolios.R;
import co.tpcreative.portfolios.common.presenter.Presenter;
import co.tpcreative.portfolios.common.utils.Utils;
import co.tpcreative.portfolios.model.CData;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PortfoliosPresenter extends Presenter<PortfoliosView>{

    private List<CObject> list ;
    private List<CObject> listMonths ;
    private List<CObject> listQ ;
    private List<CObject> listDays ;
    private BarData barData ;
    private HashMap<String,List<CPortfolios>> monthsofYear ;
    public static final String TAG = "PortfoliosPresenter";
    private PortfoliosData portfoliosData ;

    public PortfoliosPresenter(PortfoliosData portfoliosData){
        list = new ArrayList<>();
        monthsofYear = new HashMap<>();
        this.portfoliosData = portfoliosData ;
    }

    @Override
    public void bindView(@NonNull PortfoliosView view) {
        super.bindView(view);
    }

    public void loadJSONFromAsset() {
        checkViewAttached();
        String json = null;
        InputStream is  = null ;
        try {
            is = view().getContext().getAssets().open("data_json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            if (json != null) {
                Gson gson = new Gson();
                CData data = gson.fromJson(json, CData.class);
                if (data != null) {
                    int i = 0;
                    for (CObject index : data.data) {
                        for (CPortfolios sub0 : index.navs) {
                            String mount = sub0.amount ;
                            sub0.amount = mount == null ? "0" : mount ;
                            HashMap<Integer, String> hashMap = Utils.getSpecificTime(sub0.date);
                            sub0.dayOfMonths = Integer.valueOf(hashMap.get(Calendar.DAY_OF_MONTH));
                            sub0.monthOfYears = Integer.valueOf(hashMap.get(Calendar.MONTH));
                            sub0.years = Integer.valueOf(hashMap.get(Calendar.YEAR));
                            sub0.id = index.portfolioId;
                            sub0.group = i;

                            if (sub0.monthOfYears <= 3) {
                                sub0.quarterly = 1;
                            } else if (sub0.monthOfYears <= 6) {
                                sub0.quarterly = 2;
                            } else if (sub0.monthOfYears <= 9) {
                                sub0.quarterly = 3;
                            } else {
                                sub0.quarterly = 4;
                            }
                        }
                        i++;
                    }
                    list.addAll(data.data);
                    portfoliosData.setData(list);
                    view().onAddDataSuccess(list);
                }
            }
        }

        catch (IOException ex) {
            try {
                if (is != null){
                    is.close();
                }
            }
            catch (IOException ioe){
                ioe.printStackTrace();
            }
            ex.printStackTrace();
            return  ;
        }

    }

    public void showGroupOfMonths(){
        checkViewAttached();
        view().showLoading();
            ArrayList<BarEntry> group1 = new ArrayList<>();
            ArrayList<BarEntry> group2 = new ArrayList<>();
            ArrayList<BarEntry> group3 = new ArrayList<>();
            Map<Integer,BarEntry>hashMapB1 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB2 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB3 = new TreeMap<Integer, BarEntry>();
            for (int i = 0 ; i < portfoliosData.getXAxisValuesMonth().size();i++) {
                for (CObject index : portfoliosData.getData()) {
                    for (CPortfolios in : index.navs) {
                        int month = in.monthOfYears;
                        float amount = Float.valueOf(in.amount) ;
                        if (in.group == 0) {
                            if (hashMapB1.get(month) != null){
                                float previousAmount = hashMapB1.get(month).getVal();
                                hashMapB1.get(month).setVal(previousAmount + amount);
                            }
                            else{
                                hashMapB1.put(month,new BarEntry(amount, month));
                            }

                        } else if (in.group == 1 ) {
                            if (hashMapB2.get(month) != null){
                                float previousAmount = hashMapB2.get(month).getVal();
                                hashMapB2.get(month).setVal(previousAmount + amount);
                            }
                            else{
                                hashMapB2.put(month,new BarEntry(amount, month));
                            }
                        } else {
                            if (hashMapB3.get(month) != null){
                                float previousAmount = hashMapB3.get(month).getVal();
                                hashMapB3.get(month).setVal(previousAmount + amount);
                            }
                            else{
                                hashMapB3.put(month,new BarEntry(amount, month));
                            }
                        }
                    }
                }
            }

            for (Map.Entry<Integer,BarEntry> bar : hashMapB1.entrySet()){
                group1.add(bar.getValue());
            }
            for (Map.Entry<Integer,BarEntry> bar : hashMapB2.entrySet()){
                group2.add(bar.getValue());
            }
            for (Map.Entry<Integer,BarEntry> bar : hashMapB3.entrySet()){
                group3.add(bar.getValue());
            }

            BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
            //barDataSet1.setColor(Color.rgb(0, 155, 0));
            barDataSet1.setColor(ColorTemplate.JOYFUL_COLORS[0]);

            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColor(ColorTemplate.JOYFUL_COLORS[1]);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 3");
            barDataSet3.setColor(ColorTemplate.JOYFUL_COLORS[2]);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);

            portfoliosData.setBarData(new BarData(portfoliosData.getXAxisValuesMonth(), dataSets));
            view().onGetStatus(1);
            view().hideLoading();
            view().onUpdatedChartBar(portfoliosData.getBarData());

    }

    public void addMonth(){
        listMonths = new ArrayList<>();
       for (int i = 0 ; i < portfoliosData.getXAxisValuesMonth().size();i++){
           listMonths.add(new CObject(portfoliosData.getXAxisValuesMonth().get(i)));
       }
       view().onAddMonth(listMonths);
    }

    public void addDays(){
        listDays = new ArrayList<>();
        for (int i = 0 ; i < portfoliosData.getXAxisValuesOfDay().size();i++){
            listDays.add(new CObject(portfoliosData.getXAxisValuesOfDay().get(i)));
        }
        view().onAddDays(listDays);
    }

    public void addQuarterly(){
        listQ = new ArrayList<>();
        for (int i = 0 ; i < portfoliosData.getXAxisValuesOfQuarterly().size();i++){
            listQ.add(new CObject(portfoliosData.getXAxisValuesOfQuarterly().get(i)));
        }
        view().onAddQuarterly(listQ);
    }

    public void showGroupOfDays(int months){
        checkViewAttached();
        view().showLoading();
            ArrayList<BarEntry> group1 = new ArrayList<>();
            ArrayList<BarEntry> group2 = new ArrayList<>();
            ArrayList<BarEntry> group3 = new ArrayList<>();
            Map<Integer,BarEntry>hashMapB1 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB2 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB3 = new TreeMap<Integer, BarEntry>();
            for (int i = 0 ; i < portfoliosData.getXAxisValuesOfDay().size();i++) {
                for (CObject index : portfoliosData.getData()) {
                    for (CPortfolios in : index.navs) {
                        int month = in.monthOfYears;
                        int day = in.dayOfMonths;
                        float amount = Float.valueOf(in.amount) ;
                        if (month == months) {
                            if (in.group == 0) {
                                hashMapB1.put(day, new BarEntry(amount, day));
                            } else if (in.group == 1) {
                                hashMapB2.put(day, new BarEntry(amount, day));
                            } else if (in.group == 2)  {
                                hashMapB3.put(day, new BarEntry(amount, day));
                            }
                        }

                    }
                }
            }

            for (Map.Entry<Integer,BarEntry> bar : hashMapB1.entrySet()){
                group1.add(bar.getValue());
            }
            for (Map.Entry<Integer,BarEntry> bar : hashMapB2.entrySet()){
                group2.add(bar.getValue());
            }
            for (Map.Entry<Integer,BarEntry> bar : hashMapB3.entrySet()){
                group3.add(bar.getValue());
            }

            BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
            //barDataSet1.setColor(Color.rgb(0, 155, 0));
            barDataSet1.setColor(ColorTemplate.COLORFUL_COLORS[0]);


            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColor(ColorTemplate.COLORFUL_COLORS[1]);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 3");
            barDataSet3.setColor(ColorTemplate.COLORFUL_COLORS[2]);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);

            portfoliosData.setBarData(new BarData(portfoliosData.getXAxisValuesOfDay(), dataSets));
            view().onGetStatus(2);
            view().hideLoading();
            view().onUpdatedChartBar(portfoliosData.getBarData());

    }

    public void showGroupOfquarterly(){
        checkViewAttached();
        view().showLoading();
            ArrayList<BarEntry> group1 = new ArrayList<>();
            ArrayList<BarEntry> group2 = new ArrayList<>();
            ArrayList<BarEntry> group3 = new ArrayList<>();
            Map<Integer,BarEntry>hashMapB1 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB2 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB3 = new TreeMap<Integer, BarEntry>();
            for (int i = 0 ; i < portfoliosData.getXAxisValuesOfQuarterly().size();i++) {
                for (CObject index : portfoliosData.getData()) {
                    for (CPortfolios in : index.navs) {
                        int month = in.monthOfYears;
                        float amount = Float.valueOf(in.amount) ;
                        int  quarterly = in.quarterly ;
                        if (in.group == 0) {

                            if (month >= 0 && month < 3) {
                                if (hashMapB1.get(0) != null){
                                    float previousAmount = hashMapB1.get(0).getVal();
                                    hashMapB1.get(0).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB1.put(0,new BarEntry(amount, 0));
                                }
                            }
                            else if(month >= 3 && month < 6){
                                if (hashMapB1.get(1) != null){
                                    float previousAmount = hashMapB1.get(1).getVal();
                                    hashMapB1.get(1).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB1.put(1,new BarEntry(amount, 1));
                                }
                            }
                            else if(month >= 6 && month < 9){
                                if (hashMapB1.get(2) != null){
                                    float previousAmount = hashMapB1.get(2).getVal();
                                    hashMapB1.get(2).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB1.put(2,new BarEntry(amount, 2));
                                }
                            }
                            else{
                                if (hashMapB1.get(3) != null){
                                    float previousAmount = hashMapB1.get(3).getVal();
                                    hashMapB1.get(3).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB1.put(3,new BarEntry(amount, 3));
                                }
                            }

                        } else if (in.group == 1 ) {
                            if (month >= 0 && month < 3) {
                                if (hashMapB2.get(0) != null){
                                    float previousAmount = hashMapB2.get(0).getVal();
                                    hashMapB2.get(0).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB2.put(0,new BarEntry(amount, 0));
                                }
                            }
                            else if(month >= 3 && month < 6){
                                if (hashMapB2.get(1) != null){
                                    float previousAmount = hashMapB2.get(1).getVal();
                                    hashMapB2.get(1).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB2.put(1,new BarEntry(amount, 1));
                                }
                            }
                            else if(month >= 6 && month < 9){
                                if (hashMapB2.get(2) != null){
                                    float previousAmount = hashMapB2.get(2).getVal();
                                    hashMapB2.get(2).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB2.put(2,new BarEntry(amount, 2));
                                }
                            }
                            else{
                                if (hashMapB2.get(3) != null){
                                    float previousAmount = hashMapB2.get(3).getVal();
                                    hashMapB2.get(3).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB2.put(3,new BarEntry(amount, 3));
                                }
                            }
                        } else {
                            if (month >= 0 && month < 3) {
                                if (hashMapB3.get(0) != null){
                                    float previousAmount = hashMapB3.get(0).getVal();
                                    hashMapB3.get(0).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB3.put(0,new BarEntry(amount, 0));
                                }
                            }
                            else if(month >= 3 && month < 6){
                                if (hashMapB3.get(1) != null){
                                    float previousAmount = hashMapB3.get(1).getVal();
                                    hashMapB3.get(1).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB3.put(1,new BarEntry(amount, 1));
                                }
                            }
                            else if(month >= 6 && month < 9){
                                if (hashMapB3.get(2) != null){
                                    float previousAmount = hashMapB3.get(2).getVal();
                                    hashMapB3.get(2).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB3.put(2,new BarEntry(amount, 2));
                                }
                            }
                            else{
                                if (hashMapB3.get(3) != null){
                                    float previousAmount = hashMapB3.get(3).getVal();
                                    hashMapB3.get(3).setVal(previousAmount + amount);
                                }
                                else{
                                    hashMapB3.put(3,new BarEntry(amount, 3));
                                }
                            }
                        }
                    }
                }
            }

            for (Map.Entry<Integer,BarEntry> bar : hashMapB1.entrySet()){
                group1.add(bar.getValue());
            }
            for (Map.Entry<Integer,BarEntry> bar : hashMapB2.entrySet()){
                group2.add(bar.getValue());
            }
            for (Map.Entry<Integer,BarEntry> bar : hashMapB3.entrySet()){
                group3.add(bar.getValue());
            }

            BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
            //barDataSet1.setColor(Color.rgb(0, 155, 0));
            barDataSet1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);

            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 3");
            barDataSet3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);
            portfoliosData.setBarData(new BarData(portfoliosData.getXAxisValuesOfQuarterly(), dataSets));
            view().onGetStatus(3);
            view().hideLoading();
            view().onUpdatedChartBar(portfoliosData.getBarData());

    }


    public void onLoadingData(){
        checkViewAttached();
        list= portfoliosData.getData();
        if (list != null) {
            int i = 0;
            for (CObject index : list) {
                for (CPortfolios sub0 : index.navs) {
                    String mount = sub0.amount ;
                    sub0.amount = mount == null ? "0" : mount ;
                    HashMap<Integer, String> hashMap = Utils.getSpecificTime(sub0.date);
                    sub0.dayOfMonths = Integer.valueOf(hashMap.get(Calendar.DAY_OF_MONTH));
                    sub0.monthOfYears = Integer.valueOf(hashMap.get(Calendar.MONTH));
                    sub0.years = Integer.valueOf(hashMap.get(Calendar.YEAR));
                    sub0.id = index.portfolioId;
                    sub0.group = i;

                    if (sub0.monthOfYears <= 3) {
                        sub0.quarterly = 1;
                    } else if (sub0.monthOfYears <= 6) {
                        sub0.quarterly = 2;
                    } else if (sub0.monthOfYears <= 9) {
                        sub0.quarterly = 3;
                    } else {
                        sub0.quarterly = 4;
                    }
                }
                i++;
            }
            view().onAddDataSuccess(list);
        }
    }
}
