package rm.com.gasy.presentation.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rm.com.gasy.R;

/**
 * Created by oscargallon on 6/6/16.
 */

public class TankingItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvGasStationName;
    private final TextView tvMileage;
    private final TextView tvTankingdate;

    public TankingItemViewHolder(View itemView) {
        super(itemView);
        tvGasStationName = (TextView) itemView.findViewById(R.id.tv_gas_station_name);
        tvMileage =  (TextView) itemView.findViewById(R.id.tv_mileage);
        tvTankingdate =  (TextView) itemView.findViewById(R.id.tv_tanking_date);
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
}
