package com.example.jonmid.contactosbasededatos.Views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonmid.contactosbasededatos.ContactsActivity;
import com.example.jonmid.contactosbasededatos.Helpers.SqliteHelper;
import com.example.jonmid.contactosbasededatos.Models.Contact;
import com.example.jonmid.contactosbasededatos.R;
import com.example.jonmid.contactosbasededatos.Utilities.Constants;

public class DeleteActivity extends AppCompatActivity {
    Button buttonEliminar;
    TextView textViewid;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewEmail;
    SqliteHelper sqliteHelper;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        buttonEliminar = (Button) findViewById(R.id.btn_Aceptar);

        textViewid = (TextView) findViewById(R.id.delete_id);
        textViewName = (TextView) findViewById(R.id.delete_name);
        textViewPhone = (TextView) findViewById(R.id.delete_phone);
        textViewEmail = (TextView) findViewById(R.id.delete_Email);
        sqliteHelper = new SqliteHelper(this, "db_users", null, 1);

        textViewid.setText(Integer.toString(getIntent().getExtras().getInt("id")));
        textViewName.setText(getIntent().getExtras().getString("name"));
        textViewPhone.setText(getIntent().getExtras().getString("phone"));
        textViewEmail.setText(getIntent().getExtras().getString("email"));

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContact();
            }
        });

    }

    public void onClickShowPrincipal() {

        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void deleteContact() {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        int id = Integer.parseInt(textViewid.getText().toString());


       Integer datoEliminado=db.delete(Constants.TABLA_NAME_USERS, Constants.TABLA_FIELD_ID + "=" + id, null);
        db.close();

       if (datoEliminado !=0){
           Toast.makeText(this,"El usuario: "+textViewName.getText()+" se elimino con exito",Toast.LENGTH_SHORT).show();

           onClickShowPrincipal();

       }else {
           Toast.makeText(this,"El usuario: "+textViewName.getText()+" no existe en la base de datos",Toast.LENGTH_SHORT).show();
           onClickShowPrincipal();
       }



    }


    public void Regresar() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

}
