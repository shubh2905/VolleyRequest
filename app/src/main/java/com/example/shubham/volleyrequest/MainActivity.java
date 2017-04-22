package com.example.shubham.volleyrequest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPassword, inputCPassword, inputPhone, inputAddress;
    private static final String REGISTER_URL = "http://139.59.68.74:4000/api/users";
    private static final String ROOT_URL = "http://139.59.68.74:4000/";
    private Button btnRegister;
    private Spinner spnGender, spnBloodGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.register);
        inputPhone = (EditText) findViewById(R.id.phone);
        btnRegister = (Button) findViewById(R.id.register);


        /** Spinner **/
        spnGender = (Spinner) findViewById(R.id.gender);
        spnBloodGroup = (Spinner) findViewById(R.id.bloodgroup);


        ArrayAdapter<CharSequence> adapterGender;
        ArrayAdapter<CharSequence> adapterBloodGroup;

        String[] genderArr = {"Male", "Female"};
        String[] bgArr = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};


        adapterGender = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, genderArr);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapterGender);

        adapterBloodGroup = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, bgArr);
        adapterBloodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodGroup.setAdapter(adapterBloodGroup);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // registerUser();
                insertUser();
            }
        });
    }

    public void insertUser() {

        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

        mDialog.setTitle("Signing in..");
        mDialog.setMessage("Authenticating..");
        mDialog.show();

        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String role = "user";
        String status = "true";
        String address = inputAddress.getText().toString().trim();
        String gender = spnGender.getSelectedItem().toString().trim();
        String bloodgroup = spnBloodGroup.getSelectedItem().toString().trim();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.insertUser(

                //Passing the values by getting it from editTexts
                name, password, email, gender, bloodgroup, address, role, status,


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;
                        mDialog.dismiss();

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error response", "RESULT= " + error.toString());
                    }
                }
        );

    }
}
