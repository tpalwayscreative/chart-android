package co.tpcreative.portfolios.common.presenter;
import android.content.Context;

/**
 * Created by Phong on 5/26/17.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    Context getContext();
}
