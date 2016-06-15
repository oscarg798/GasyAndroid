package rm.com.gasy.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rm.com.core.model.dto.TankingDTO;
import rm.com.gasy.R;
import rm.com.gasy.presentation.holders.TankingItemHeaderViewHolder;
import rm.com.gasy.presentation.holders.TankingItemViewHolder;

/**
 * Created by oscargallon on 6/6/16.
 */

public class TankingItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final SimpleDateFormat simpleDateFormat;

    private List<TankingDTO> tankingDTOList;
    private Context context;


    public TankingItemAdapter(Context context, List<TankingDTO> tankingDTOList) {
        this.tankingDTOList = tankingDTOList;
        this.context = context;
        simpleDateFormat = new SimpleDateFormat(context.getString(R.string.date_format));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = viewType == TYPE_ITEM ? LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tanking_item, parent, false) :
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tanking_item_header, parent, false);

        RecyclerView.ViewHolder holder = viewType == TYPE_ITEM ?
                new TankingItemViewHolder(itemView) : new TankingItemHeaderViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TankingItemViewHolder) {
            TankingDTO tankingDTO = tankingDTOList.get(position - 1);
            TankingItemViewHolder tankingItemViewHolder = (TankingItemViewHolder) holder;
            tankingItemViewHolder.getTvGasStationName().setText(tankingDTO.getGasStationName());

            tankingItemViewHolder.getTvTankingdate()
                    .setText(simpleDateFormat.format
                            (new Date(tankingDTO.getDate().getTime())));

            tankingItemViewHolder.getTvMileage().setText((String.format("%.0f",
                    tankingDTO.getMileage())));
        }


    }

    @Override
    public int getItemViewType(int position) {
        int type = position == 0 ? TYPE_HEADER : TYPE_ITEM;
        return type;
    }

    @Override
    public int getItemCount() {
        return tankingDTOList.size() + 1;
    }

    public List<TankingDTO> getTankingDTOList() {
        return tankingDTOList;
    }
}
