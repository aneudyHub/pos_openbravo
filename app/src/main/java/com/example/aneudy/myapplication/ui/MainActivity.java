package com.example.aneudy.myapplication.ui;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;

import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AutoCompleteTextView;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aneudy.myapplication.Adapter.DetalleProductos;
import com.example.aneudy.myapplication.Adapter.SearchItem_Adapter;


import com.example.aneudy.myapplication.AlertBuilder;
import com.example.aneudy.myapplication.Insertar;
import com.example.aneudy.myapplication.Models.Receipt;
import com.example.aneudy.myapplication.NET.ApiClient;
import com.example.aneudy.myapplication.NET.ApiInterface;
import com.example.aneudy.myapplication.Product;
import com.example.aneudy.myapplication.ProductosResponse;
import com.example.aneudy.myapplication.R;
import com.example.aneudy.myapplication.ResponseMSJ;
import com.example.aneudy.myapplication.Utils.Zebraprint;
import com.example.aneudy.myapplication.provider.Configs;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements AlertBuilder.NoticeDialogListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    //private AdaptadorTarea adaptador;

    private String Client_Name;
    private DetalleProductos mainAdp;
    private Product selectedProduct;
    AlertDialog.Builder payment_alert;
    ProgressDialog server_prog;

    AlertDialog PAY_alertDialog;

    ListView listView;


    EditText CantidadProducto;

    RecyclerView DetallesFact;

    AlertDialog.Builder builderMateriale;
    AlertDialog alertMateriale;

    AutoCompleteTextView SearchItem;


    private SearchView mSearchView;


    ImageButton BtnAdd;
    AlertDialog.Builder List_Products_Dialog;

    public static TextView Monto;
    public static TextView Tax;
    public static TextView MontoGeneral;
    public String ClienteID;
    public String Client_Tax;
    public String PaymentMode;

    ImageView ScanQR;

    ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        getIntent().putExtra("ID",ClienteID);
        getIntent().putExtra("NAME",Client_Name);
        getIntent().putExtra("TAX",Client_Tax);
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);

        ClienteID=getIntent().getExtras().getString("ID");
        Client_Name=getIntent().getExtras().getString("NAME");
        Client_Tax= getIntent().getExtras().getString("TAX");
        //Monto =(TextView)findViewById(R.id.MontoF);
        //Tax =(TextView)findViewById(R.id.TaxF);
        MontoGeneral =(TextView)findViewById(R.id.MontoGeneral);



        ScanQR=(ImageView)findViewById(R.id.ScanQR);
        ScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


        List_Products_Dialog = new AlertDialog.Builder(getApplicationContext());
        List_Products_Dialog.setTitle("Lista de Productos");
        mainAdp = new DetalleProductos(getApplicationContext(),R.layout.lista_materiales);

        CantidadProducto =(EditText)findViewById(R.id.Cantidad);

        DetallesFact =(RecyclerView) findViewById(R.id.DetalleR);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        DetallesFact.setLayoutManager(layoutManager);




        BtnAdd=(ImageButton)findViewById(R.id.BtnList_Products);
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int Cantidad=Integer.parseInt(CantidadProducto.getText().toString());

                int count=0;
                if (Cantidad > 0) {

                        for (int x = 0; x < mainAdp.items.size(); x++) {
                            if (mainAdp.items.get(x).getID() == selectedProduct.getID()) {
                                count++;
                            }
                        }

                        if (count == 0) {
                            selectedProduct.setCantidad(Cantidad);
                            mainAdp.items.add(selectedProduct);
                            DetallesFact.setAdapter(mainAdp);
                            mainAdp.notifyDataSetChanged();

                        } else {
                            //showAlert("Este Materiale ya existe....")
                            // ;

                            for (int x = 0; x < mainAdp.items.size(); x++) {
                                if (mainAdp.items.get(x).getID() == selectedProduct.getID()) {
                                    mainAdp.items.get(x).setCantidad(mainAdp.items.get(x).getCantidad() + Cantidad);
                                    DetallesFact.setAdapter(mainAdp);
                                    mainAdp.notifyDataSetChanged();
                                }
                            }
                        }
                }

                mainAdp.update();

                SearchItem.setText("");
                CantidadProducto.setText("");
                SearchItem.requestFocus();

                Log.e("agregar","ok");

            }
        });





        builderMateriale = new AlertDialog.Builder(MainActivity.this);
        final EditText buscarMateriales = new EditText(MainActivity.this);

        listView = new ListView(MainActivity.this);

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(buscarMateriales);
        layout.addView(listView);
        builderMateriale.setView(layout);
        alertMateriale = builderMateriale.create();
        //lista de clientes



        SearchItem=(AutoCompleteTextView)findViewById(R.id.SearchItem);
        SearchItem.setThreshold(1);

        showLoad("Loading Products");

        load_Productos();
    }

    private void load_Productos(){

        ApiClient
                .getClient()
                .getProductos1()
                .enqueue(new Callback<ProductosResponse>() {
                    @Override
                    public void onResponse(Call<ProductosResponse> call, Response<ProductosResponse> response) {
                        //Log.e("status",response.headers().get("Status"));

                        progressDialog.dismiss();
                        if(response.isSuccessful()){

                            if(response.body().getProducts().size()==0){
                                showAlert("NO PRODUCTS ON SCOPE");
                                return;
                            }
                            for (Product product:response.body().getProducts())
                            {
                                Log.e("pro",product.getNAME());
                                Log.e("tax",product.getTAXCAT());
                            }

                            final SearchItem_Adapter searchItem_adapter= new SearchItem_Adapter(getApplicationContext(),R.layout.autocomplete_search,response.body().getProducts());
                            SearchItem.setAdapter(searchItem_adapter);
                            Log.e("adp","ok");
                            SearchItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.e("position",String.valueOf(id));
                                    selectedProduct = searchItem_adapter.getItem(position);
                                    Log.e("name",selectedProduct.getID());
                                    CantidadProducto.setText("1");
                                }
                            });
                        }else{
                            try {
                                Log.e("error productos",response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("Message");
                            alert.setMessage("Products could not be loaded");
                            alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    load_Productos();
                                }
                            });
                            alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            AlertDialog alertDialog = alert.create();
                            alert.show();
                            Log.e("error productos",response.errorBody().toString());
                        }


                    }

                    @Override
                    public void onFailure(Call<ProductosResponse> call, Throwable t) {
                        Log.e("error",t.getMessage());
                        progressDialog.dismiss();
                        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setTitle("Message");
                        alert.setMessage("Products could not be loaded");
                        alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                load_Productos();
                            }
                        });
                        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alertDialog = alert.create();
                        alert.show();
                    }
                });
    }

    private void showLoad(String msj) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msj);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void showAlert(String mensaje){
        server_prog.dismiss();
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

    private void agregarToolbar() {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.main_appbar);
        //setSupportActionBar(toolbar);
    }

    private void Find_Cliente(String query){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.pay:

                if(mainAdp.items.size()<=0){
                    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Message");
                    alert.setMessage("No Products to Pay");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alert.show();

                }else {


                    LayoutInflater inflater = this.getLayoutInflater();
                    View mView = inflater.inflate(R.layout.payment_options, null);

                    payment_alert = new AlertDialog.Builder(this);
                    payment_alert.setTitle("Payment Options");
                    payment_alert.setView(mView);
                    payment_alert.setCancelable(true);
                    CardView Cheque = (CardView) mView.findViewById(R.id.Payments_Check);
                    CardView Cash = (CardView) mView.findViewById(R.id.Payments_Cash);
                    CardView Credit = (CardView) mView.findViewById(R.id.Payments_Credit);


                    Cheque.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PaymentMode="cheque";
                            PAY_alertDialog.dismiss();
                            showConfirm();
                        }
                    });

                    Cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PaymentMode="cash";
                            PAY_alertDialog.dismiss();
                            showConfirm();
                        }
                    });

                    Credit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PaymentMode="debt";
                            PAY_alertDialog.dismiss();
                            showConfirm();
                        }
                    });

                    PAY_alertDialog = payment_alert.create();
                    PAY_alertDialog.show();
                }
                //insertar();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirm(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Message");
        alert.setMessage("Are you shore to pay "+MontoGeneral.getText().toString()+ " in "+PaymentMode+" mode?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                server_prog = new ProgressDialog(MainActivity.this);
                server_prog.setTitle("Loading Payment");
                server_prog.show();
                //server_prog.setCancelable(false);
                insertar();
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

    private void insertar() {

        String cadena ="";
        //String xml=null;


        //String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\"><properties><comment>Openbravo POS</comment><entry key=\"product.taxcategoryid\">001</entry><entry key=\"product.com\">false</entry><entry key=\"product.categoryid\">000</entry><entry key=\"product.name\">papa</entry></properties>";

        Double total=0.00;

        for(int x=0;x<mainAdp.items.size();x++){
            if(mainAdp.items.get(x)!=null){
                if(x==mainAdp.items.size()-1){
                    //cadena+="(@,"+x+",'"+MainStrMateriales.get(x).getID()+"',null,"+MainStrMateriales.get(x).getCantidad()+","+MainStrMateriales.get(x).getPRICESELL()+","+MainStrMateriales.get(x).getTAXCAT()+","+xml+")";

                    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\"><properties><comment>Openbravo POS</comment><entry key=\"product.taxcategoryid\">"+mainAdp.items.get(x).getTAXCAT()+"</entry><entry key=\"product.com\">false</entry><entry key=\"product.categoryid\">"+mainAdp.items.get(x).getCATEGORY()+"</entry><entry key=\"product.name\">"+mainAdp.items.get(x).getNAME()+"</entry></properties>";

                    cadena+="('@',"+x+",'"+mainAdp.items.get(x).getID()+"',null,"+mainAdp.items.get(x).getCantidad()+","+mainAdp.items.get(x).getPRICESELL()+",'"+mainAdp.items.get(x).getTAXCAT()+"','"+xml+"')";
                    total+=Double.parseDouble(mainAdp.items.get(x).getPRICESELL())*mainAdp.items.get(x).getCantidad();
                }else {
                    //cadena+="(@,"+x+",'"+MainStrMateriales.get(x).getID()+"',null,"+MainStrMateriales.get(x).getCantidad()+","+MainStrMateriales.get(x).getPRICESELL()+","+MainStrMateriales.get(x).getTAXCAT()+","+xml+"),";

                    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\"><properties><comment>Openbravo POS</comment><entry key=\"product.taxcategoryid\">"+mainAdp.items.get(x).getTAXCAT()+"</entry><entry key=\"product.com\">false</entry><entry key=\"product.categoryid\">"+mainAdp.items.get(x).getCATEGORY()+"</entry><entry key=\"product.name\">"+mainAdp.items.get(x).getNAME()+"</entry></properties>";

                    cadena+="('@',"+x+",'"+mainAdp.items.get(x).getID()+"',null,"+mainAdp.items.get(x).getCantidad()+","+mainAdp.items.get(x).getPRICESELL()+",'"+mainAdp.items.get(x).getTAXCAT()+"','"+xml+"'),";
                    total+=Double.parseDouble(mainAdp.items.get(x).getPRICESELL())*mainAdp.items.get(x).getCantidad();
                }
            }
        }

        final Insertar i = new Insertar();
        i.setMoney(Configs.MONEY);
        i.setPerson(Configs.ID_USER);
        i.setPrimero(ClienteID);
        i.setTipopago(PaymentMode);
        i.setTotal(total);
        i.setDetalle(cadena);

        Log.e("cliente",ClienteID);
        Log.e("money",i.getMoney());
        //Log.e("p1",i.getPrimero());
        Log.e("p1",i.getTipopago());
        Log.e("p1",i.getTotal().toString());
        Log.e("datos",i.getDetalle());

        ApiClient
                .getClient()
                .insertar(i)
                .enqueue(new Callback<ResponseMSJ>() {
                    @Override
                    public void onResponse(Call<ResponseMSJ> call, Response<ResponseMSJ> response) {
                        Log.e("response",String.valueOf(response.isSuccessful()));
                        Log.e("code",String.valueOf(response.code()));

                        if(response.isSuccessful()){
                            Toast.makeText(MainActivity.this,"correcto",Toast.LENGTH_LONG);
                            //Log.e("ok",response.body().getMensaje());
                            Log.e("receipt",response.body().getReceipt());
                            //String id, List<Product> details, Double subTotal, Double total, String client, String cashier
                            Receipt receipt= new Receipt(response.body().getReceipt(),mainAdp.items,i.getTotal(),i.getTotal(),0.00,"",Configs.USER,response.body().getDate(),i.getTipopago(),Client_Name);
                            receipt.setClient(Client_Name);
                            Zebraprint zebraprint = new Zebraprint(MainActivity.this,receipt,Zebraprint.TAG_IMPRESION);
                            zebraprint.probarlo();
                            ///finish();

                        }else{
                            try {
                                server_prog.dismiss();
                                Log.e("insertar error",response.errorBody().string());

                                showAlert("ERROR FROM SERVER");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseMSJ> call, Throwable t) {
                        Log.e("error",t.getMessage());
                    }
                });

    }


    @Override
    public void onAlertPositiveClick(DialogFragment dialog, String Identificador) {
        // Toast.makeText(ActividadInsercionTarea.this,"ESTOY ACA",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAlertNegativeClick(DialogFragment dialog, String Identificador) {
        //insertar();

    }

    @Override
    public void onAlertNeutralClick(DialogFragment dialog, String Identificador) {

    }

    class TareaAnadirContacto extends AsyncTask<Uri, Void, Void> {
        private final ContentResolver resolver;
        private final ContentValues valores;

        public TareaAnadirContacto(ContentResolver resolver, ContentValues valores) {
            this.resolver = resolver;
            this.valores = valores;
        }

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("loading");
            pd.show();
        }





        @Override
        protected Void doInBackground(Uri... args) {
//            Uri uri = args[0];
//            if (null != uri) {
//                /*
//                Verificación: Si el contacto que se va a actualizar aún no ha sido sincronizado,
//                es decir su columna 'insertado' = 1, entonces la columna 'modificado' no debe ser
//                alterada
//                 */
//                Cursor c = resolver.query(uri, new String[]{Contrato.Tareas.INSERTADO}, null, null, null);
//                if (c != null && c.moveToNext()) {
//                    // Verificación de sincronización
//                    if (UConsultas.obtenerInt(c, Tareas.INSERTADO) == 0) {
//                        valores.put(Tareas.MODIFICADO, 1);
//                    }
//                    valores.put(Tareas.FECHA_CIERRE, UTiempo.obtenerFecha());
//                    valores.put(Tareas.HORA_FIN, UTiempo.obtenerHora());
//                    valores.put(Tareas.OBSERVACION, Observacion);
//                    resolver.update(uri, valores, null, null);
//                }
//
//            } else {
//                resolver.insert(Tareas.URI_CONTENIDO, valores);
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pd != null)
            {
                pd.dismiss();
                //sincronizar();
                finish();

            }
        }

    }






}

