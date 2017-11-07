package com.example.jonmid.contactosbasededatos.Views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonmid.contactosbasededatos.ContactsActivity;
import com.example.jonmid.contactosbasededatos.Helpers.SqliteHelper;
import com.example.jonmid.contactosbasededatos.R;
import com.example.jonmid.contactosbasededatos.Utilities.Constants;

public class SearchContactActivity extends AppCompatActivity {

    TextView textViewParam;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewEmail;
    EditText edtxtName, edtxtphone, edtxtEmail;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);

        textViewParam = (TextView) findViewById(R.id.id_tv_search_param_name);
        textViewName = (TextView) findViewById(R.id.id_tv_search_name);
        textViewPhone = (TextView) findViewById(R.id.id_tv_search_phone);
        textViewEmail = (TextView) findViewById(R.id.id_tv_search_email);


        edtxtName = (EditText) findViewById(R.id.id_et_name);
        edtxtphone = (EditText) findViewById(R.id.id_et_phone);
        edtxtEmail = (EditText) findViewById(R.id.id_et_email);


        sqliteHelper = new SqliteHelper(this, "db_users", null, 1);
    }

    public void onClickSearchContact(View view) {
        searchContact();
    }

    public void searchContact() {
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        String[] params = {textViewParam.getText().toString()};
        String[] fields = {Constants.TABLA_FIELD_ID, Constants.TABLA_FIELD_NAME, Constants.TABLA_FIELD_PHONE, Constants.TABLA_FIELD_EMAIL};


        try {
            Cursor cursor = db.query(Constants.TABLA_NAME_USERS, fields, Constants.TABLA_FIELD_NAME + "=?", params, null, null, null);
            cursor.moveToFirst();

            textViewName.setText(cursor.getString(1));
            textViewPhone.setText(cursor.getString(2));
            textViewEmail.setText(cursor.getString(3));


            edtxtName.setText(cursor.getString(1));
            edtxtphone.setText(cursor.getString(2));
            edtxtEmail.setText(cursor.getString(3));
            id = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "Nombre del contacto no encontrado", Toast.LENGTH_SHORT).show();
        }


    }

    public void updateUsers(View v) {

        SQLiteDatabase bdc = sqliteHelper.getReadableDatabase();

        bdc.execSQL("UPDATE users SET name= '" + edtxtName.getText().toString() +
                "', phone= " + Integer.parseInt(edtxtphone.getText().toString()) +
                ", email = '" + edtxtEmail.getText().toString() +
                "' WHERE id = " + id);

        Toast.makeText(this, "Todos los datos fueron actualizados con exito",
                Toast.LENGTH_LONG);

        goBack();

    }

    public void goBack() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    int id;
}
