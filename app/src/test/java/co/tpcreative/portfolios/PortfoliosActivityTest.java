package co.tpcreative.portfolios;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosData;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosPresenter;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class PortfoliosActivityTest {

    @Mock
    private PortfoliosView view;
    @Mock
    private PortfoliosData portfoliosData ;
    private PortfoliosPresenter presenter ;


    @Before
    public void setUp() throws Exception {
        presenter = new PortfoliosPresenter(portfoliosData);
        presenter.bindView(view);
    }

    @Test
    public void testMonths() throws Exception {
        presenter.showGroupOfMonths();
        verify(view).showLoading();
        when(portfoliosData.getXAxisValuesMonth()).thenReturn(getXAxisValuesMonth());
        when(portfoliosData.getData()).thenReturn(mGetData());
        verify(view).onGetStatus(1);
        verify(view).hideLoading();
        portfoliosData.setBarData((portfoliosData.getBarData()));
        verify(view).onUpdatedChartBar(portfoliosData.getBarData());
    }

    @Test
    public void testDays() throws Exception {
        presenter.showGroupOfDays(1);
        verify(view).showLoading();
        when(portfoliosData.getXAxisValuesOfDay()).thenReturn(getXAxisValuesOfDay());
        when(portfoliosData.getData()).thenReturn(mGetData());
        verify(view).onGetStatus(2);
        verify(view).hideLoading();
        portfoliosData.setBarData((portfoliosData.getBarData()));
        verify(view).onUpdatedChartBar(portfoliosData.getBarData());
    }

    @Test
    public void testQuarterly(){
        presenter.showGroupOfquarterly();
        verify(view).showLoading();
        when(portfoliosData.getXAxisValuesOfQuarterly()).thenReturn(getXAxisValuesOfQuarterly());
        when(portfoliosData.getData()).thenReturn(mGetData());
        verify(view).onGetStatus(3);
        verify(view).hideLoading();
        portfoliosData.setBarData(portfoliosData.getBarData());
        verify(view).onUpdatedChartBar(portfoliosData.getBarData());
    }

    @Test
    public void testLoadingData(){
        presenter.onLoadingData();
        verify(view).onAddDataSuccess(portfoliosData.getData());
    }

    public List<CObject> mGetData(){

        List<CObject> ls = new ArrayList<>();
        List<CPortfolios> lss = new ArrayList<>();
        lss.add(new CPortfolios("2017-01-02","11.23"));
        ls.add(new CObject("123",lss));
        lss.add(new CPortfolios("2017-01-02","11.23"));
        ls.add(new CObject("123",lss));
        lss.add(new CPortfolios("2017-02-02","11.23"));
        ls.add(new CObject("12",lss));
        lss.add(new CPortfolios("2017-02-02","11.23"));
        ls.add(new CObject("12",lss));
        return  ls ;
    }

    public BarData mGetBarData(){

        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));


        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));


        ArrayList<BarEntry> group3 = new ArrayList<>();
        group3.add(new BarEntry(7f, 0));
        group3.add(new BarEntry(8f, 1));
        group3.add(new BarEntry(9f, 2));
        group3.add(new BarEntry(13f, 3));


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
        BarData data = new BarData(getXAxisValuesOfQuarterly(), dataSets);
        return data ;
    }

    public List<String> getXAxisValuesMonth() {
        String[] mMonths = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        return Arrays.asList(mMonths);
    }

    public List<String> getXAxisValuesOfQuarterly(){
        String[] mDays = new String[]{
                "Mar", "Jun", "Sep", "Dec"
        };
        return Arrays.asList(mDays);
    }

    public List<String> getXAxisValuesOfDay(){
        String[] mDays = new String[]{
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24","25", "26", "27", "28","29", "30", "31"
        };
        return Arrays.asList(mDays);
    }

}
