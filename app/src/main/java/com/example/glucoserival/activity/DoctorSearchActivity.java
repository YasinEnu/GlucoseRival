package com.example.glucoserival.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.helper.Utility;
import com.example.glucoserival.model.ApplyAppointmentResponse;
import com.example.glucoserival.model.Category;
import com.example.glucoserival.model.DoctorCategory;
import com.example.glucoserival.model.DoctorInfo;
import com.example.glucoserival.model.DoctorList;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSearchActivity extends AppCompatActivity {

    private ArrayList<Category> doctorCatList = new ArrayList<>();
    private ArrayList<String> doctorCatAdapterList = new ArrayList<>();
    private ArrayList<String> doctorListArrayList = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private ArrayList<DoctorInfo> doctorInfos = new ArrayList<>();
    private ListView doctorList;
    private EditText name, hospitalName;
    private TextView docNameFromServer, hospitalNameFromServer, docPhoneNumber;
    private FancyButton searchBtn;
    private Spinner docCategory;
    private CustomAdapter doctorAdapter;
    private AppData appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search);

        appData = new AppData(this);
        name = findViewById(R.id.doctorNameET);
        hospitalName = findViewById(R.id.presentHospitalET);
        docCategory = findViewById(R.id.doctorCategorySP);
        doctorList = findViewById(R.id.doctorListLV);
        searchBtn = findViewById(R.id.searchBtn);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, doctorCatAdapterList);
        doctorAdapter = new CustomAdapter(this, doctorInfos);
        docCategory.setAdapter(categoryAdapter);
        doctorList.setAdapter(doctorAdapter);
        getDocCat();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();
                String hospitalNameString = hospitalName.getText().toString();
                String categoryId = doctorCatList.get(docCategory.getSelectedItemPosition()).getId();
                getDocList(nameString, categoryId, hospitalNameString, "");
            }
        });

    }

    private void getDocCat() {

        doctorCatList.clear();
        doctorCatAdapterList.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getDocCat("").enqueue(new Callback<DoctorCategory>() {
            @Override
            public void onResponse(Call<DoctorCategory> call, Response<DoctorCategory> response) {

                if (response.isSuccessful()) {

                    try {
                        DoctorCategory doctorCategory = response.body();
                        if (doctorCategory.getStatus().equals("success")) {
                            doctorCatList.add(new Category("", "Select Category"));
                            doctorCatAdapterList.add("Select Category");
                            for (Category ca : (ArrayList<Category>) doctorCategory.getCategorys()) {
                                doctorCatList.add(ca);
                                doctorCatAdapterList.add(ca.getName());
                            }

                            categoryAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(DoctorSearchActivity.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(DoctorSearchActivity.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(DoctorSearchActivity.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                    }
                }

            }

            @Override
            public void onFailure(Call<DoctorCategory> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(DoctorSearchActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    private void getDocList(String name, String categoryId, String presentHospital, String post) {
        doctorInfos = new ArrayList<>();
        doctorInfos.clear();
        doctorListArrayList.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getDocList(name, categoryId, presentHospital, post).enqueue(new Callback<DoctorList>() {
            @Override
            public void onResponse(Call<DoctorList> call, Response<DoctorList> response) {

                if (response.isSuccessful()) {

                    try {
                        DoctorList doctorListDetails = response.body();
                        if (doctorListDetails.getStatus().equals("success")) {
                            doctorInfos = (ArrayList<DoctorInfo>) doctorListDetails.getDoctorInfo();
                            for (DoctorInfo doctorInfo : doctorInfos) {
                                doctorListArrayList.add(doctorInfo.getName());
                            }
                            doctorAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else if (doctorListDetails.getStatus().equals("fail")) {
                            String message = doctorListDetails.getMessage();
                            Toast.makeText(DoctorSearchActivity.this, message, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(DoctorSearchActivity.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JsonParseException e) {
                        Toast.makeText(DoctorSearchActivity.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(DoctorSearchActivity.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                    }
                }

            }

            @Override
            public void onFailure(Call<DoctorList> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(DoctorSearchActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    private class CustomAdapter extends ArrayAdapter<DoctorInfo> {


        public CustomAdapter(@NonNull Context context, @NonNull List<DoctorInfo> objects) {
            super(context, 0, objects);
        }

        @Override
        public int getCount() {
            return doctorInfos.size();
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View row = null;
            if (convertView == null) {
                row = LayoutInflater.from(DoctorSearchActivity.this).inflate(R.layout.doctor_list_item, parent, false);
            } else {
                row = convertView;
            }
            final TextView doctorName = row.findViewById(R.id.doctorNameTV);
            final TextView specialist = row.findViewById(R.id.doctorSpecialistTV);
            final TextView medical = row.findViewById(R.id.hospitalNameTV);
            final Button applyButton = row.findViewById(R.id.applyBtn);
            final DoctorInfo doctorsDetails = doctorInfos.get(position);

            doctorName.setText(doctorsDetails.getName());
            if (doctorsDetails.getEduQua()!=null){
                specialist.setText(doctorsDetails.getEduQua());
            }
            if (doctorsDetails.getPresentHospital()!=null){
                medical.setText(doctorsDetails.getPresentHospital());
            }
            applyButton.setTag(position);
            row.setTag(doctorsDetails.getDoctorId());
            final View finalConvertView = row;
            final Calendar calendar = Calendar.getInstance();
            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String docId=doctorInfos.get(Integer.parseInt(view.getTag().toString())).getId();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(DoctorSearchActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year, month, dayOfMonth);

                                    applyForAppointment(appData.getUserID(), docId, Utility.getFormattedDateForPrentSystem(calendar));
                                }
                            }, Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setTitle("Set Appointment Date");
                    datePickerDialog.show();


//                    LinearLayout calenderDialog=new LinearLayout(DoctorSearchActivity.this);
//                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    calenderDialog.setLayoutParams(layoutParams);
//                    final DatePicker datePicker=new DatePicker(DoctorSearchActivity.this);
//                    DatePicker.LayoutParams layoutParams1=new DatePicker.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams1.gravity=Gravity.CENTER;
//                    datePicker.setLayoutParams(layoutParams1);
//                    calenderDialog.addView(datePicker);
//                    AlertDialog.Builder ada=new AlertDialog.Builder(DoctorSearchActivity.this);
//                    ada.setView(calenderDialog);
//                    ada.setTitle("Set appointment date");
//                    ada.setPositiveButton("Set Date", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            applyForDocAppointment(appData.getUserID(),doctorInfos.get(Integer.parseInt(applyButton.getTag().toString())),datePicker.getD);
//                        }
//                    });
//                    ada.create().show();
                }
            });
            return row;
        }
    }

    private void applyForAppointment(String userId, String doctorId, final String appointmentDate) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.applyForDocAppointment(userId, doctorId, appointmentDate).enqueue(new Callback<ApplyAppointmentResponse>() {
            @Override
            public void onResponse(Call<ApplyAppointmentResponse> call, Response<ApplyAppointmentResponse> response) {

                if (response.isSuccessful()) {

                    try {
                        ApplyAppointmentResponse applyAppointmentResponse = response.body();
                        if (applyAppointmentResponse.getStatus().equals("success")) {
                            Toast.makeText(DoctorSearchActivity.this, applyAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DoctorSearchActivity.this, applyAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(DoctorSearchActivity.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(DoctorSearchActivity.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApplyAppointmentResponse> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(DoctorSearchActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
