package co.tpcreative.portfolios.common.presenter;
import android.content.Context;


public interface BaseView {
    void showLoading();
    void hideLoading();
    Context getContext();
}
