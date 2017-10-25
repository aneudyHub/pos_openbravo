package com.example.aneudy.myapplication.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aneudy.myapplication.Models.User;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.Utils.eASimpleSHA1;
import com.example.aneudy.myapplication.provider.HelperTareas;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 100;
    EditText usuario;
    EditText password;
    Button iniciar;
    ProgressDialog progressDialog;
    String IMEI;

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE},REQUEST_CODE_ASK_PERMISSIONS);

            return true;
        }
        getIMEI();
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Log.e("permision", String.valueOf(PackageManager.PERMISSION_GRANTED));
                    getIMEI();
                } else {
                    // Permission Denied
                    Toast.makeText(Login.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getIMEI(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        IMEI=telephonyManager.getDeviceId();
        Log.e("IMEI",IMEI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        runtime_permissions();

        usuario=(EditText)findViewById(R.id.Login_User);
        password=(EditText)findViewById(R.id.Login_Password);
        iniciar=(Button)findViewById(R.id.Login_Btn);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    iniciar();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showConfirm(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage("Server Error");
        alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    iniciar();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alert.create();
        alert.show();
    }



    public void iniciar() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Login...");
        progressDialog.show();

        String pass=  eASimpleSHA1.SHA1(password.getText().toString());
        Log.e("sha1",pass);
        Log.e("user",usuario.getText().toString());
        User user = new User(usuario.getText().toString(),IMEI,pass);
        ApiClient
                .getClient()
                .Login(user.getName(),user.getPassword(),user.getImei())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){

                            Log.e("response",response.body().getId());
                            HelperTareas helperTareas= new HelperTareas(Login.this);
                            Log.e("response",response.body().getId()+","+response.body().getName()+","+response.body().getMoney());
                            helperTareas.insertar_login(response.body().getId(),response.body().getName(),response.body().getMoney());

                            startActivity(new Intent(Login.this,Clients.class));

                        }else{
                            Log.e("error response",response.message());
                            Toast.makeText(Login.this,"Check UserName or Password is correct",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("error",t.getMessage());
                        showConfirm();
                    }
                });
    }
}
