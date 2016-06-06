package rm.com.gasy.presentation.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.gasy.R;
import rm.com.gasy.controller.ReportFragmentController;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;
import rm.com.gasy.persistence.utils.TankingLoader;

/**
 * This is the fragment that show the report of gas consume,
 */
public class ReportFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<TankingDTO>> {

    private static final String REPORT_LIST_KEY = "report_list";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private ReportFragmentController reportFragmentController;

    private FloatingActionButton fabAddReport;

    private TankingLoader tankingLoader;

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
        fabAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportFragmentController.showAddReportDialog();
            }
        });
        reportFragmentController.loadTankingDataFromDataBase();
    }

    private void initComponents() {
        reportFragmentController = new ReportFragmentController(getActivity());

    }

    public void setParamatersAndLoadTankingData(ITankingDAO iTankingDAO, SQLiteDatabase db, String whereClause,
                                                String[] wherearg) {
        getTankingLoader().setParameters(iTankingDAO, db, whereClause, wherearg);
        getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();

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
                Log.i("DATA", tankingDTO.getGasStationName());
                Log.i("DATA", tankingDTO.getDate().toString());

            }
        }


    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public TankingLoader getTankingLoader() {
        if (tankingLoader == null) {
            tankingLoader = new TankingLoader(getActivity());
        }
        return tankingLoader;
    }
}
