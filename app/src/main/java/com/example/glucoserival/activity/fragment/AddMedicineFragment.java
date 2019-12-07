package com.example.glucoserival.activity.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.PatientNewFragment;
import com.example.glucoserival.activity.home.UniversalRecyclerViewAdapter;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.MedicineDatum;
import com.example.glucoserival.model.MedicineModel;
import com.example.glucoserival.model.SuggestionDatum;
import com.example.glucoserival.model.SuggestionModel;
import com.example.glucoserival.model.SuggestionResponse;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFragment extends Fragment {

    private static final String APPOINTMENT_ID = "appointment_id";
    private static final String USER_ID = "user_id";
    RecyclerView recyclerView;
    ArrayList medicineList=new ArrayList<>();
    String appointmentId,userId;
    UniversalRecyclerViewAdapter universalRecyclerViewAdapter;

    public static AddMedicineFragment newInstance(String  appointmentId, String userId) {
        AddMedicineFragment fragment = new AddMedicineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(APPOINTMENT_ID, appointmentId);
        bundle.putString(USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        appointmentId=getArguments().getString(APPOINTMENT_ID);
        userId=getArguments().getString(USER_ID);
        getPrescriptionData(userId,appointmentId);
        View view= inflater.inflate(R.layout.fragment_add_medicine, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.medicineListRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        universalRecyclerViewAdapter=new UniversalRecyclerViewAdapter(getContext(), medicineList, R.layout.medicine_list_item_view, new UniversalRecyclerViewAdapter.OnRecyclerViewItemSingleViewSet() {
            @Override
            public void onItemViewSet(UniversalRecyclerViewAdapter.UniversalViewHolder universalViewHolder, Object itemData, int position) {

                if (itemData instanceof MedicineDatum){
                    ((TextView)universalViewHolder.itemView.findViewById(R.id.medicineNameTV)).setText("Medicine Name = "+((MedicineDatum)itemData).getMedicineName());
                    ((TextView)universalViewHolder.itemView.findViewById(R.id.beforeOrAfterTV)).setText(((MedicineDatum)itemData).getBeforeOrAfterEat()+ " to eat");
                    ((TextView)universalViewHolder.itemView.findViewById(R.id.daysTV)).setText(((MedicineDatum)itemData).getHowManyDays()+" days ");
                    ((TextView)universalViewHolder.itemView.findViewById(R.id.medicineTakingTimeTV)).setText(((MedicineDatum)itemData).getMorning()+"/"+((MedicineDatum)itemData).getNoon()+"/"+((MedicineDatum)itemData).getNight());

                }else {
                    if (itemData instanceof SuggestionDatum){

                        Log.d("NOT_MEDICINE","SUGGESTION");
                        ((TextView)universalViewHolder.itemView.findViewById(R.id.medicineNameTV)).setText("Suggestion: \n"+((SuggestionDatum)itemData).getSuggestion());
                        ((TextView)universalViewHolder.itemView.findViewById(R.id.beforeOrAfterTV)).setVisibility(View.GONE);
                        ((TextView)universalViewHolder.itemView.findViewById(R.id.daysTV)).setVisibility(View.GONE);
                        ((TextView)universalViewHolder.itemView.findViewById(R.id.medicineTakingTimeTV)).setVisibility(View.GONE);
                        ((TextView)universalViewHolder.itemView.findViewById(R.id.medicineTakingTitle)).setVisibility(View.GONE);

                    }
                }

            }
        });

        recyclerView.setAdapter(universalRecyclerViewAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new AppData(getContext()).getUserType().equalsIgnoreCase("P")){
                    showMedicineInputDialog("Add medicine");
                }else {
                    showSuggestionInputDialog("Add Suggestion");
                }

            }
        });
        return view;
    }

    private void showMedicineInputDialog(String title) {

        AlertDialog.Builder aD=new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.medicine_input_view, null);
        final EditText mName,beforOrAfterEat,days;
        final CheckBox morningCB,noonCB,nightCB;
        mName=dialogView.findViewById(R.id.medicineNameET);
        beforOrAfterEat=dialogView.findViewById(R.id.beforeOrAfterET);
        days=dialogView.findViewById(R.id.daysET);
        morningCB=dialogView.findViewById(R.id.morningCB);
        noonCB =dialogView.findViewById(R.id.noonCB);
        nightCB=dialogView.findViewById(R.id.nightCB);
        aD.setView(dialogView);
        aD.setTitle(title);
        aD.setCancelable(false);
        aD.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String name=mName.getText().toString();
                String bOrAEat=beforOrAfterEat.getText().toString();
                String daysString=days.getText().toString();
                String morningString = new String(),noonString = new String(),nightString = new String();
                if (morningCB.isChecked()){
                    morningString=morningCB.getText().toString();
                }
                if (noonCB.isChecked()){
                    noonString=noonCB.getText().toString();
                }
                if (nightCB.isChecked()){
                    nightString=nightCB.getText().toString();
                }
                addPrescriptionData(new AppData(getContext()).getUserID(),appointmentId,name,bOrAEat,morningString,noonString,nightString,daysString);
            }
        });
        aD.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = aD.create();
        dialog.show();
    }

    private void getPrescriptionData(String userId, final String appointmentId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getPrescription(userId,appointmentId).enqueue(new Callback<MedicineModel>() {
            @Override
            public void onResponse(Call<MedicineModel> call, Response<MedicineModel> response) {

                if (response.isSuccessful()) {



                    try {
                        MedicineModel medicineModel=response.body();
                        if (medicineModel.getStatus().equals("success")) {

                            for (MedicineDatum medicineDatum: (ArrayList<MedicineDatum>) medicineModel.getMedicineData()){
                                medicineList.add(medicineDatum);
                                universalRecyclerViewAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getContext(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                        getSuggestionData(appointmentId,medicineList,recyclerView);
                    } catch (JsonParseException e) {
                        Toast.makeText(getContext(), "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.hide();
                    }
                }

            }

            @Override
            public void onFailure(Call<MedicineModel> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addPrescriptionData(final String userId,
                                     String appointment_id,
                                     String medicine_name,
                                     String before_or_after_eat,
                                     String morning,
                                     String noon,
                                     String night,
                                     String how_many_days) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.addPrescription(userId,appointment_id,medicine_name,before_or_after_eat,morning,noon,night,how_many_days).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {



                    try {
                        JsonObject jsonObject=response.body();
                        if (jsonObject.get("status").getAsString().equals("success")) {
                            Toast.makeText(getContext(), "Medicine Added successfully", Toast.LENGTH_SHORT).show();
                            medicineList.clear();
                            universalRecyclerViewAdapter.notifyDataSetChanged();
                            getPrescriptionData(userId,appointmentId);
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
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }




    private void getSuggestionData(String appointmentId, final ArrayList arrayList, final RecyclerView recyclerView) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.get_suggestion(appointmentId).enqueue(new Callback<SuggestionModel>() {
            @Override
            public void onResponse(Call<SuggestionModel> call, Response<SuggestionModel> response) {

                if (response.isSuccessful()) {



                    try {
                        SuggestionModel suggestionModel=response.body();
                        if (suggestionModel.getStatus().equals("success")) {
                            for (int a=0;a<suggestionModel.getSuggestionData().size();a++){
                                medicineList.add(suggestionModel.getSuggestionData().get(a));
                                universalRecyclerViewAdapter.notifyDataSetChanged();
                            }


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
            public void onFailure(Call<SuggestionModel> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


    private void showSuggestionInputDialog(String title) {

        AlertDialog.Builder aD=new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.suggestion_input_view, null);
        final EditText mName;
        mName=dialogView.findViewById(R.id.medicineNameET);
        aD.setView(dialogView);
        aD.setTitle(title);
        aD.setCancelable(false);
        aD.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String name=mName.getText().toString();

                addSuggestionData(appointmentId,name);
            }
        });
        aD.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = aD.create();
        dialog.show();
    }

    private void addSuggestionData(String appointment_id,
                                   String medicine_name) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.add_suggestion(appointment_id,medicine_name).enqueue(new Callback<SuggestionResponse>() {
            @Override
            public void onResponse(Call<SuggestionResponse> call, Response<SuggestionResponse> response) {

                if (response.isSuccessful()) {



                    try {
                        SuggestionResponse jsonObject=response.body();
                        if (jsonObject.getStatus().equals("success")) {
                            Toast.makeText(getContext(), "Suggestion Added successfully", Toast.LENGTH_SHORT).show();
                            medicineList.clear();
                            recyclerView.getAdapter().notifyDataSetChanged();
                            getPrescriptionData(userId,appointmentId);

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
            public void onFailure(Call<SuggestionResponse> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


}
