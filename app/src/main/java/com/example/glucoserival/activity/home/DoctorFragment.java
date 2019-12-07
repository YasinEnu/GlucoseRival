package com.example.glucoserival.activity.home;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.DoctorDashboardData;
import com.example.glucoserival.model.PatientDashboard;
import com.example.glucoserival.model.PendingAppointmentList;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {


    public DoctorFragment() {
        // Required empty public constructor
    }
    TextView totalCompleteAppointment;
    TextView totalPendingAppointment;
    RecyclerView recyclerView;
    AppData appData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        appData=new AppData(getContext());
        View view= inflater.inflate(R.layout.fragment_doctor, container, false);
        totalCompleteAppointment=view.findViewById(R.id.totalCompleteTV);
        totalPendingAppointment=view.findViewById(R.id.totalPendingTV);
        recyclerView=view.findViewById(R.id.pendingListRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getDoctorDashboardData(appData.getUserID());
        return view;
    }

    private void getDoctorDashboardData(String doctorData) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getDoctorDashboardData(doctorData).enqueue(new Callback<DoctorDashboardData>() {
            @Override
            public void onResponse(Call<DoctorDashboardData> call, Response<DoctorDashboardData> response) {

                if (response.isSuccessful()) {

                    try {
                        DoctorDashboardData doctorDashboardData = response.body();
                        if (doctorDashboardData.getStatus().equals("success")) {
                            totalCompleteAppointment.setText(String.valueOf(doctorDashboardData.getDashboardData().getTotalCompletedAppointment()));
                            totalPendingAppointment.setText(String.valueOf(doctorDashboardData.getDashboardData().getTotalPendingAppointment()));
                            ArrayList<PendingAppointmentList> appointmentArrayLists=(ArrayList<PendingAppointmentList>) doctorDashboardData.getPendingAppointmentList();
                            PendingListAdapter pendingListAdapter=new PendingListAdapter(getContext(), appointmentArrayLists);
                            recyclerView.setAdapter(pendingListAdapter);

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
            public void onFailure(Call<DoctorDashboardData> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


}
