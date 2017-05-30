package co.tpcreative.portfolios;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import co.tpcreative.portfolios.common.presenter.Presenter;
import co.tpcreative.portfolios.model.CData;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosActivity;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosPresenter;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosView;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class PortfoliosActivityTest {

    @Mock
    private PortfoliosView view;
    @Mock
    private PortfoliosPresenter presenter ;

    @Before
    public void setUp() throws Exception {

        presenter = new PortfoliosPresenter(view);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testData() throws Exception {
        when(presenter.getData()).thenReturn(list());
        when(presenter.getListMonths()).thenReturn(list());
    }

    @Test
    public void testMonths() throws Exception{
        when(presenter.getListMonths()).thenReturn(list());
    }

    @Test
    public void testDays() throws Exception {
        when(presenter.getListDays()).thenReturn(list());
    }

    @Test
    public void testQ(){
        when(presenter.getListQ()).thenReturn(list());
    }

    public List<CObject> list(){

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

    public BarData simpleTest(){

        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(18f, 4));
        group1.add(new BarEntry(9f, 5));

        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        ArrayList<BarEntry> group3 = new ArrayList<>();
        group3.add(new BarEntry(7f, 0));
        group3.add(new BarEntry(8f, 1));
        group3.add(new BarEntry(9f, 2));
        group3.add(new BarEntry(13f, 3));
        group3.add(new BarEntry(16f, 4));
        group3.add(new BarEntry(11f, 5));

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
        return data ;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("JAN");
        labels.add("FEB");
        labels.add("MAR");
        labels.add("APR");
        labels.add("MAY");
        labels.add("JUN");
        return labels;
    }


}
