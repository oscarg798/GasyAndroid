package rm.com.gasy.presentation.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.utils.Callbacks;
import rm.com.core.utils.GasyUtils;
import rm.com.gasy.R;
import rm.com.gasy.controller.ReportFragmentController;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;
import rm.com.gasy.persistence.utils.TankingLoader;
import rm.com.gasy.presentation.activities.AddTankingActivity;
import rm.com.gasy.presentation.adapters.TankingItemAdapter;
import rm.com.gasy.presentation.dialogs.TankingDetailDialog;

/**
 * This is the fragment that show the report of gas consume,
 */
public class ReportFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<TankingDTO>>,
        Callbacks.SendSerializableToFragment {

    private static final String REPORT_LIST_KEY = "report_list";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private ReportFragmentController reportFragmentController;

    private FloatingActionButton fabAddReport;

    private RecyclerView rvTanking;

    private TankingLoader tankingLoader;

    private TankingItemAdapter tankingItemAdapter;

    private int tankingToModifyPosition = -1;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * We need to pass to fragment a report list
     *
     * @param reportList gas reportList
     * @return @{ReportFragment} instance
     */
    public static ReportFragment newInstance(Serializable reportList) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putSerializable(REPORT_LIST_KEY, reportList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(REPORT_LIST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        initComponents();
        initViewComponents(view);
        return view;
    }

    private void initViewComponents(View view) {
        fabAddReport = (FloatingActionButton) view.findViewById(R.id.fab_add_report);
        rvTanking = (RecyclerView) view.findViewById(R.id.rv_tanking);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        rvTanking.setLayoutManager(mLayoutManager);

        rvTanking.setAdapter(tankingItemAdapter);

        fabAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTankingActivity.class);
                getActivity().startActivityForResult(intent, GasyUtils.ADD_REPORT_REQUEST_CODE);
            }
        });

        reportFragmentController.loadTankingDataFromDataBase();
    }

    private void initComponents() {
        reportFragmentController = new ReportFragmentController(getActivity());
        reportFragmentController.setFragment1(this);
        tankingItemAdapter = new TankingItemAdapter(getActivity().getApplicationContext(),
                new ArrayList<TankingDTO>(), new Callbacks.IAdapterListeners() {
            @Override
            public void onAdapterClickListener(View view, Object object, String action) {
                switch (action) {
                    case Callbacks.EDIT_ACTION: {
                        Log.i("ACTION", "EDIT");
                        editTankingRequest(object);
                        break;
                    }
                    case Callbacks.DELETE_ACTION: {
                        Log.i("ACTION", "DELETE");
                        List<TankingDTO> tankingDTOs = new ArrayList<>();
                        tankingDTOs.add((TankingDTO) object);
                        tankingToModifyPosition = tankingItemAdapter.getTankingDTOList().indexOf((TankingDTO) object);
                        reportFragmentController.deleteTanking(tankingDTOs);
                        break;
                    }

                    case Callbacks.SHOW_ACTION: {
                        Log.i("ACTION", "VIEW");
                        showTankingDetailDialog((TankingDTO) object);
                        break;
                    }
                }
            }
        });

    }


    private void editTankingRequest(Object object) {
        TankingDTO tankingDTO = (TankingDTO) object;
        tankingToModifyPosition = tankingItemAdapter.getTankingDTOList().indexOf(tankingDTO);
        Intent intent = new Intent(getActivity(), AddTankingActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(getActivity().getString(R.string.tanking_key), tankingDTO);
        intent.putExtras(extras);
        getActivity().startActivityForResult(intent, GasyUtils.EDIT_REPORT_REQUEST_CODE);
    }

    public void setParamatersAndLoadTankingData(ITankingDAO iTankingDAO, SQLiteDatabase db, String whereClause,
                                                String[] wherearg) {
        getTankingLoader().setParameters(iTankingDAO, db, whereClause, wherearg);
        getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();

    }

    public void tankingDTODeleted() {
        if (tankingToModifyPosition >= 0) {
            tankingItemAdapter.getTankingDTOList().remove(tankingToModifyPosition);
            tankingItemAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return getTankingLoader();
    }

    @Override
    public void onLoadFinished(Loader<List<TankingDTO>> loader, List<TankingDTO> data) {
        if (data != null) {
            for (TankingDTO tankingDTO : data) {
                if (tankingItemAdapter != null && tankingItemAdapter.getTankingDTOList() != null) {
                    tankingItemAdapter.getTankingDTOList().add(tankingDTO);
                }
            }

            if (tankingItemAdapter != null) {
                tankingItemAdapter.notifyDataSetChanged();
            }

        }


    }

    private void showTankingDetailDialog(TankingDTO tankingDTO) {
        TankingDetailDialog tankingDetailDialog = TankingDetailDialog.getInstace("titulo", "aceptar", tankingDTO);
        tankingDetailDialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onLoaderReset(Loader loader) {
        tankingItemAdapter.getTankingDTOList().clear();
    }

    public TankingLoader getTankingLoader() {
        if (tankingLoader == null) {
            tankingLoader = new TankingLoader(getActivity());
        }
        return tankingLoader;
    }

    public void findTankingDTOByDate(TankingDTO tankingDTO) {

    }

    @Override
    public void sendSerializableData(Serializable serializable) {
        List<TankingDTO> tankingDTOList = (List<TankingDTO>) serializable;
        if (tankingDTOList != null && tankingDTOList.size() > 0) {
            if (tankingToModifyPosition >= 0) {
                tankingItemAdapter.getTankingDTOList().remove(tankingToModifyPosition);
                tankingItemAdapter.getTankingDTOList().add(tankingToModifyPosition, tankingDTOList.get(0));
                tankingToModifyPosition = -1;
            } else {
                tankingItemAdapter.getTankingDTOList().add(tankingDTOList.get(0));

            }
            tankingItemAdapter.notifyDataSetChanged();

        }
    }
}
