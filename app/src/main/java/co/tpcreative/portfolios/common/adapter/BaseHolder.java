package co.tpcreative.portfolios.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by hdadmin 22/11/2016
 */
public class BaseHolder<V> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(V data, int position){}

    public void event(){}
}
