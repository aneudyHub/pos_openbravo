package com.example.aneudy.myapplication.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aneudy.myapplication.Models.BalanceResponse;
import com.example.aneudy.myapplication.Models.Receipt;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.Printer.Progress;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.Utils.Memory;
import com.example.aneudy.myapplication.Printer.Zebraprint;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Operations extends AppCompatActivity implements Progress{

    private String ID;
    private String Client_Name;
    private String Client_Tax;
    public TextView Name;
    public TextView TaxID;
    public TextView Balance;
    public Button Pay;
    public Button Reprint;
    public Button Shop;
    public Double balance;
    ProgressDialog mPrinterProgress;

    public ProgressDialog progress;

    @Override
    protected void onResume() {
        super.onResume();
        load_balance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);
        ID = Memory.client.getId();
        Client_Name =Memory.client.getName();
        Client_Tax = Memory.client.getTAID();
        prepare_view();
        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            ID = getIntent().getExtras().getString("ID");
            Client_Name = getIntent().getExtras().getString("NAME");
            Client_Tax = getIntent().getExtras().getString("TAX");
        }else{
            Log.e("id",getIntent().getExtras().getString("ID"));
        }*/
        //prepare_view();
        //load_balance();


    }


    public void showAlert(String mensaje){
        progress.dismiss();
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

    private void prepare_view(){
        getSupportActionBar().setTitle(Memory.client.getName());
        mPrinterProgress = new ProgressDialog(this);
        mPrinterProgress.setTitle("Printing...");
        mPrinterProgress.setCancelable(false);
        Name = (TextView)findViewById(R.id.Operations_ClientName);
        Name.setText(Client_Name);
        TaxID = (TextView)findViewById(R.id.Operations_ClientTaxID);
        TaxID.setText(Client_Tax);
        Balance = (TextView)findViewById(R.id.Operations_ClienteBalance);
        Balance.setText("0.00");
        Pay = (Button)findViewById(R.id.Operations_Pay);
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(balance==0.00){
                    showAlert("NOTHING TO PAY");
                    return;
                }

                Intent intent=new Intent(Operations.this,Pay_Credits.class);
                intent.putExtra("ID",ID);
                intent.putExtra("CLIENT",Memory.client.getName());
                intent.putExtra("BALANCE",balance);
                startActivity(intent);
            }
        });

        Reprint =(Button)findViewById(R.id.Operations_Reprint);
        Reprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reprint_receipt();
            }
        });
        Shop = (Button)findViewById(R.id.Operations_Shop);
        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Operations.this,MainActivity.class);
                intent.putExtra("ID",ID);
                intent.putExtra("NAME",Client_Name);
                intent.putExtra("TAX",Client_Tax);
                startActivityForResult(intent,1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode == RESULT_OK){
                ID =Memory.client.getId();
                Client_Name = Memory.client.getName();
                Client_Tax = Memory.client.getTAID();
                prepare_view();
            }
        }

        if(requestCode==200 && resultCode== RESULT_OK){

        }
    }

    private void reprint_receipt(){
        showProgress("Loading data");
        ApiClient
                .getClient()
                .reprint(ID)
                .enqueue(new Callback<Receipt>() {
                    @Override
                    public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                        progress.dismiss();
                        if(response.isSuccessful()){
                            Receipt receipt = response.body();

                            //Log.e("recipt",response.body().getCashier());
                            //Log.e("detail",response.body().getDetails().get(0).getNAME());
                            if(response.body().getPayMethod().equalsIgnoreCase("debtpaid")){
                                receipt.setPending(balance);
                                receipt.setTotal(receipt.getTotal() * -1);
                                Zebraprint zebraprint = new Zebraprint(Operations.this,receipt,Zebraprint.TAG_PAGO_REIMPRESION,Operations.this);
                                zebraprint.probarlo();
                            }else{
                                Zebraprint zebraprint = new Zebraprint(Operations.this,receipt,Zebraprint.TAG_REIMPRESION,Operations.this);
                                zebraprint.probarlo();
                            }


                        }else{
                            try {
                                Log.e("error 1 reprint",response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Receipt> call, Throwable t) {
                        Log.e("error reprint",t.getMessage());
                        progress.dismiss();
                    }
                });
    }



    private void load_balance(){
        showProgress("Loading data");
        ApiClient
                .getClient()
                .getBalance(ID)
                .enqueue(new Callback<BalanceResponse>() {
                    @Override
                    public void onResponse(Call<BalanceResponse> call, Response<BalanceResponse> response) {
                        if(response.isSuccessful()){
                            /*if(response.body().getBalance()==null){
                                response.body().setBalance(0.0);
                            }*/

                            Log.e("BALANCE=>>",String.valueOf(response.body().getBalance()));

                            Balance.setText(response.body().getBalance().toString());
                            balance=response.body().getBalance();
                        }else{
                            try {
                                Log.e("error 1 balance",response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<BalanceResponse> call, Throwable t) {
                        Log.e("error balance",t.getMessage());
                        progress.dismiss();
                    }
                });
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
        mPrinterProgress.dismiss();
    }
}
