package com.example.glucoserival.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.UniversalRecyclerViewAdapter;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.Datum;
import com.example.glucoserival.model.GlucoseList;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryMapActivity extends AppCompatActivity {

    private ArrayList<Datum> arrayList=new ArrayList<>();
    LineChart chart;
    List<Entry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);
        chart = (LineChart) findViewById(R.id.chart);
        entries = new ArrayList<Entry>();
        getGlucoseList(new AppData(getApplicationContext()).getUserID());


    }

    private void getGlucoseList(String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(HistoryMapActivity.this);
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
                        for (int a =0 ;a<arrayList.size();a++){
                            entries.add(new Entry(a+1,(float) Double.parseDouble(arrayList.get(a).getValueOfGlucose())));
                        }

                        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                        dataSet.setColor(getResources().getColor(R.color.colorAccent));
                        dataSet.setValueTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        LineData lineData = new LineData(dataSet);
                        chart.setData(lineData);
                        chart.invalidate();
                    }
                }else {
                    Toast.makeText(HistoryMapActivity.this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<GlucoseList> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(HistoryMapActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

}
