package com.example.glucoserival.activity.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.model.AppointmentStatusModel;
import com.example.glucoserival.model.PendingAppointmentList;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YASIN on 06,July,2019
 * Email: yasinenubd5@gmail.com
 */
public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.PendingViewHolder> {

    Context context;
    ArrayList<PendingAppointmentList> pendingAppointmentLists;

    public PendingListAdapter(Context context, ArrayList<PendingAppointmentList> pendingAppointmentLists) {
        this.context=context;
        this.pendingAppointmentLists=pendingAppointmentLists;
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PendingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_list_item_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingViewHolder pendingViewHolder, final int i) {

        TextView patientName=pendingViewHolder.itemView.findViewById(R.id.patientNameTV);
        TextView appointmentDate=pendingViewHolder.itemView.findViewById(R.id.appointmentDateTV);
        TextView acceptButton=pendingViewHolder.itemView.findViewById(R.id.acceptButton);
        TextView declineButton=pendingViewHolder.itemView.findViewById(R.id.declineButton);

        final PendingAppointmentList pendingAppointmentList=pendingAppointmentLists.get(i);

        patientName.setText(pendingAppointmentList.getPatient_name());
        appointmentDate.setText(pendingAppointmentList.getDate());
        acceptButton.setTag(i);
        declineButton.setTag(i);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog(pendingAppointmentList.getUserId(),"1",i);
            }
        });
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog(pendingAppointmentList.getUserId(),"2", i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pendingAppointmentLists.size();
    }


    public class PendingViewHolder extends RecyclerView.ViewHolder {
        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    void showConfirmDialog(final String patientId, final String status, final int position){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("You want to "+ getUserUnderstandableStatus(status)+", are you sure.");
        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendAppointmentDataToServer(patientId,status,position);
            }
        });
        alertDialog.show();
    }


    String getUserUnderstandableStatus(String statusCode){
        switch (statusCode){
            case "1":return "Accept";
            case "2":return "Reject";
            default: return "null";
        }
    }

    private void sendAppointmentDataToServer(String patientData, String status, final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.setAppointmentStatus(patientData,status).enqueue(new Callback<AppointmentStatusModel>() {
            @Override
            public void onResponse(Call<AppointmentStatusModel> call, Response<AppointmentStatusModel> response) {

                if (response.isSuccessful()) {

                    try {
                        AppointmentStatusModel appointmentStatusModel = response.body();
                        if (appointmentStatusModel.getStatus().equals("success")) {
                            Toast.makeText(context, appointmentStatusModel.getMessage(), Toast.LENGTH_SHORT).show();
                            pendingAppointmentLists.remove(position);
                            notifyDataSetChanged();
                        } else if (appointmentStatusModel.getStatus().equals("fail")){
                            Toast.makeText(context, appointmentStatusModel.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(context, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(context, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<AppointmentStatusModel> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(context, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


}
