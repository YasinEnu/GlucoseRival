package com.example.glucoserival.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.helper.Utility;
import com.example.glucoserival.model.AddGlucose;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputGlucoseActivity extends AppCompatActivity {

    private AppData appData;
    private Calendar dateCal;
    private SimpleDateFormat simpleDateFormat;
    private TextView dateTV;
    private EditText glucoseValue;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_glucose);

        appData=new AppData(this);
        dateCal =Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd MMM yyyy");
        dateTV=findViewById(R.id.dateTV);
        submitButton=findViewById(R.id.submitButton);
        glucoseValue=findViewById(R.id.glucoseValueET);

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(InputGlucoseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateCal.set(year,month,dayOfMonth);
                        dateTV.setText(simpleDateFormat.format(dateCal.getTime()));
                    }
                }, dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitGlucoseDataToServer(appData.getUserID(),glucoseValue.getText().toString(), Utility.getFormattedDateForPrentSystem(dateCal));
            }
        });



    }

    private void submitGlucoseDataToServer(String userID, String glucoseValue, String date) {

        final ProgressDialog progressDialog = new ProgressDialog(InputGlucoseActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        MyNetwork.getRetrofit().create(ConnApi.class).submitGlucoseData(userID,glucoseValue,date).enqueue(new Callback<AddGlucose>() {
            @Override
            public void onResponse(Call<AddGlucose> call, Response<AddGlucose> response) {
                if (response.isSuccessful()){
                    AddGlucose addGlucose=response.body();
                    if (addGlucose.getStatus().equals("success")){
                        Toast.makeText(InputGlucoseActivity.this, addGlucose.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InputGlucoseActivity.this, "Submission Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddGlucose> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(InputGlucoseActivity.this, "Network error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}
