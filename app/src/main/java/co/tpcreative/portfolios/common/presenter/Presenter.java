package co.tpcreative.portfolios.common.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Single;
import rx.subscriptions.CompositeSubscription;


public class Presenter<V> {

    @Nullable
    private volatile V view;

    protected CompositeSubscription subscriptions;

    @CallSuper
    public void bindView(@NonNull V view) {
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    @Nullable
    protected V view() {
        return view;
    }

    @Nullable
    public void setView(V view) {
       this.view = view;
    }

    @CallSuper
    private void unbindView(@NonNull V view) {
        if (subscriptions != null) {
            if (subscriptions.isUnsubscribed()) {
                subscriptions.unsubscribe();
            }
            if (subscriptions.hasSubscriptions()) {
                subscriptions.clear();
            }
            subscriptions = null;
        }

        this.view = null;
    }

    @CallSuper
    public void unbindView() {
        unbindView(view);
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }
    private static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super(
                    "Please call Presenter.attachView(MvpView) before"
                            + " requesting data to the Presenter");
        }
    }

}

