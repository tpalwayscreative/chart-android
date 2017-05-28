package co.tpcreative.portfolios.ui.portfolios.activity;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import co.tpcreative.portfolios.common.presenter.Presenter;
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
                    cPortfolios.group = i ;

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
        view.hideLoading();
        if (locList != null){
            if (locList.size()>0){
                list = new ArrayList<>();
                list.addAll(locList);
                view.onAddDataSuccess(locList,getXAxisValues());
                view.onSaveDataToFirebase(list);

            }
        }
    }


    public void printTime(){
        for (CPortfolios index : getData().get(0).navs){
            Calendar calendar2 = Calendar.getInstance();
            DateTime date  = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            calendar2.setTime(date.parse(index.date,formatter).toDate());
        }
    }


    public HashMap<Integer,String> getSpecificTime(String date){
        HashMap<Integer,String> hashMap = new HashMap<>();
        Calendar calendar2 = Calendar.getInstance();
        DateTime dates  = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        calendar2.setTime(dates.parse(date,formatter).toDate());
        hashMap.put (Calendar.DAY_OF_MONTH,(calendar2.get(Calendar.DAY_OF_MONTH ) > 9 ? calendar2.get(Calendar.DAY_OF_MONTH) : "0"+calendar2.get(Calendar.DAY_OF_MONTH )).toString());
       // hashMap.put (Calendar.MONTH, ((calendar2.get(Calendar.MONTH) + 1) > 9 ? (calendar2.get(Calendar.MONTH) + 1) : "0"+(calendar2.get(Calendar.MONTH) + 1)).toString());
        hashMap.put (Calendar.MONTH, calendar2.get(Calendar.MONTH)+"");
        hashMap.put (Calendar.YEAR, calendar2.get(Calendar.YEAR)+"");
        return hashMap ;
    }

    public void showDataGroup(){
        Observable.create(subscriber -> {
            ArrayList<BarEntry> group1 = new ArrayList<>();
            group1.add(new BarEntry(4f, 0));
            group1.add(new BarEntry(8f, 1));
            group1.add(new BarEntry(6f, 2));
            group1.add(new BarEntry(12f, 3));
            group1.add(new BarEntry(18f, 4));
            group1.add(new BarEntry(9f, 5));
            group1.add(new BarEntry(11f, 6));
            group1.add(new BarEntry(12f, 7));
            group1.add(new BarEntry(13f, 8));
            group1.add(new BarEntry(14f, 9));
            group1.add(new BarEntry(15f, 10));
            group1.add(new BarEntry(16f, 11));


            ArrayList<BarEntry> group2 = new ArrayList<>();
            group2.add(new BarEntry(6f, 0));
            group2.add(new BarEntry(7f, 1));
            group2.add(new BarEntry(8f, 2));
            group2.add(new BarEntry(12f, 3));
            group2.add(new BarEntry(15f, 4));
            group2.add(new BarEntry(10f, 5));
            group2.add(new BarEntry(13f, 6));
            group2.add(new BarEntry(14f, 7));
            group2.add(new BarEntry(11f, 8));
            group2.add(new BarEntry(16f, 9));
            group2.add(new BarEntry(18f, 10));
            group2.add(new BarEntry(20f, 11));


            ArrayList<BarEntry> group3 = new ArrayList<>();
            group3.add(new BarEntry(7f, 0));
            group3.add(new BarEntry(8f, 1));
            group3.add(new BarEntry(9f, 2));
            group3.add(new BarEntry(13f, 3));
            group3.add(new BarEntry(16f, 4));
            group3.add(new BarEntry(11f, 5));
            group3.add(new BarEntry(10f, 6));
            group3.add(new BarEntry(18f, 7));
            group3.add(new BarEntry(17f, 8));
            group3.add(new BarEntry(16f, 9));
            group3.add(new BarEntry(12f, 10));
            group3.add(new BarEntry(15f, 11));


            BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
            //barDataSet1.setColor(Color.rgb(0, 155, 0));
            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 2");
            barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);
            BarData data = new BarData(getXAxisValues(), dataSets);
            subscriber.onNext(data);

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(data ->{
                    view().onUpdatedChartBar((BarData) data);
                });
    }


    public void showGroupOfMonths(){
        final PortfoliosView view = view();
        view.showLoading();
        Observable.create(subscriber -> {
            ArrayList<BarEntry> group1 = new ArrayList<>();
            ArrayList<BarEntry> group2 = new ArrayList<>();
            ArrayList<BarEntry> group3 = new ArrayList<>();
            Map<Integer,BarEntry>hashMapB1 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB2 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB3 = new TreeMap<Integer, BarEntry>();
            for (int i = 0 ; i < getXAxisValues().size();i++) {
                for (CObject index : list) {
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
            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 3");
            barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);

            BarData data = new BarData(getXAxisValues(), dataSets);
            subscriber.onNext(data);
            subscriber.onCompleted();
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.computation())
        .subscribe(data ->{
            view.onGetStatus(1);
            view.hideLoading();
            view.onUpdatedChartBar((BarData)data);
        });
    }

    public void addMonth(){
        listMonths = new ArrayList<>();
       for (int i = 0 ; i < getXAxisValues().size();i++){
           listMonths.add(new CObject(getXAxisValues().get(i),i));
       }
       view().onAddMonth(listMonths);
    }

    public void addDays(){
        listDays = new ArrayList<>();
        for (int i = 0 ; i < getXAxisValuesOfDay().size();i++){
            listDays.add(new CObject(getXAxisValuesOfDay().get(i),i));
        }
        view().onAddDays(listDays);
    }

    public void addQuarterly(){
        listQ = new ArrayList<>();
        for (int i = 0 ; i < getXAxisValuesOfQuarterly().size();i++){
            listQ.add(new CObject(getXAxisValuesOfQuarterly().get(i),i));
        }
        view().onAddQuarterly(listQ);
    }


    public void showGroupOfDays(int months){
        final PortfoliosView view = view();
        view.showLoading();
        Observable.create(subscriber -> {
            ArrayList<BarEntry> group1 = new ArrayList<>();
            ArrayList<BarEntry> group2 = new ArrayList<>();
            ArrayList<BarEntry> group3 = new ArrayList<>();
            Map<Integer,BarEntry>hashMapB1 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB2 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB3 = new TreeMap<Integer, BarEntry>();
            for (int i = 0 ; i < getXAxisValuesOfDay().size();i++) {
                for (CObject index : list) {
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
            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 3");
            barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);

            BarData data = new BarData(getXAxisValuesOfDay(), dataSets);
            subscriber.onNext(data);
            subscriber.onCompleted();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(data ->{
                    view.onGetStatus(2);
                    view.hideLoading();
                    view.onUpdatedChartBar((BarData)data);
                });
    }



    public void showGroupOfquarterly(){
        final PortfoliosView view = view();
        view.showLoading();
        Observable.create(subscriber -> {
            ArrayList<BarEntry> group1 = new ArrayList<>();
            ArrayList<BarEntry> group2 = new ArrayList<>();
            ArrayList<BarEntry> group3 = new ArrayList<>();
            Map<Integer,BarEntry>hashMapB1 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB2 = new TreeMap<Integer, BarEntry>();
            Map<Integer,BarEntry>hashMapB3 = new TreeMap<Integer, BarEntry>();
            for (int i = 0 ; i < getXAxisValuesOfQuarterly().size();i++) {
                for (CObject index : list) {
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
            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

            BarDataSet barDataSet3 = new BarDataSet(group3, "Group 3");
            barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);

            BarData data = new BarData(getXAxisValuesOfQuarterly(), dataSets);
            subscriber.onNext(data);
            subscriber.onCompleted();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(data ->{
                    view.onGetStatus(3);
                    view.hideLoading();
                    view.onUpdatedChartBar((BarData)data);
                });
    }



    public List<String> getXAxisValues() {
        String[] mMonths = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        return Arrays.asList(mMonths);
    }

    public List<String> getXAxisValuesOfDay(){
        String[] mDays = new String[]{
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24","25", "26", "27", "28","29", "30", "31"
        };
        return Arrays.asList(mDays);
    }

    public List<String> getXAxisValuesOfQuarterly(){
        String[] mDays = new String[]{
                "Mar", "Jun", "Sep", "Dec"
        };
        return Arrays.asList(mDays);
    }


    public List<CObject> getData(){
        return list ;
    }
}
