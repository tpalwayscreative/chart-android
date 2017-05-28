package co.tpcreative.portfolios.ui.portfolios.activity;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;
import java.util.List;

import co.tpcreative.portfolios.common.presenter.BaseView;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;

/**
 * Created by Phong on 5/26/17.
 */

public interface PortfoliosView  extends BaseView{
    void showError(String error);
    void onAddDataSuccess(List<CObject> cPortfolios, List<String> list);
    void onGetCardSuccess(@NonNull ArrayList<CObject> cPortfolios);
    void onUpdatedChartBar(BarData data);
    void onAddMonth(List<CObject>list);
    void onAddQuarterly(List<CObject>list);
}
