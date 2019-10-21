package com.example.glucoserival.activity.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.PatientDashboard;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonParseException;

import java.text.BreakIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientFragment extends Fragment {


    private TextView totalAppointMentTV,nextAppointMent;
    private TextView pendingAppointment;

    public PatientFragment() {
        // Required empty public constructor
    }

    AppData appData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData=new AppData(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient, container, false);
        totalAppointMentTV=view.findViewById(R.id.totalComplete);
        nextAppointMent=view.findViewById(R.id.nextAppointmentDate);
        pendingAppointment=view.findViewById(R.id.totalPending);
        getPatientDashboardData(appData.getUserID());
        return view;
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
                        PatientDashboard patientDashboard = response.body();
                        if (patientDashboard.getStatus().equals("success")) {

                            totalAppointMentTV.setText(String.valueOf(patientDashboard.getDashboardData().getTotalAppointment()));
                            nextAppointMent.setText(patientDashboard.getNext_appointment_date());
                            pendingAppointment.setText(String.valueOf(patientDashboard.getDashboardData().getTotalPendingAppointment()));
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

}
