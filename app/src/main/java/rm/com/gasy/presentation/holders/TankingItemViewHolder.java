package rm.com.gasy.presentation.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rm.com.gasy.R;

/**
 * Created by oscargallon on 6/6/16.
 */

public class TankingItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvGasStationName;
    private final TextView tvMileage;
    private final TextView tvTankingdate;
    private final ImageView ivEditTanking;
    private final ImageView ivDeleteTanking;

    public TankingItemViewHolder(View itemView) {
        super(itemView);
        tvGasStationName = (TextView) itemView.findViewById(R.id.tv_gas_station_name);
        tvMileage =  (TextView) itemView.findViewById(R.id.tv_mileage);
        tvTankingdate =  (TextView) itemView.findViewById(R.id.tv_tanking_date);
        ivEditTanking = (ImageView) itemView.findViewById(R.id.iv_edit_tanking);
        ivDeleteTanking = (ImageView)itemView.findViewById(R.id.iv_delete_tanking);
    }

    public TextView getTvGasStationName() {
        return tvGasStationName;
    }

    public TextView getTvMileage() {
        return tvMileage;
    }

    public TextView getTvTankingdate() {
        return tvTankingdate;
    }

    public ImageView getIvEditTanking() {
        return ivEditTanking;
    }

    public ImageView getIvDeleteTanking() {
        return ivDeleteTanking;
    }
}
