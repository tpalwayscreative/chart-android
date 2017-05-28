package co.tpcreative.portfolios.ui.portfolios.activity;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;
import java.util.List;

import co.tpcreative.portfolios.common.presenter.BaseView;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;


public interface PortfoliosView  extends BaseView{
    void showError(String error);
    void onAddDataSuccess(List<CObject> cPortfolios, List<String> list);
    void onUpdatedChartBar(BarData data);
    void onAddMonth(List<CObject>list);
    void onAddQuarterly(List<CObject>list);
    void onAddDays(List<CObject>list);
    void onGetStatus(int position);
    void onSaveDataToFirebase(List<CObject>list);

}
