package rm.com.gasy.presentation.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.utils.GasyUtils;
import rm.com.gasy.R;
import rm.com.gasy.controller.AddTankingActivityController;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

/**
 * This Activity allows the user to enter a tanking report
 */
@ContentView(R.layout.add_report_dialog)
public class AddTankingActivity extends RoboActionBarActivity implements DatePickerDialog.OnDateSetListener {

    @InjectView(R.id.atv_gas_station_name)
    private AutoCompleteTextView atGasStationName;

    @InjectView(R.id.et_mileage)
    private EditText etMileage;

    @InjectView(R.id.et_spent_amount)
    private EditText etSpentAmount;

    @InjectView(R.id.et_tanking_date)
    private EditText etTankingDate;

    @InjectView(R.id.btn_accept)
    private Button btnAccept;

    @InjectResource(R.array.gas_station_names_array)
    private String[] gasStationNames;

    @InjectView(R.id.toolbar)
    private Toolbar toolbar;

    /**
     * Calendar instance, is used for allow the user to pick a date,
     * and get the date when he returns btnAccept
     */
    private Calendar mCalendar = Calendar.getInstance();

    /**
     * Is used for get the current device date, and set the min, and
     * max date to {@link DatePickerDialog}
     */
    private Calendar currentTimeCalendar = Calendar.getInstance();

    /**
     * This property allows to show the correct date format on tanking date edit text
     */
    private SimpleDateFormat simpleDateFormat;

    /**
     * Activity Controller
     */
    private AddTankingActivityController addTankingActivityController;

    private TankingDTO tankingDTOToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        initViewComponents();
        if (getIntent() != null && getIntent().getExtras() != null) {
            TankingDTO tankingDTO = (TankingDTO) getIntent().getExtras()
                    .getSerializable(getString(R.string.tanking_key));

            fillViewsWithTankingDTO(tankingDTO);
        }
    }

    private void fillViewsWithTankingDTO(TankingDTO tankingDTO) {
        if (tankingDTO == null) {
            return;
        }
        tankingDTOToEdit = tankingDTO;
        etTankingDate.setText(simpleDateFormat.format
                (new Date(tankingDTO.getDate().getTime())));

        etMileage.setText((String.format("%.0f",
                tankingDTO.getMileage())));

        etSpentAmount.setText((String.format("%.0f",
                tankingDTO.getSpentAmount())));

        atGasStationName.setText(tankingDTO.getGasStationName());

    }

    /**
     * This method init the bussines logic components
     */
    private void initComponents() {
        addTankingActivityController = new AddTankingActivityController(this);
        simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.US);

    }

    /**
     * this method init the view components
     */
    private void initViewComponents() {
        toolbar.setTitle(getString(R.string.add_tanking_activity_title));

        setSupportActionBar(toolbar);

        atGasStationName.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                R.layout.text_view_dropdown, gasStationNames));

        etTankingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * we created a {@link DatePickerDialog}
                 * and we set the max and min date for the dialog
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTankingActivity.this, AddTankingActivity.this, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(currentTimeCalendar.getTime().getTime());
                /**
                 * We take the current device date and decrease it one year
                 */
                //currentTimeCalendar.add(Calendar.YEAR, -1);
                //datePickerDialog.getDatePicker().setMinDate(currentTimeCalendar.getTime().getTime());
                datePickerDialog.show();

                /**
                 * This reset the current device date
                 */
                currentTimeCalendar.add(Calendar.YEAR, +1);


            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * We check if the user has picked a tanking date,
                 * if he has picked it, we send the form data to validate them.
                 */
                if (!etTankingDate.getText().toString().equals(getString(R.string.tanking_date_label))
                        && !etTankingDate.getText().toString().equals("")) {
                    addTankingActivityController.validateTankingData(mCalendar.getTime().getTime(),
                            atGasStationName.getText().toString(), etMileage.getText().toString(),
                            etSpentAmount.getText().toString(), tankingDTOToEdit);
                } else {
                    etTankingDate.setError(getString(R.string.should_add_a_tanking_date));
                    etTankingDate.requestFocus();
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(year, monthOfYear, dayOfMonth);
        etTankingDate.setText(simpleDateFormat.format(mCalendar.getTime()));

    }

    public void finishAndReturnResult(List<TankingDTO> tankingDTOList) {
        /**
         * After the form data has been validated,
         * we return the result to the {@link DashboardActivity}
         */
        Intent resultData = new Intent();
        resultData.putExtra(getString(R.string.tanking_list_key), (Serializable) tankingDTOList);
        if (getParent() == null) {
            if(tankingDTOList.get(0).getID()==0){
                setResult(GasyUtils.ADD_RESULT_CODE, resultData);
            }else{
                setResult(GasyUtils.EDIT_RESULT_CODE, resultData);
            }

        } else {
            getParent().setResult(GasyUtils.ADD_RESULT_CODE, resultData);
        }
        finish();
    }


    public EditText getAtGasStationName() {
        return atGasStationName;
    }

    public EditText getEtMileage() {
        return etMileage;
    }

    public EditText getEtSpentAmount() {
        return etSpentAmount;
    }

    public EditText getEtTankingDate() {
        return etTankingDate;
    }
}