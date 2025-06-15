package com.s23010383.agralab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                boolean isInserted = myDb.addUser(username, password);

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data Saved Successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Saved. Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}