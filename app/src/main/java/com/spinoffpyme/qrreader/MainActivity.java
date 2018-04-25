package com.spinoffpyme.qrreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spinoffpyme.qrreader.Database.UserRepository;
import com.spinoffpyme.qrreader.Local.UserDataSource;
import com.spinoffpyme.qrreader.Local.UserDatabase;
import com.spinoffpyme.qrreader.Model.User;

public class MainActivity extends AppCompatActivity {
    Button btnEscanear;
    Button btnCargarDatos;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEscanear=findViewById(R.id.btnScannear);
        btnCargarDatos=findViewById(R.id.btnCargar);
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();

        //Cargamos datos en la bbdd de prueba
        UserDatabase userDatabase = UserDatabase.getmInstance(this);//create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));

        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScannerActivity.class));

            }
        });

        btnCargarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference datos=db.getReference(auth.getCurrentUser().getUid());
                datos.child("Evento1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        new GuardarDatos().execute(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private class GuardarDatos extends AsyncTask<DataSnapshot,Void,Void>{

        @Override
        protected Void doInBackground(DataSnapshot... dataSnapshots) {
            Log.i("Datos",dataSnapshots[0].toString());
            for (DataSnapshot dataSnapshot1: dataSnapshots[0].getChildren()) {
                User user = dataSnapshot1.getValue(User.class);
                userRepository.inserUser(user);
                Log.i("Datos",user.getName());
            }

/*
                                for (DataSnapshot dataSnapshot1: dataSnapshots[0].getChildren()) {
                                    Log.i("Datos", dataSnapshot1.toString());
                                    User user = dataSnapshot1.getValue(User.class);
                                    userRepository.inserUser(user);
                                    //imagesDir.add(imageSnapshot.child("address").getValue(String.class));
                                    Log.i("Datos",user.getName());
                                }
*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this,"Datos cargados correctamente",Toast.LENGTH_SHORT).show();
        }
    }
}
