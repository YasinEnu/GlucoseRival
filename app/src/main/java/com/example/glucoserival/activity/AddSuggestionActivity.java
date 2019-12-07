package com.example.glucoserival.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.PendingListAdapter;
import com.example.glucoserival.activity.home.UniversalRecyclerViewAdapter;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.AllAppointmentData;
import com.example.glucoserival.model.DoctorDashboardData;
import com.example.glucoserival.model.PendingAppointmentList;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSuggestionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suggestion);
        recyclerView=findViewById(R.id.totalAppointmentListRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDoctorDashboardData(new AppData(this).getUserID());
    }


    private void getDoctorDashboardData(String doctorId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getDoctorDashboardData(doctorId).enqueue(new Callback<DoctorDashboardData>() {
            @Override
            public void onResponse(Call<DoctorDashboardData> call, Response<DoctorDashboardData> response) {

                if (response.isSuccessful()) {

                    try {
                        DoctorDashboardData doctorDashboardData = response.body();
                        if (doctorDashboardData.getStatus().equals("success")) {

                            ArrayList<AllAppointmentData> all_appointment_list = (ArrayList<AllAppointmentData>) doctorDashboardData.getDashboardData().getAll_appointment_list();

                            recyclerView.setAdapter(new UniversalRecyclerViewAdapter(AddSuggestionActivity.this,all_appointment_list , R.layout.add_suggestion_list_item_view, new UniversalRecyclerViewAdapter.OnRecyclerViewItemSingleViewSet() {
                                @Override
                                public void onItemViewSet(UniversalRecyclerViewAdapter.UniversalViewHolder universalViewHolder, final Object itemData, int position) {
                                    ((TextView)universalViewHolder.itemView.findViewById(R.id.patientNameTV)).setText(((AllAppointmentData)itemData).getPatientName());
                                    ((TextView)universalViewHolder.itemView.findViewById(R.id.appointmentDateTV)).setText(((AllAppointmentData)itemData).getDateTime());
                                    universalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            startActivity(new Intent(AddSuggestionActivity.this,AppointmentListActivity.class)
                                                    .putExtra(AppointmentListActivity.SENT_APPOINTMENT_ID,((AllAppointmentData)itemData).getId()).putExtra(AppointmentListActivity.SENT_USER_ID,((AllAppointmentData)itemData).getUserId()));

                                        }
                                    });
                                }
                            }));
                            if(all_appointment_list.size()<1){
                                Toast.makeText(AddSuggestionActivity.this, "No appointment Data available!!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(AddSuggestionActivity.this, "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(AddSuggestionActivity.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(AddSuggestionActivity.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<DoctorDashboardData> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(AddSuggestionActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }




}
