package co.tpcreative.portfolios.common.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
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
}

