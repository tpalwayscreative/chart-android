package co.tpcreative.portfolios.ui.portfolios.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import co.tpcreative.portfolios.R;
import co.tpcreative.portfolios.R2;
import co.tpcreative.portfolios.common.activity.BaseActivity;
import co.tpcreative.portfolios.common.custom.DayAxisValueFormatter;
import co.tpcreative.portfolios.common.custom.MyAxisValueFormatter;
import co.tpcreative.portfolios.common.custom.XYMarkerView;
import co.tpcreative.portfolios.common.dialog.DialogFactory;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;
import co.tpcreative.portfolios.ui.portfolios.adapter.PortfoliosAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PortfoliosActivity extends BaseActivity implements PortfoliosView,PortfoliosAdapter.ListenerPortfolios,OnChartValueSelectedListener {

    @BindView(R2.id.tvHello)
    TextView tvHello ;
    @BindView(R2.id.rc_Item)
    RecyclerView recyclerView ;
    @BindView(R2.id.chart1)
    BarChart mChart ;
    private ProgressDialog mProgressDialog;
    private PortfoliosPresenter presenter ;
    private PortfoliosAdapter adapter ;

    /* Chart */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_portfolios);
        DialogFactory.isStoragePermissionGranted(this);
        presenter = new PortfoliosPresenter();
        presenter.bindView(this);
        presenter.showLoading();
        setupRecyclerView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadJSONFromAsset();
            }
        }, 2000);


//        mChart.setOnChartValueSelectedListener(this);
//
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(true);
//
//        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
//        mChart.setMaxVisibleValueCount(60);
//
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//
//        mChart.setDrawGridBackground(false);
//        // mChart.setDrawYLabels(false);
//
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setLabelCount(7);
////        xAxis.setValueFormatter(new IAxisValueFormatter() {
////            @Override
////            public String getFormattedValue(float value, AxisBase axis) {
////                Log.d("action","Value : " + value );
////                return mMonths[(int) value % mMonths.length];
////            }
////        });
//
//
//        IAxisValueFormatter custom = new MyAxisValueFormatter();
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//
//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//
//        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart
//        //setData(12, 50);



