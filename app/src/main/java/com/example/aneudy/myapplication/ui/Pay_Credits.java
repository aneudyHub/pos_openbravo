package com.example.aneudy.myapplication.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aneudy.myapplication.Insertar;
import com.example.aneudy.myapplication.Models.Receipt;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.Printer.Progress;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.ResponseMSJ;
import com.example.aneudy.myapplication.Printer.Zebraprint;
import com.example.aneudy.myapplication.provider.Configs;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pay_Credits extends AppCompatActivity implements Progress {

    public TextView Balance;
    public EditText Amount;
    Button pay;
    Button cancel;
    ProgressDialog progress;
    String ID;
    Double BALANCE;
    String CLIENT;
    AlertDialog.Builder payment_alert;
    private String PaymentMode;
    AlertDialog PAY_alertDialog;
    ProgressDialog mPrinterProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abonar_layout);
        ID=getIntent().getExtras().getString("ID");
        BALANCE =getIntent().getExtras().getDouble("BALANCE");
        CLIENT = getIntent().getExtras().getString("CLIENT");
        prepareView();
    }

    public void prepareView(){
        Balance=(TextView)findViewById(R.id.Abonar_Balance);
        Balance.setText(BALANCE.toString());
        Amount = (EditText)findViewById(R.id.Abonar_Amount);
        pay =(Button)findViewById(R.id.Abonar_Process);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Amount.getText().toString().isEmpty()){
                    showAlert("THE AMOUNT COULD NOT BE EMPTY");
                    return;
                }


                Double t= Double.parseDouble(Amount.getText().toString());

                if(t==0){
                    showAlert("THE AMOUNT COULD NOT BE ZERO");
                    return;
                }

                if(t>BALANCE){
                    showAlert("THE AMOUNT COULD NOT BE HIGHER THAN THE BALANCE");
                    return;
                }

                show_payment();
            }
        });
        cancel = (Button)findViewById(R.id.Abonar_Cancelar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPrinterProgress = new ProgressDialog(this);
        mPrinterProgress.setTitle("Printing...");
        mPrinterProgress.setCancelable(false);
    }

    public void show_payment(){
        LayoutInflater inflater = this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.payment_options, null);

        payment_alert = new AlertDialog.Builder(this);
        payment_alert.setTitle("Payment Options");
        payment_alert.setView(mView);
        payment_alert.setCancelable(true);
        CardView Cheque = (CardView) mView.findViewById(R.id.Payments_Check);
        CardView Cash = (CardView) mView.findViewById(R.id.Payments_Cash);
        CardView Credit = (CardView) mView.findViewById(R.id.Payments_Credit);
        Credit.setVisibility(View.GONE);


        Cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMode="cheque";

                //showConfirm();
                pay_process();
            }
        });

        Cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMode="cash";
                pay_process();
                //showConfirm();
            }
        });

        Credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMode="debt";

                //showConfirm();
            }
        });

        PAY_alertDialog = payment_alert.create();
        PAY_alertDialog.show();

    }
    public void showAlert(String mensaje){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(mensaje);

        alertDialogBuilder.setPositiveButton("salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void pay_process(){
        final Insertar i = new Insertar();
        i.setMoney(Configs.MONEY);
        i.setPerson(Configs.ID_USER);
        i.setPrimero(ID);
        i.setTipopago(PaymentMode);
        i.setTotal(Double.parseDouble(Amount.getText().toString()) * -1);


        showProgress("Processing payment");
        ApiClient
                .getClient()
                .abonar(i)
                .enqueue(new Callback<ResponseMSJ>() {
                    @Override
                    public void onResponse(Call<ResponseMSJ> call, Response<ResponseMSJ> response) {
                        progress.dismiss();
                        PAY_alertDialog.dismiss();
                        if(response.isSuccessful()){


                            Double t = BALANCE + i.getTotal();
                            Receipt receipt = new Receipt(response.body().getReceipt(),Double.parseDouble(Amount.getText().toString()),CLIENT,Configs.USER,response.body().getDate(),i.getTipopago(),t);


                            Zebraprint zebraprint = new Zebraprint(Pay_Credits.this,receipt,Zebraprint.TAG_PAGO,Pay_Credits.this);
                            zebraprint.probarlo();
                            //setResult(RESULT_OK);
                            //finish();
                        }else{
                            try {

                                Log.e("insertar error",response.errorBody().string());

                                showAlert("ERROR FROM SERVER");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMSJ> call, Throwable t) {
                        Log.e("error pay",t.getMessage());
                    }
                });
    }

    public void showProgress(String title){
        progress = new ProgressDialog(this);
        progress.setTitle(title);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void showProgressPrint(Boolean b) {
        if(b){
            mPrinterProgress.show();
        }else{
            mPrinterProgress.dismiss();
        }
    }

    @Override
    public void error(String msj) {
        if(mPrinterProgress!=null)
            mPrinterProgress.dismiss();

        Log.e("error printer",msj);
        showAlert(msj);
    }

    @Override
    public void finishPrint() {
        setResult(RESULT_OK);
        finish();
    }
}
