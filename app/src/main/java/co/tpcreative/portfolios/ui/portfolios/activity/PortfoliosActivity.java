package co.tpcreative.portfolios.ui.portfolios.activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import co.tpcreative.portfolios.R;
import co.tpcreative.portfolios.common.activity.BaseActivity;
import co.tpcreative.portfolios.common.dialog.DialogFactory;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.ui.portfolios.adapter.PortfoliosAdapter;


public class PortfoliosActivity extends BaseActivity implements PortfoliosView,PortfoliosAdapter.ListenerPortfolios {

    @BindView(R.id.rc_Item)
    RecyclerView recyclerView ;
    @BindView(R.id.chart1)
    BarChart mChart ;
    @BindView(R.id.btnShowMonth)
    Button btnShowMonth ;
    @BindView(R.id.btnShowQuarterly)
    Button btnShowQuarterly ;
    private ProgressDialog mProgressDialog;
    private PortfoliosPresenter presenter ;
    private PortfoliosAdapter adapter ;
    private int mStatus = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_portfolios);
        DialogFactory.isStoragePermissionGranted(this);
        presenter = new PortfoliosPresenter();
        presenter.bindView(this);
        setupRecyclerView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadJSONFromAsset();
            }
        }, 2000);
    }

    @OnClick(R.id.btnShowMonth)
    public void onbtnShowMonth(View view) {
        presenter.addMonth();
    }

    @OnClick(R.id.btnShowQuarterly)
    public void onbtnShowQuarterly(View view) {
        presenter.addQuarterly();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onUpdatedChartBar(BarData data) {
        mChart.setVisibility(View.VISIBLE);
        mChart.setData(data);
        mChart.setDescription("");
        mChart.animateXY(2000, 2000);
        mChart.invalidate();
    }

    @Override
    public void onAddDataSuccess(List<CObject> cPortfolios, List<String> list) {
        adapter.setDataSource(cPortfolios);
        presenter.showGroupOfMonths();
        presenter.addMonth();
    }

    @Override
    public void onSaveDataToFirebase(List<CObject>list) {
        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(); //Getting root reference
        myRef1.setValue(list, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    Log.d("action","Error occurred :" + databaseError.getMessage());
                }
                else{
                    Log.d("action","Sent data to Firebase successfully");
                }
            }
        });
    }

    @Override
    public void onAddMonth(List<CObject> list) {
        adapter.setDataSource(list);
        presenter.showGroupOfMonths();
    }

    @Override
    public void onAddQuarterly(List<CObject> list) {
        adapter.setDataSource(list);
        presenter.showGroupOfquarterly();
    }

    @Override
    public void onAddDays(List<CObject> list) {
        adapter.setDataSource(list);
    }

    public void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        adapter = new PortfoliosAdapter(getLayoutInflater(),this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetStatus(int position) {
         this.mStatus = position ;
    }

    @Override
    public void onItemClicked(int position) {
        try {
            if (mStatus == 1 || mStatus == 2) {
                presenter.showGroupOfDays(position);
            }
            else if(mStatus == 3){
                presenter.showGroupOfDays(position);
            }
        }
        catch (Exception e){
            Log.d("error here : ", e.getMessage());
        }
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
