package com.example.glucoserival.activity.home;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.AppointmentHomeActivity;
import com.example.glucoserival.activity.AppointmentListActivity;
import com.example.glucoserival.activity.HistoryMapActivity;
import com.example.glucoserival.activity.InputGlucoseActivity;
import com.example.glucoserival.activity.TrackGlucoseActivity;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.PatientDashboard;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientNewFragment extends Fragment implements View.OnClickListener {


    private AppData appData;
    private PatientDashboard patientDashboard;
    private TextView lastGlucoseLevelTV,inputGlucoseTV,trackGlucoseTV,appointmentTV,showHistoryTV,showPrescriptionTV,storePrescriptionTV;

    public PatientNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData=new AppData(getContext());
        patientDashboard=new PatientDashboard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_patient_new, container, false);
        lastGlucoseLevelTV=view.findViewById(R.id.lastGlucoseLevelTV);
        inputGlucoseTV=view.findViewById(R.id.inputGlucoseTV);
        trackGlucoseTV=view.findViewById(R.id.trackGlucoseTV);
        appointmentTV=view.findViewById(R.id.appointmentTV);
        showHistoryTV=view.findViewById(R.id.showHistoryTV);
        showPrescriptionTV=view.findViewById(R.id.showPrescriptionTV);
        storePrescriptionTV=view.findViewById(R.id.storePrescriptionTV);
        inputGlucoseTV.setOnClickListener(this);
        trackGlucoseTV.setOnClickListener(this);
        appointmentTV.setOnClickListener(this);
        showHistoryTV.setOnClickListener(this);
        showPrescriptionTV.setOnClickListener(this);
        storePrescriptionTV.setOnClickListener(this);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPatientDashboardData(appData.getUserID());
    }

    private void getPatientDashboardData(String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getPatientDashboardData(userId).enqueue(new Callback<PatientDashboard>() {
            @Override
            public void onResponse(Call<PatientDashboard> call, Response<PatientDashboard> response) {

                if (response.isSuccessful()) {

                    try {
                        patientDashboard = response.body();
                        if (patientDashboard.getStatus().equals("success")) {

                            lastGlucoseLevelTV.setText("Last Glucose Level: "+patientDashboard.getLast_glucose_level()+" mmol/l");
                            appData.setUserDOB(patientDashboard.getUserInfo().getDateOfBirth());

                        } else {
                            Toast.makeText(getContext(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(getContext(), "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<PatientDashboard> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inputGlucoseTV :
                startActivity(new Intent(getContext(), InputGlucoseActivity.class));
                break;
            case R.id.trackGlucoseTV:
                startActivity(new Intent(getContext(), TrackGlucoseActivity.class));
                break;
            case R.id.appointmentTV:
                startActivity(new Intent(getContext(), AppointmentHomeActivity.class));
                break;
            case R.id.showHistoryTV:
                startActivity(new Intent(getContext(), HistoryMapActivity.class));
                break;
            case R.id.showPrescriptionTV:
                break;
            case R.id.storePrescriptionTV:
                break;
        }

    }
}
