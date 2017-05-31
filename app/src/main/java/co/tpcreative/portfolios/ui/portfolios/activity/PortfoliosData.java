package co.tpcreative.portfolios.ui.portfolios.activity;
import com.github.mikephil.charting.data.BarData;
import java.util.Arrays;
import java.util.List;
import co.tpcreative.portfolios.model.CObject;


public class PortfoliosData {
    private List<CObject> listData ;
    private BarData barData ;
    public List<String> getXAxisValuesMonth() {
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
        return listData ;
    }
    public void setData(List<CObject>listData){
        this.listData = listData ;
    }
    public BarData getBarData(){
        return barData;
    }
    public void setBarData(BarData barData){
        this.barData = barData ;
    }

}
