package co.tpcreative.portfolios.ui.portfolios.adapter;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import co.tpcreative.portfolios.R;
import co.tpcreative.portfolios.R2;
import co.tpcreative.portfolios.common.adapter.BaseAdapter;
import co.tpcreative.portfolios.common.adapter.BaseHolder;
import co.tpcreative.portfolios.model.CObject;

public class PortfoliosAdapter extends  BaseAdapter<CObject, BaseHolder>{


    private ListenerPortfolios listenerPortfolios ;
    private Activity activity ;

    public PortfoliosAdapter(LayoutInflater inflater, Activity activity, ListenerPortfolios listenerPortfolios) {
        super(inflater);
        this.activity = activity;
        this.listenerPortfolios = listenerPortfolios;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PortfoliosHolder(inflater.inflate(R.layout.item_portfolios, parent, false));
    }

    public class PortfoliosHolder extends BaseHolder<CObject>  {

        @BindView(R2.id.rlItem)
        RelativeLayout rlItem ;
        @BindView(R2.id.ivItem)
        ImageView ivItem ;
        @BindView(R2.id.tvItem)
        TextView tvItem ;

        private CObject object ;
        private int position ;
        public PortfoliosHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(CObject data, int position) {
            super.bind(data, position);
            this.object = data ;
            this.position = position ;
            ivItem.setImageResource(getListIcon()[position]);
            tvItem.setText(data.portfolioId);
        }

        @OnClick(R.id.rlItem)
        public void onFoodSelected(){
            listenerPortfolios.onItemClicked(position);
        }

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface ListenerPortfolios {
        void onItemClicked(int position);
    }

    public int[] getListIcon(){
        int[] iconList = new int[]{
                R.drawable.ic_1,
                R.drawable.ic_2,
                R.drawable.ic_3,
                R.drawable.ic_4,
                R.drawable.ic_5,
                R.drawable.ic_6,
                R.drawable.ic_7,
                R.drawable.ic_8,
                R.drawable.ic_9,
                R.drawable.ic_10,
                R.drawable.ic_11,
                R.drawable.ic_12,
        };
        return iconList;
    }

}
