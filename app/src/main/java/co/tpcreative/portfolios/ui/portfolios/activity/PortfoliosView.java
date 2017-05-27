package co.tpcreative.portfolios.ui.portfolios.activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import co.tpcreative.portfolios.common.presenter.BaseView;
import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.model.CPortfolios;

/**
 * Created by Phong on 5/26/17.
 */

public interface PortfoliosView  extends BaseView{
    void showError(String error);
    void addCardSuccess(ArrayList<CObject> cPortfolios);
    void getCardSuccess(@NonNull ArrayList<CObject> cPortfolios);
}
