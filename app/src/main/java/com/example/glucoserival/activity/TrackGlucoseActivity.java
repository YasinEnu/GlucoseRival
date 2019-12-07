package com.example.glucoserival.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.AcceptedListFragment;
import com.example.glucoserival.activity.home.CompleteListFragment;
import com.example.glucoserival.activity.home.PendingListFragment;
import com.example.glucoserival.activity.home.UniversalRecyclerViewAdapter;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.Datum;
import com.example.glucoserival.model.GlucoseList;
import com.example.glucoserival.model.PatientDashboard;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackGlucoseActivity extends AppCompatActivity {

    ArrayList<Datum> arrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_glucose);
        recyclerView = findViewById(R.id.glucoseListRV);
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getGlucoseList(new AppData(this).getUserID());
    }

    private void getGlucoseList(String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(TrackGlucoseActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getGlucoseList(userId).enqueue(new Callback<GlucoseList>() {
            @Override
            public void onResponse(Call<GlucoseList> call, Response<GlucoseList> response) {

                if (response.isSuccessful()) {
                    GlucoseList glucoseList=response.body();
                    if (glucoseList.getStatus().equals("success")){
                        arrayList= (ArrayList<Datum>) glucoseList.getData();
                        recyclerView.setAdapter(new UniversalRecyclerViewAdapter(TrackGlucoseActivity.this,arrayList , R.layout.glucose_list_item, new UniversalRecyclerViewAdapter.OnRecyclerViewItemSingleViewSet() {
                            @Override
                            public void onItemViewSet(UniversalRecyclerViewAdapter.UniversalViewHolder universalViewHolder, Object itemData, int position) {
                                ((TextView)universalViewHolder.itemView.findViewById(R.id.glucoseValueTV)).setText("Glucose Level : "+((Datum)itemData).getValueOfGlucose()+" mmol/l");
                                ((TextView)universalViewHolder.itemView.findViewById(R.id.dateTV)).setText("Date : "+((Datum)itemData).getDate());
                            }
                        }));
                        if (arrayList.size()<1){
                            Toast.makeText(TrackGlucoseActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(TrackGlucoseActivity.this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<GlucoseList> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(TrackGlucoseActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

}