//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(formatter);
        //setDataGroup();

        //setDataBar();
        XAxis xAxis = mChart.getXAxis();
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        setDataGroup();

    }



    private void setData(int count, float range) {

        float start = 0f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            if (Math.random() * 100 < 25) {
                Log.d("action",i + "");
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.ic_account_balance_black_36dp)));
            } else {
                Log.d("action",i + "");
                yVals1.add(new BarEntry(i, val));

            }
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            Log.d("action","Show data is existing");
        } else {

            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);
            mChart.setData(data);
        }
    }

    public void setDataBar(){
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        mChart.setData(data);
        mChart.setFitBars(true); // make the x-axis fit exactly all bars
        mChart.invalidate(); // refresh
    }


    public void setDataGroup(){
        Observable.create(subscriber -> {

            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

            yVals1.add(new BarEntry(0f, 30f));
            yVals1.add(new BarEntry(1f, 80f));
            yVals1.add(new BarEntry(2f, 60f));
            yVals1.add(new BarEntry(3f, 50f));
            // gap of 2f
            yVals1.add(new BarEntry(5f, 70f));
            yVals1.add(new BarEntry(6f, 60f));

            yVals2.add(new BarEntry(0f, 40f));
            yVals2.add(new BarEntry(1f, 70f));
            yVals2.add(new BarEntry(2f, 90f));
            yVals2.add(new BarEntry(3f, 20f));
            // gap of 2f
            yVals2.add(new BarEntry(5f, 10f));
            yVals2.add(new BarEntry(6f, 67f));

            BarDataSet set1 = new BarDataSet(yVals1, "Group 1");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            BarDataSet set2 = new BarDataSet(yVals2, "Group 2");
            set2.setColors(ColorTemplate.COLORFUL_COLORS);
            BarData data = new BarData(set1, set2);
            subscriber.onNext(data);

        }).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.newThread())
           .subscribe(data ->{
               XAxis xAxis = mChart.getXAxis();
               xAxis.setCenterAxisLabels(true);
               BarData datas = (BarData) data;
               float groupSpace = 0.06f;
               float barSpace = 0.02f; // x2 dataset
               float barWidth = 0.45f; // x2 dataset
               // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
               datas.setBarWidth(barWidth); // set the width of each bar
               mChart.setData(datas);
               mChart.setFitBars(true);
               mChart.groupBars(1980f, groupSpace, barSpace); // perform the "explicit" grouping
               mChart.invalidate();

           });
    }



    private void setData(final int count) {

        Observable.create(subscriber -> {
            presenter.showLoading();
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
            ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
            List<CObject> list = presenter.getData();
            float start = 1f;
            for (int i = (int) start; i < start + count + 1; i++) {
                Log.d("action","show : "+ i);
                for (CObject index : list){
                    for (CPortfolios j : index.getNavs()){
                        if (index.getPortfolioId().equals("0d515913-20e9-4cf0-83e9-a53532ee70a8")){
                            yVals1.add(new BarEntry(i,Float.valueOf(j.amount)));
                        }
                       // yVals2.add(new BarEntry(i,Float.valueOf(j.amount)));
                       // yVals3.add(new BarEntry(i,Float.valueOf(j.amount)));
                    }
                }
            }

            BarDataSet  set1 = new BarDataSet(yVals1, "0d515913-20e9-4cf0-83e9-a53532ee70a8");
            set1.setDrawIcons(false);
            set1.setValueTextColor(R.color.colorAccent);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

//            BarDataSet  set2 = new BarDataSet(yVals2, "Invest 2");
//            set2.setDrawIcons(false);
//            set2.setColors(ColorTemplate.MATERIAL_COLORS);
//            set2.setValueTextColor(R.color.colorPrimary);
//
//            BarDataSet  set3 = new BarDataSet(yVals3, "Invest 3");
//            set3.setDrawIcons(false);
//            set3.setColors(ColorTemplate.MATERIAL_COLORS);
//            set3.setValueTextColor(R.color.cardview_shadow_start_color);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            //dataSets.add(set2);
            //dataSets.add(set3);

            //float groupSpace = 1f;
            //float barSpace = 0.03f; // x2 dataset
            //float barWidth = 1f; // x2 dataset
            // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

            //BarData d = new BarData(dataSets);
            //d.setBarWidth(barWidth);
            // make this BarData object grouped
            //d.groupBars(0, groupSpace, barSpace); // start at x = 0


            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            subscriber.onNext(data);
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(data -> {
                    presenter.hideLoading();
                    mChart.setData((BarData) data);
                    mChart.invalidate();
                });

    }


    protected RectF mOnValueSelectedRectF = new RectF();
    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        if (e == null)
//            return;
//
//        RectF bounds = mOnValueSelectedRectF;
//        mChart.getBarBounds((BarEntry) e, bounds);
//        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        Log.i("x-index",
//                "low: " + mChart.getLowestVisibleX() + ", high: "
//                        + mChart.getHighestVisibleX());
//
//        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void addCardSuccess(ArrayList<CObject> cPortfolios) {
        Log.d("action","Size : " + cPortfolios.size());
        adapter.setDataSource(cPortfolios);
        //presenter.printTime();
        //setData(12);

    }

    @Override
    public void getCardSuccess(@NonNull ArrayList<CObject> cPortfolios) {

    }

    public void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        adapter = new PortfoliosAdapter(getLayoutInflater(),this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getApplicationContext(),"Show item : " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null){
            mProgressDialog = DialogFactory.simpleLoadingDialog(this , "Loading...");
        }
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.unbindView();
        }
    }



}
