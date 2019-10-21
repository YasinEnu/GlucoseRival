package com.example.glucoserival.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.glucoserival.model.UserInfoModel;


public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "soft_doc";

    // Account Registration table name
    private static final String TABLE_ACCOUNT_REGISTRATION = "account_registration";

    private static final String TABLE_VACCINE_INFO = "vaccine_info";

    //child account registration table name
    private static final String TABLE_CHILD_ACCOUNT_REGISTRATION = "child_account_registration";

    //given immunization table name
    private static final String TABLE_GIVEN_IMMUNIZATION = "given_immunization";

    //given immunization table name
    private static final String TABLE_CHILD_GROWTH_RECORD = "child_growth_record";


    private static final String TABLE_ALARM = "alarm_table";

    private static final String TABLE_SET_ALARM = "alarm_set_table";

    private static final String TABLE_WOMEN_ACCOUNT_REGISTRATION = "women_account_registration";

    private static final String TABLE_WOMEN_ANTENATAL_VISIT = "women_antenatal_visit";

    private static final String TABLE_WOMEN_TT_INFO = "women_tt_info";

    private static final String TABLE_WOMEN_GIVEN_TT = "women_given_tt";


    private static final String TABLE_ELDER_ACCOUNT_REGISTRATION = "elder_account_registration";

    private static final String TABLE_ELDER_MEDICATION_INFO = "elder_medication_info";

    private static final String TABLE_ELDER_BLOOD_INFO = "elder_blood_info";


    // Account Registration Table Columns names
    private static final String KEY_REG_USER_UNIQUE_ID = "user_unique_id";
    private static final String KEY_REG_USER_NAME = "user_name";
    private static final String KEY_REG_EMAIL = "user_email";
    private static final String KEY_REG_PASSWORD = "password";

    // Child Registration Table Columns names

    private static final String KEY_REG_CHILD_UNIQUE_ID = "child_unique_id";
    private static final String KEY_REG_CHILD_FATHER_UNIQUE_ID = "user_unique_id";
    private static final String KEY_REG_CHILD_NAME = "child_name";
    private static final String KEY_REG_CHILD_DATE_OF_BIRTH = "child_date_of_birth";
    private static final String KEY_REG_CHILD_GENDER = "child_gender";
    //    private static final String KEY_REG_CHILD_HEIGHT = "child_height";
    private static final String KEY_REG_CHILD_WEIGHT = "child_weight";
    private static final String KEY_REG_CHILD_IMAGE_PATH = "child_image_path";


    // Vaccine Info Table Columns names
    private static final String KEY_VACCINE_UNIQUE_ID = "user_unique_id";
    private static final String KEY_VACCINE_NAME = "user_name";
    private static final String KEY_VACCINE_TYPE = "vaccine_type";
    private static final String KEY_VACCINE_STARTING = "starting_time_of_doses";
    private static final String KEY_DOSES = "doses";


    private static final String KEY_GIVEN_IMMUNIZATION_ID = "given_immunization_id";
    private static final String KEY_GIVEN_IMMUNIZATION_CHILD_ID = "child_id";
    private static final String KEY_GIVEN_IMMUNIZATION_VACCINE_ID = "vaccine_id";
    private static final String KEY_GIVEN_IMMUNIZATION_TOTAL_DOSE_NO = "total_dose";
    private static final String KEY_GIVEN_IMMUNIZATION_DOSE_NO = "doses_no";
    private static final String KEY_GIVEN_IMMUNIZATION_STARTING_DATE = "starting_date";
    private static final String KEY_GIVEN_IMMUNIZATION_STATUS = "given_immunization_status";
    private static final String KEY_GIVEN_IMMUNIZATION_DOSES = "given_doses";


    //Child growth table columns
    private static final String KEY_GROWTH_ID = "growth_id";
    private static final String KEY_GROWTH_CHILD_UNIQUE_ID = "child_id";
    private static final String KEY_GROWTH_CHILD_WEIGHT = "child_weight";
    private static final String KEY_GROWTH_CHILD_AGE = "child_age";


    private static final String KEY_ALARM_UNIQUE_ID = "alarm_unique_id";
    private static final String KEY_ALARM_USER_UNIQUE_ID = "user_unique_id";
    private static final String KEY_ALARM_CHILD_UNIQUE_ID = "child_unique_id";
    private static final String KEY_ALARM_IMMUNIZATION_UNIQUE_ID = "immunization_unique_id";
    private static final String KEY_ALARM_TIME = "alarm_time";


    private static final String KEY_SET_ALARM_ID = "set_alarm_id";


    // Elder Account Registration
    private static final String KEY_REG_ELDER_UNIQUE_ID = "elder_unique_id";
    private static final String KEY_REG_ELDER_USER_UNIQUE_ID = "user_unique_id";
    private static final String KEY_REG_ELDER_NAME = "elder_name";
    private static final String KEY_REG_ELDER_DATE_OF_BIRTH = "elder_date_of_birth";
    private static final String KEY_REG_ELDER_GENDER = "elder_gender";
    private static final String KEY_REG_ELDER_HEIGHT = "elder_height";
    private static final String KEY_REG_ELDER_WEIGHT = "elder_weight";
    private static final String KEY_REG_ELDER_OCCUPATION = "elder_occupation";
    private static final String KEY_REG_ELDER_IMAGE_PATH = "elder_image_path";


    // Elder Medication Insert
    private static final String KEY_INS_ELDER_MEDICATION_UNIQUE_ID = "medication_unique_id";
    private static final String KEY_INS_ELDER_UNIQUE_ID = "elder_unique_id";
    private static final String KEY_INS_ELDER_MEDICATION_TYPE = "medication_type";
    private static final String KEY_INS_ELDER_MEDICATION_NAME = "medication_name";
    private static final String KEY_INS_ELDER_MEDICATION_STRENGTH = "medication_strength";
    private static final String KEY_INS_ELDER_MEDICATION_TAKING_TIME = "medication_taking_time";
    private static final String KEY_INS_ELDER_MEDICATION_DURATION = "medication_duration";

    // Elder Blood Record Table Columns names
    private static final String KEY_ELDER_BLOOD_UNIQUE_ID = "elder_blood_unique_id";
    private static final String KEY_ELDER_USER_UNIQUE_ID = "elder_user_unique_id";
    private static final String KEY_ELDER_BLOOD_PRESSURE = "elder_blood_pressure";
    private static final String KEY_ELDER_BLOOD_PRESSURE_UPPER = "elder_blood_pressure_upper";
    private static final String KEY_ELDER_BLOOD_PRESSURE_LOWER = "elder_blood_pressure_lower";
    private static final String KEY_ELDER_BLOOD_GLUCOSE_MOLE = "elder_blood_glucose_mole";
    private static final String KEY_ELDER_BLOOD_GLUCOSE_MG = "elder_blood_glucose_mg";
    private static final String KEY_ELDER_BLOOD_COMMENT = "elder_blood_comment";
    private static final String KEY_ELDER_BLOOD_RECORD_TIME = "elder_blood_record_time";


    private static final String KEY_REG_WOMEN_UNIQUE_ID = "women_unique_id";
    private static final String KEY_REG_WOMEN_USER_UNIQUE_ID = "user_unique_id";
    private static final String KEY_REG_WOMEN_NAME = "women_name";
    private static final String KEY_REG_WOMEN_DATE_OF_BIRTH = "women_date_of_birth";
    private static final String KEY_REG_WOMEN_LAST_MENSTRUATION_DATE = "women_last_menstruation_date";
    private static final String KEY_REG_WOMEN_HEIGHT = "women_height";
    private static final String KEY_REG_WOMEN_WEIGHT = "women_weight";
    private static final String KEY_REG_WOMEN_IMAGE_PATH = "women_image_path";


    private static final String KEY_ANTENATAL_VISIT_ID = "antenatal_visit_id";
    private static final String KEY_ANTENATAL_VISIT_USER_ID = "antenatal_visit_user_id";
    private static final String KEY_ANTENATAL_VISIT_WOMEN_ID = "antenatal_visit_women_id";
    private static final String KEY_ANTENATAL_VISIT_NUM = "antenatal_visit_num";
    private static final String KEY_ANTENATAL_VISIT_TIME = "antenatal_visit_time";

    private static final String KEY_TT_ID = "tt_id";
    private static final String KEY_TT_NUM = "tt_num";
    private static final String KEY_TT_DAY_DIFFERENCE = "tt_day_difference";
    private static final String KEY_TT_TO_DO = "tt_to_do";
    private static final String KEY_TT_DETAILS = "tt_details";


    private static final String KEY_GIVEN_TT_ID = "given_tt_id";
    private static final String KEY_GIVEN_TT_WOMEN_ID = "given_tt_women_id";
    private static final String KEY_GIVEN_TT_TIME = "given_tt_time";
    private static final String KEY_REG_USER_TYPE = "userType";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_CHILD_ACCOUNT_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_CHILD_ACCOUNT_REGISTRATION + "("
            + KEY_REG_CHILD_UNIQUE_ID + " TEXT NOT NULL,"
            + KEY_REG_CHILD_FATHER_UNIQUE_ID + " TEXT,"
            + KEY_REG_CHILD_NAME + " TEXT,"
            + KEY_REG_CHILD_DATE_OF_BIRTH + " TEXT,"
            + KEY_REG_CHILD_GENDER + " TEXT,"
            + KEY_REG_CHILD_WEIGHT + " TEXT,"
            + KEY_REG_CHILD_IMAGE_PATH + " TEXT );";

    public static final String CREATE_WOMEN_ACCOUNT_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_WOMEN_ACCOUNT_REGISTRATION + "("
            + KEY_REG_WOMEN_UNIQUE_ID + " TEXT NOT NULL,"
            + KEY_REG_WOMEN_USER_UNIQUE_ID + " TEXT,"
            + KEY_REG_WOMEN_NAME + " TEXT,"
            + KEY_REG_WOMEN_DATE_OF_BIRTH + " TEXT,"
            + KEY_REG_WOMEN_LAST_MENSTRUATION_DATE + " TEXT,"
            + KEY_REG_WOMEN_HEIGHT + " TEXT,"
            + KEY_REG_WOMEN_WEIGHT + " TEXT,"
            + KEY_REG_WOMEN_IMAGE_PATH + " TEXT );";

    public static final String CREATE_GIVEN_IMMUNIZATION_TABLE = "CREATE TABLE " + TABLE_GIVEN_IMMUNIZATION + "("
            + KEY_GIVEN_IMMUNIZATION_ID + " TEXT NOT NULL,"
            + KEY_GIVEN_IMMUNIZATION_CHILD_ID + " TEXT,"
            + KEY_GIVEN_IMMUNIZATION_VACCINE_ID + " TEXT,"
            + KEY_GIVEN_IMMUNIZATION_TOTAL_DOSE_NO + " TEXT,"
            + KEY_GIVEN_IMMUNIZATION_DOSE_NO + " TEXT,"
            + KEY_GIVEN_IMMUNIZATION_STARTING_DATE + " TEXT,"
            + KEY_GIVEN_IMMUNIZATION_STATUS + " TEXT,"
            + KEY_GIVEN_IMMUNIZATION_DOSES + " TEXT );";

    public static final String CREATE_CHILD_GROWTH_TABLE = "CREATE TABLE " + TABLE_CHILD_GROWTH_RECORD + "("
            + KEY_GROWTH_ID + " TEXT NOT NULL,"
            + KEY_GROWTH_CHILD_UNIQUE_ID + " TEXT,"
            + KEY_GROWTH_CHILD_WEIGHT + " TEXT,"
            + KEY_GROWTH_CHILD_AGE + " TEXT );";

    public static final String CREATE_SET_ALARM_TABLE = "CREATE TABLE " + TABLE_SET_ALARM + "("
            + KEY_SET_ALARM_ID + " TEXT UNIQUE,"
            + KEY_ALARM_UNIQUE_ID + " TEXT );";

    public static final String CREATE_ELDER_ACCOUNT_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_ELDER_ACCOUNT_REGISTRATION + "("
            + KEY_REG_ELDER_UNIQUE_ID + " TEXT NOT NULL,"
            + KEY_REG_ELDER_USER_UNIQUE_ID + " TEXT,"
            + KEY_REG_ELDER_NAME + " TEXT,"
            + KEY_REG_ELDER_DATE_OF_BIRTH + " TEXT,"
            + KEY_REG_ELDER_GENDER + " TEXT,"
            + KEY_REG_ELDER_HEIGHT + " TEXT,"
            + KEY_REG_ELDER_WEIGHT + " TEXT,"
            + KEY_REG_ELDER_OCCUPATION + " TEXT,"
            + KEY_REG_ELDER_IMAGE_PATH + " TEXT );";


    public static final String CREATE_ELDER_MEDICATION_INSERT_TABLE = "CREATE TABLE " + TABLE_ELDER_MEDICATION_INFO + "("
            + KEY_INS_ELDER_MEDICATION_UNIQUE_ID + " TEXT NOT NULL,"
            + KEY_INS_ELDER_UNIQUE_ID + " TEXT,"
            + KEY_INS_ELDER_MEDICATION_TYPE + " TEXT,"
            + KEY_INS_ELDER_MEDICATION_NAME + " TEXT,"
            + KEY_INS_ELDER_MEDICATION_STRENGTH + " TEXT,"
            + KEY_INS_ELDER_MEDICATION_TAKING_TIME + " TEXT,"
            + KEY_INS_ELDER_MEDICATION_DURATION + " TEXT );";


    public static final String CREATE_ANTENATAL_VISIT_TABLE = "CREATE TABLE " + TABLE_WOMEN_ANTENATAL_VISIT + "("
            + KEY_ANTENATAL_VISIT_ID + " TEXT NOT NULL,"
            + KEY_ANTENATAL_VISIT_USER_ID + " TEXT,"
            + KEY_ANTENATAL_VISIT_WOMEN_ID + " TEXT,"
            + KEY_ANTENATAL_VISIT_NUM + " TEXT,"
            + KEY_ANTENATAL_VISIT_TIME + " TEXT );";

    public static final String CREATE_WOMEN_TT_INFO_TABLE = "CREATE TABLE " + TABLE_WOMEN_TT_INFO + "("
            + KEY_TT_ID + " TEXT NOT NULL,"
            + KEY_TT_NUM + " INTEGER,"
            + KEY_TT_DAY_DIFFERENCE + " INTEGER,"
            + KEY_TT_TO_DO + " TEXT,"
            + KEY_TT_DETAILS + " TEXT );";

    public static final String CREATE_WOMEN_GIVEN_TT_TABLE = "CREATE TABLE " + TABLE_WOMEN_GIVEN_TT + "("
            + KEY_GIVEN_TT_ID + " TEXT NOT NULL,"
            + KEY_TT_ID + " TEXT,"
            + KEY_GIVEN_TT_WOMEN_ID + " TEXT,"
            + KEY_GIVEN_TT_TIME + " TEXT );";

    public static final String CREATE_ELDER_BLOOD_RECORD_TABLE = "CREATE TABLE " + TABLE_ELDER_BLOOD_INFO + "("
            + KEY_ELDER_BLOOD_UNIQUE_ID + " TEXT NOT NULL,"
            + KEY_ELDER_USER_UNIQUE_ID + " TEXT,"
            + KEY_ELDER_BLOOD_PRESSURE + " TEXT,"
            + KEY_ELDER_BLOOD_PRESSURE_UPPER + " TEXT,"
            + KEY_ELDER_BLOOD_PRESSURE_LOWER + " TEXT ,"
            + KEY_ELDER_BLOOD_GLUCOSE_MOLE + " TEXT,"
            + KEY_ELDER_BLOOD_GLUCOSE_MG + " TEXT ,"
            + KEY_ELDER_BLOOD_COMMENT + " TEXT,"
            + KEY_ELDER_BLOOD_RECORD_TIME + " TEXT );";


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ACCOUNT_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_ACCOUNT_REGISTRATION + "("
                + KEY_REG_USER_UNIQUE_ID + " TEXT NOT NULL,"
                + KEY_REG_USER_NAME + " TEXT,"
                + KEY_REG_USER_TYPE + " TEXT,"
                + KEY_REG_EMAIL + " TEXT UNIQUE,"
                + KEY_REG_PASSWORD + " TEXT )";

        String CREATE_VACCINE_INFO_TABLE = "CREATE TABLE " + TABLE_VACCINE_INFO + "("
                + KEY_VACCINE_UNIQUE_ID + " TEXT NOT NULL,"
                + KEY_VACCINE_NAME + " TEXT,"
                + KEY_VACCINE_TYPE + " TEXT,"
                + KEY_VACCINE_STARTING + " TEXT,"
                + KEY_DOSES + " TEXT )";

        String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_ALARM + "("
                + KEY_ALARM_UNIQUE_ID + " TEXT UNIQUE,"
                + KEY_ALARM_USER_UNIQUE_ID + " TEXT,"
                + KEY_ALARM_IMMUNIZATION_UNIQUE_ID + " TEXT,"
                + KEY_ALARM_CHILD_UNIQUE_ID + " TEXT,"
                + KEY_ALARM_TIME + " TEXT )";


        try {
            db.execSQL(CREATE_ACCOUNT_REGISTRATION_TABLE);
            db.execSQL(CREATE_VACCINE_INFO_TABLE);
            db.execSQL(CREATE_CHILD_ACCOUNT_REGISTRATION_TABLE);
            db.execSQL(CREATE_GIVEN_IMMUNIZATION_TABLE);
            db.execSQL(CREATE_CHILD_GROWTH_TABLE);
            db.execSQL(CREATE_ALARM_TABLE);
            db.execSQL(CREATE_SET_ALARM_TABLE);
            db.execSQL(CREATE_ELDER_ACCOUNT_REGISTRATION_TABLE);
            db.execSQL(CREATE_ELDER_MEDICATION_INSERT_TABLE);
            db.execSQL(CREATE_WOMEN_ACCOUNT_REGISTRATION_TABLE);
            db.execSQL(CREATE_ANTENATAL_VISIT_TABLE);
            db.execSQL(CREATE_WOMEN_TT_INFO_TABLE);
            db.execSQL(CREATE_WOMEN_GIVEN_TT_TABLE);
            db.execSQL(CREATE_ELDER_BLOOD_RECORD_TABLE);
            Log.d(TAG, "Database tables created");
        } catch (SQLException e) {
            Log.e("my_error" + TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT_REGISTRATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE_INFO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD_ACCOUNT_REGISTRATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIVEN_IMMUNIZATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD_GROWTH_RECORD);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SET_ALARM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELDER_ACCOUNT_REGISTRATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELDER_MEDICATION_INFO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WOMEN_ACCOUNT_REGISTRATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WOMEN_ANTENATAL_VISIT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WOMEN_TT_INFO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WOMEN_GIVEN_TT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELDER_BLOOD_INFO);
            onCreate(db);
        } catch (SQLException e) {

            Log.e("my_error" + TAG, e.toString());

        }


    }

    public void addAdmin(String uniqueID, String name, String type, String email, String password, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(KEY_REG_USER_UNIQUE_ID, uniqueID);
        values.put(KEY_REG_USER_NAME, name);
        values.put(KEY_REG_USER_TYPE, type);
        values.put(KEY_REG_EMAIL, email);
        values.put(KEY_REG_PASSWORD, password);

        long id = db.insert(TABLE_ACCOUNT_REGISTRATION, null, values);
        Log.d(TAG, "New user inserted into ACCOUNT REGISTRATION table: " + id);
    }

    public UserInfoModel getUserInfoFormDatabase(String email, String pass) {

        String selectQuery = "SELECT * FROM " + TABLE_ACCOUNT_REGISTRATION + " WHERE " + KEY_REG_EMAIL + " LIKE '" + email + "' AND " + KEY_REG_PASSWORD + " LIKE '" + pass + "'";

        UserInfoModel userInfoModel = new UserInfoModel();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();

            String userUniqueId = cursor.getString(cursor.getColumnIndex(KEY_REG_USER_UNIQUE_ID));
            String userName = cursor.getString(cursor.getColumnIndex(KEY_REG_USER_NAME));
            String userType = cursor.getString(cursor.getColumnIndex(KEY_REG_USER_TYPE));
            String userEmail = cursor.getString(cursor.getColumnIndex(KEY_REG_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(KEY_REG_PASSWORD));

            userInfoModel = new UserInfoModel(userUniqueId, userName, userType, userEmail, password);


        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching use from User Info table: ");

        return userInfoModel;
    }

    public UserInfoModel getUserInfoFormDatabase(String id) {

        String selectQuery = "SELECT * FROM " + TABLE_ACCOUNT_REGISTRATION + " WHERE " + KEY_REG_USER_UNIQUE_ID + " LIKE '" + id + "'";

        UserInfoModel userInfoModel = new UserInfoModel();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();

            String userUniqueId = cursor.getString(cursor.getColumnIndex(KEY_REG_USER_UNIQUE_ID));
            String userName = cursor.getString(cursor.getColumnIndex(KEY_REG_USER_NAME));
            String userType = cursor.getString(cursor.getColumnIndex(KEY_REG_USER_TYPE));
            String userEmail = cursor.getString(cursor.getColumnIndex(KEY_REG_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(KEY_REG_PASSWORD));

            userInfoModel = new UserInfoModel(userUniqueId, userName, userType, userEmail, password);


        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching use from User Info table: ");

        return userInfoModel;
    }


    public void addUserToAccountRegistration(String uniqueID, String name, String type, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REG_USER_UNIQUE_ID, uniqueID);
        values.put(KEY_REG_USER_NAME, name);
        values.put(KEY_REG_USER_TYPE, type);
        values.put(KEY_REG_EMAIL, email);
        values.put(KEY_REG_PASSWORD, password);

        long id = db.insert(TABLE_ACCOUNT_REGISTRATION, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into ACCOUNT REGISTRATION table: " + id);
    }


    public void addChildToChildAccountRegistration(String childFatherUniqueID, String childUniqueID,
                                                   String childName, String childDateOfBirth, String childGender,
                                                   String childWeight, String childImagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REG_CHILD_FATHER_UNIQUE_ID, childFatherUniqueID);
        values.put(KEY_REG_CHILD_UNIQUE_ID, childUniqueID);
        values.put(KEY_REG_CHILD_NAME, childName);
        values.put(KEY_REG_CHILD_DATE_OF_BIRTH, childDateOfBirth);
        values.put(KEY_REG_CHILD_GENDER, childGender);
        values.put(KEY_REG_CHILD_WEIGHT, childWeight);
        values.put(KEY_REG_CHILD_IMAGE_PATH, childImagePath);

        long id = db.insert(TABLE_CHILD_ACCOUNT_REGISTRATION, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Child inserted into CHILD ACCOUNT REGISTRATION table: " + id);

    }


    public Boolean addElderToElderAccountRegistration(String elderUniqueID, String elderUserUniqueID, String elderName,
                                                      String elderDateOfBirth, String elderGender, String elderHeight,
                                                      String elderWeight, String elderOccupation, String elderImagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REG_ELDER_UNIQUE_ID, elderUniqueID);
        values.put(KEY_REG_ELDER_USER_UNIQUE_ID, elderUserUniqueID);
        values.put(KEY_REG_ELDER_NAME, elderName);
        values.put(KEY_REG_ELDER_DATE_OF_BIRTH, elderDateOfBirth);
        values.put(KEY_REG_ELDER_GENDER, elderGender);
        values.put(KEY_REG_ELDER_HEIGHT, elderHeight);
        values.put(KEY_REG_ELDER_WEIGHT, elderWeight);
        values.put(KEY_REG_ELDER_OCCUPATION, elderOccupation);
        values.put(KEY_REG_ELDER_IMAGE_PATH, elderImagePath);

        long id = db.insert(TABLE_ELDER_ACCOUNT_REGISTRATION, null, values);
        db.close(); // Closing database connection


        if (id > 0) {
            Log.d(TAG, "New Elder Person inserted into ELDER ACCOUNT REGISTRATION table: " + id);
            return true;
        } else {
            Log.d(TAG, "New Elder Person not inserted into ELDER ACCOUNT REGISTRATION table: " + id);
            return false;
        }

    }


    public Boolean addMedicationToMedicationInsertTable(String medicationUniqueID, String elderUniqueID,
                                                        String medicationType, String medicationName, String medicationStrength,
                                                        String medicationTakingTime, String medicationDuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INS_ELDER_MEDICATION_UNIQUE_ID, medicationUniqueID);
        values.put(KEY_INS_ELDER_UNIQUE_ID, elderUniqueID);
        values.put(KEY_INS_ELDER_MEDICATION_TYPE, medicationType);
        values.put(KEY_INS_ELDER_MEDICATION_NAME, medicationName);
        values.put(KEY_INS_ELDER_MEDICATION_STRENGTH, medicationStrength);
        values.put(KEY_INS_ELDER_MEDICATION_TAKING_TIME, medicationTakingTime);
        values.put(KEY_INS_ELDER_MEDICATION_DURATION, medicationDuration);

        long id = db.insert(TABLE_ELDER_MEDICATION_INFO, null, values);
        db.close(); // Closing database connection


        if (id > 0) {
            Log.d(TAG, "New Medication inserted into MEDICATION INSERT table: " + id);
            return true;
        } else {
            Log.d(TAG, "New Medication not inserted into MEDICATION INSERT table: " + id);
            return false;
        }
    }


    public boolean addGivenImmunizationToDatabase(String givenImmunizationId, String givenChildId,
                                                  String givenVaccineId,
                                                  String totalDosesNo, String givenDoseNo,
                                                  String immunizationStartingDate, String immunizationStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(KEY_GIVEN_IMMUNIZATION_ID, givenImmunizationId);
            values.put(KEY_GIVEN_IMMUNIZATION_CHILD_ID, givenChildId);
            values.put(KEY_GIVEN_IMMUNIZATION_VACCINE_ID, givenVaccineId);
            values.put(KEY_GIVEN_IMMUNIZATION_TOTAL_DOSE_NO, totalDosesNo);
            values.put(KEY_GIVEN_IMMUNIZATION_DOSE_NO, givenDoseNo);
            values.put(KEY_GIVEN_IMMUNIZATION_STARTING_DATE, immunizationStartingDate);
            values.put(KEY_GIVEN_IMMUNIZATION_STATUS, immunizationStatus);
            //        values.put(KEY_GIVEN_IMMUNIZATION_DOSES, givenDoses);
            long id = db.insert(TABLE_GIVEN_IMMUNIZATION, null, values);

            Log.d(TAG, "New Given Immunization inserted into Given Immunization table: " + id);
            return true;

        } catch (SQLException e) {
            Log.d(TAG, e.toString());
            return false;

        } finally {
            db.close(); // Closing database connection
        }
    }

    public boolean addChildGrowth(String growthId, String childUniqueId, String childWeight, String childAge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROWTH_ID, growthId);
        values.put(KEY_GROWTH_CHILD_UNIQUE_ID, childUniqueId);
        values.put(KEY_GROWTH_CHILD_WEIGHT, childWeight);
        values.put(KEY_GROWTH_CHILD_AGE, childAge);

        long id = db.insert(TABLE_CHILD_GROWTH_RECORD, null, values);
        db.close();
        Log.d(TAG, "New growth inserted into GROWTH RECORD table: " + id);

        if (id > 0) {
            Log.d(TAG, "New growth store into GROWTH RECORD table: " + id);
            return true;
        } else {
            Log.d(TAG, "New growth not store into GROWTH RECORD table: " + id);
            return false;
        }
    }


    public void addDataToAlarmTable(String alarmUniqueId, String userUniqueId,
                                    String childUniqueId, String immunizationID, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ALARM_UNIQUE_ID, alarmUniqueId);
        values.put(KEY_ALARM_USER_UNIQUE_ID, userUniqueId);
        values.put(KEY_ALARM_CHILD_UNIQUE_ID, childUniqueId);
        values.put(KEY_ALARM_IMMUNIZATION_UNIQUE_ID, immunizationID);
        values.put(KEY_ALARM_TIME, time);

        long id = db.insert(TABLE_ALARM, null, values);
        db.close();
        Log.d(TAG, "New data inserted into Alarm Table: " + id);


    }


    public void addAlarmToSetAlarmTable(String alarmUniqueId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SET_ALARM_ID, alarmUniqueId);
        values.put(KEY_ALARM_UNIQUE_ID, alarmUniqueId);

        long id = db.insert(TABLE_SET_ALARM, null, values);
        db.close();
        Log.d(TAG, "New data inserted into SET Alarm Table: " + id);


    }


    public void addWomenToWomenAccountRegistration(String womenUserId, String userUniqueID,
                                                   String womenName, String womenDateOfBirth,
                                                   String womenDateOfMenstruation,
                                                   String womenHeight,
                                                   String womenWeight, String womenImagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REG_WOMEN_UNIQUE_ID, womenUserId);
        values.put(KEY_REG_WOMEN_USER_UNIQUE_ID, userUniqueID);
        values.put(KEY_REG_WOMEN_NAME, womenName);
        values.put(KEY_REG_WOMEN_DATE_OF_BIRTH, womenDateOfBirth);
        values.put(KEY_REG_WOMEN_LAST_MENSTRUATION_DATE, womenDateOfMenstruation);
        values.put(KEY_REG_WOMEN_HEIGHT, womenHeight);
        values.put(KEY_REG_WOMEN_WEIGHT, womenWeight);
        values.put(KEY_REG_WOMEN_IMAGE_PATH, womenImagePath);

        long id = db.insert(TABLE_WOMEN_ACCOUNT_REGISTRATION, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New women inserted into WOMEN ACCOUNT REGISTRATION table: " + id);

    }


    public boolean addAntenatalVisitToDatabase(String antenatalVisitId, String antenatalVisitUserId,
                                               String antenatalVisitWomenId,
                                               String antenatalVisitNum, String antenatalVisitTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(KEY_ANTENATAL_VISIT_ID, antenatalVisitId);
            values.put(KEY_ANTENATAL_VISIT_USER_ID, antenatalVisitUserId);
            values.put(KEY_ANTENATAL_VISIT_WOMEN_ID, antenatalVisitWomenId);
            values.put(KEY_ANTENATAL_VISIT_NUM, antenatalVisitNum);
            values.put(KEY_ANTENATAL_VISIT_TIME, antenatalVisitTime);
            long id = db.insert(TABLE_WOMEN_ANTENATAL_VISIT, null, values);

            Log.d(TAG, "New ANTENATAL VISIT inserted into Antenatal Visit Table: " + id);
            return true;

        } catch (SQLException e) {
            Log.d(TAG, e.toString());
            return false;

        } finally {
            db.close(); // Closing database connection
        }
    }


    public boolean addGivenTtToDatabase(String givenTtId, String ttId, String womenId, String givenDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(KEY_GIVEN_TT_ID, givenTtId);
            values.put(KEY_TT_ID, ttId);
            values.put(KEY_GIVEN_TT_WOMEN_ID, womenId);
            values.put(KEY_GIVEN_TT_TIME, givenDate);
            Log.d("ADD_GIVEN_TT", values.toString());
            long id = db.insert(TABLE_WOMEN_GIVEN_TT, null, values);
            Log.d(TAG, "New GIVEN TT inserted into Given Tt Table: " + id);
            return true;
        } catch (SQLException e) {
            Log.d(TAG, e.toString());
            return false;

        } finally {
            db.close(); // Closing database connection
        }
    }


    // Update child photo
    public boolean updateChildImage(String childUniqueId, String childImagePath) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REG_CHILD_IMAGE_PATH, childImagePath);

        long id = db.update(TABLE_CHILD_ACCOUNT_REGISTRATION, values, KEY_REG_CHILD_UNIQUE_ID + " =? ", new String[]{childUniqueId});
        if (id > 0) {
            return true;
        } else {
            return false;
        }

    }


    // DELETE Child from database to childList
    public boolean deleteChild(String childUniqueId) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = db.delete(TABLE_CHILD_ACCOUNT_REGISTRATION, KEY_REG_CHILD_UNIQUE_ID + " =? ", new String[]{childUniqueId});
        if (id > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }


    }

    public boolean deleteTTInfo(String womenId) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = db.delete(TABLE_WOMEN_GIVEN_TT, KEY_GIVEN_TT_WOMEN_ID + " =? ", new String[]{womenId});
        if (id > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }


    }


    public boolean checkUserAvailability(String email) {

        String selectQuery = "SELECT * FROM " + TABLE_ACCOUNT_REGISTRATION + " WHERE " + KEY_REG_EMAIL + " = '" + email + "'";
        boolean isUserAvailable = true;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            isUserAvailable = false;
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "checking user availability");

        return isUserAvailable;
    }

    public void deleteSetAlarm() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {


            db.execSQL("delete from " + TABLE_SET_ALARM);
            Log.d(TAG, "deleting all set alarm");


        } catch (SQLException e) {
            Log.d(TAG, e.toString());
        } finally {
            db.close();
        }
    }

}
