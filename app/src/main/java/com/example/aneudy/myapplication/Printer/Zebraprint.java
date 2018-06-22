package com.example.aneudy.myapplication.Printer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;


import com.example.aneudy.myapplication.Models.Receipt;
import com.example.aneudy.myapplication.Product;
import com.example.aneudy.myapplication.Utils.Resolve;
import com.example.aneudy.myapplication.provider.Configs;
import com.example.aneudy.myapplication.ui.MainActivity;
import com.example.aneudy.myapplication.ui.Operations;
import com.example.aneudy.myapplication.ui.Pay_Credits;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;


import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

/**
 * Created by Juan on 1/26/2015.
 */
public class Zebraprint {

    private Context _context;
    private Progress mListener;
    private List<HashMap<String, String>> detalles;// Conceptos de pago
    private String[] parametros;// TRN Conceptopago etc... etc...
    //private List<NameValuePair> param_post; //Parametros por post

    public static final String TAG_IMPRESION = "Normal";
    public static final String TAG_REIMPRESION = "Reimprimir";
    public static final String TAG_PAGO = "Pago";
    public static final String TAG_PAGO_REIMPRESION="pago_reimpresion";

    private final Integer caracteres_X_linea = 47;
    private final String Final_Linea = "\r\n";
    private final String linea_mitad = "- - - - - - - - - - - - ";
    private final String linea_entera = "- - - - - - - - - - - - - - - - - - - - - - - -";



    private String imp_dat = "";
    private Receipt receipt;



    public Zebraprint(Context context,Receipt receipt, String TAG){
        this._context = context;
        this.receipt=receipt;
        this.imp_dat=TAG;
        Log.e("ok","ok");
    }

    public Zebraprint(Context context,Receipt receipt, String TAG,Progress mListener){
        this._context = context;
        this.receipt=receipt;
        this.imp_dat=TAG;
        this.mListener=mListener;
    }
    public Zebraprint(Context context, String dato){
        this._context = context;
        this.imp_dat = dato;
    }

    public  void probarlo(){
        mListener.showProgressPrint(true);
        new Thread(new Runnable() {
            public void run() {


                try {
                    Looper.prepare();
                    doConnectionTest(imp_dat);
                    Looper.loop();
                    Looper.myLooper().quit();
                } catch (InterruptedException e) {
                    //mListener.showProgressPrint(false);
                    mListener.error("ERROR CONECTION TO PRINTER");
                    Log.e("CONECT ERROR","error conection");
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private Connection printerConnection;
    private ZebraPrinter printer;


    private void toggleEditField(EditText editText, boolean set) {
        /*
         * Note: Disabled EditText fields may still get focus by some other means, and allow text input.
         *       See http://code.google.com/p/android/issues/detail?id=2771
         */
        editText.setEnabled(set);
        editText.setFocusable(set);
        editText.setFocusableInTouchMode(set);
    }

    private boolean isBluetoothSelected() {
        return true;
    }

    public ZebraPrinter connect() throws InterruptedException {

        ZebraPrinter printer = null;
        printerConnection = null;
        if (isBluetoothSelected()) {
            BluetoothDevice mmDevice = null;
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(mBluetoothAdapter == null){
                mListener.error("NO BLUETOOH ADAPTER AVAIBLE!!");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                _context.startActivity(enableBluetooth);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {


                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices    jhos15
                    if (device.getName().equals("jhos15")) {
                        mmDevice = device;
                        printerConnection = new BluetoothConnection(mmDevice.getAddress());
                        break;
                    }
                }
            }


            if(printerConnection==null){
                mListener.error("PRINTER NOT FOUND");
                return null;

            }

        } else {
            Log.e("CONECT ERROR","error conection");

            mListener.error("ERROR CONECTION TO PRINTER ");

            return null;
        }

        try {
            printerConnection.open();

            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);

                PrinterLanguage pl = printer.getPrinterControlLanguage();

            } catch (ConnectionException e) {

                printer = null;

                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                mListener.error("UNKNOW PRINTER LANGUAGE");
                printer = null;

                disconnect();
            }
        } catch (ConnectionException e)
        {

            mListener.error("FAILD CONECTION TO PRINTER");

            disconnect();
        }
        return printer;
    }

    public void disconnect() {
        try {

            if (printerConnection != null) {
                printerConnection.close();
            }

        } catch (ConnectionException e) {

        } finally {
            //enableTestButton(true);
        }
    }


    private void doConnectionTest(String opcion) throws InterruptedException {
        printer = connect();
        if (printer != null) {
            sendTestLabel(opcion);
        }
    }

    private void sendTestLabel(String opcion) throws InterruptedException {
        try {
            Log.d("Impresion", "Tag Impresion que llego: " + opcion + " El valor a comprar ");

            //printer.Connection.write Recibe un byte[]
            switch(opcion) {
                case TAG_IMPRESION :
                    Log.d("Impresion", "Impresion Normal");
                    printerConnection.write(openbravoFactura()); //
                    break;
                case TAG_REIMPRESION :
                    Log.d("Impresion", "Cambie a Reimprimir");
                    printerConnection.write(openbravoFactura_reimprimir());
                    break;
                case "Listo" :{
                    printerConnection.write(openbravoFactura());
                    break;
                }
                case TAG_PAGO:{
                    printerConnection.write(openbravoPago());
                    break;
                }
                case TAG_PAGO_REIMPRESION:{
                    printerConnection.write(openbravoPago_Reprint());
                    break;
                }
            }


            if (printerConnection instanceof BluetoothConnection) {
                String friendlyName = ((BluetoothConnection) printerConnection).getFriendlyName();


            }



        } catch (ConnectionException e) {
            //((MainActivity)this._context).showAlert("ERROR PRINTER");
            //mListener.error("FAILD PRINTING THE RECIEPT");

        } finally {

            disconnect();
            mListener.finishPrint();
        }
    }

    /*
    * Returns the command for a test label depending on the printer control language
    * The test label is a box with the word "TEST" inside of it
    *
    * _________________________
    * |                       |
    * |                       |
    * |        TEST           |
    * |                       |
    * |                       |
    * |_______________________|
    *
    *
    */
    private byte[] getConfigLabel() {
        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 TEST\r\n" + "PRINT\r\n";
            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;
    }

    private byte[] openbravoFactura_reimprimir() {

        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! U1 SETLP 7 0 20 \r\n" +
                    "! U1 CONTRAST 3" + Final_Linea +
                    "! U1 CENTER" + Final_Linea + //No funciona D:!!
                    Resolve.alinea_centro(receipt.getName(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getDescription(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getAddress(), caracteres_X_linea) + Final_Linea +
                    Resolve.dos_columna("Tel: "+receipt.getPhone(), caracteres_X_linea, "Fax: "+receipt.getFax()) + Final_Linea +
                    Final_Linea+
                    "-----COPY"+
                    Final_Linea+
                    Resolve.dos_columna("RECIPT:", 20, receipt.getId()) + Final_Linea +
                    Resolve.dos_columna("DATE:",42, receipt.getDate()) + Final_Linea +
                    Resolve.dos_columna("PAYMENT:", 20, receipt.getPayMethod())+
                    Final_Linea+
                    Resolve.dos_columna("CUSTOMER:",20,receipt.getClient())+
                    Final_Linea +
                    Final_Linea+
                    Resolve.alinea_centro("R E C E I P T", caracteres_X_linea) +
                    Final_Linea+
                    Final_Linea+
                    "Items                "+"Prices       "+"values        "+"\r\n"+
                    linea_entera+
                    Final_Linea+
                    print_details(receipt.getDetails())+
                    Final_Linea+
                    linea_entera+
                    Final_Linea+
                    "Items Count: "+receipt.getItemsCount()+
                    Final_Linea+
                    ">>SubTotal: $"+receipt.getSubTotal()+
                    Final_Linea+
                    ">>Total: $"+receipt.getTotal()+
                    Final_Linea+
                    Final_Linea+
                    "Delivered by: "+receipt.getCashier()+
                    Final_Linea+
                    receipt.getFooter()+
                    Final_Linea+Final_Linea;




            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;

    }

    private byte[] openbravoFactura() {

        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! U1 SETLP 7 0 20 \r\n" +
                    "! U1 CONTRAST 3" + Final_Linea +
                    "! U1 CENTER" + Final_Linea + //No funciona D:!!
                    Resolve.alinea_centro(receipt.getName(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getDescription(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getAddress(), caracteres_X_linea) + Final_Linea +
                    Resolve.dos_columna("Tel: "+receipt.getPhone(), caracteres_X_linea, "Fax: "+receipt.getFax()) + Final_Linea +
                    Final_Linea+
                    "-----ORIGINAL"+
                    Final_Linea+
                    Resolve.dos_columna("RECIPT:", 20, receipt.getId()) + Final_Linea +
                    Resolve.dos_columna("DATE:",42, receipt.getDate()) + Final_Linea +
                    Resolve.dos_columna("PAYMENT:", 20, receipt.getPayMethod())+
                    Final_Linea+
                    Resolve.dos_columna("CUSTOMER:",20,receipt.getClient())+
                    Final_Linea +
                    Final_Linea+
                    Resolve.alinea_centro("R E C E I P T", caracteres_X_linea) +
                    Final_Linea+
                    Final_Linea+
                    "Items                "+"Prices       "+"values        "+"\r\n"+
                    linea_entera+
                    Final_Linea+
                    print_details(receipt.getDetails())+
                    Final_Linea+
                    linea_entera+
                    Final_Linea+
                    "Items Count: "+receipt.getItemsCount()+
                    Final_Linea+
                    ">>SubTotal: $"+receipt.getSubTotal()+
                    Final_Linea+
                    ">>Total: $"+receipt.getTotal()+
                    Final_Linea+
                    Final_Linea+
                    "Delivered by: "+receipt.getCashier()+
                    Final_Linea+
                    receipt.getFooter()+
                    Final_Linea+Final_Linea;




            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;

    }

    private byte[] openbravoPago() {

        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! U1 SETLP 7 0 20 \r\n" +
                    "! U1 CONTRAST 3" + Final_Linea +
                    "! U1 CENTER" + Final_Linea + //No funciona D:!!
                    Resolve.alinea_centro(receipt.getName(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getDescription(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getAddress(), caracteres_X_linea) + Final_Linea +
                    Resolve.dos_columna("Tel: "+receipt.getPhone(), caracteres_X_linea, "Fax: "+receipt.getFax()) + Final_Linea +
                    Final_Linea+
                    "-----ORIGINAL"+
                    Final_Linea+
                    Resolve.dos_columna("RECIPT:", 20, receipt.getId()) + Final_Linea +
                    Resolve.dos_columna("DATE:",42, receipt.getDate()) + Final_Linea +
                    Resolve.dos_columna("PAYMENT:", 20, receipt.getPayMethod())+
                    Final_Linea+
                    Resolve.dos_columna("CUSTOMER:",20,receipt.getClient())+
                    Final_Linea +
                    Final_Linea+
                    Resolve.dos_columna("TOTAL PAID:",20,receipt.getTotal().toString())+
                    Final_Linea+
                    Resolve.dos_columna("PENDING:",20,receipt.getPending().toString())+
                    Final_Linea+
                    Final_Linea+
                    receipt.getPayMethod()+
                    Final_Linea+
                    "Delivered by: "+receipt.getCashier()+
                    Final_Linea+
                    receipt.getFooter()+
                    Final_Linea+Final_Linea;




            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;

    }

    private byte[] openbravoPago_Reprint() {

        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! U1 SETLP 7 0 20 \r\n" +
                    "! U1 CONTRAST 3" + Final_Linea +
                    "! U1 CENTER" + Final_Linea + //No funciona D:!!
                    Resolve.alinea_centro(receipt.getName(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getDescription(), caracteres_X_linea) + Final_Linea +
                    Resolve.alinea_centro(receipt.getAddress(), caracteres_X_linea) + Final_Linea +
                    Resolve.dos_columna("Tel: "+receipt.getPhone(), caracteres_X_linea, "Fax: "+receipt.getFax()) + Final_Linea +
                    Final_Linea+
                    "-----COPY"+
                    Final_Linea+
                    Resolve.dos_columna("RECIPT:", 20, receipt.getId()) + Final_Linea +
                    Resolve.dos_columna("DATE:",42, receipt.getDate()) + Final_Linea +
                    Resolve.dos_columna("PAYMENT:", 20, receipt.getPayMethod())+
                    Final_Linea+
                    Resolve.dos_columna("CUSTOMER:",20,receipt.getClient())+
                    Final_Linea +
                    Final_Linea+
                    Resolve.dos_columna("TOTAL PAID:",20,receipt.getTotal().toString())+
                    Final_Linea+
                    Resolve.dos_columna("PENDING:",20,receipt.getPending().toString())+
                    Final_Linea+
                    Final_Linea+
                    receipt.getPayMethod()+
                    Final_Linea+
                    "Delivered by: "+receipt.getCashier()+
                    Final_Linea+
                    receipt.getFooter()+
                    Final_Linea+Final_Linea;




            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;

    }


    private String print_details(List<Product> products){
        StringBuilder SB = new StringBuilder();
        int maximo_item=19;
        int maximo_price=12;
        int maximo_total=13;

        for (Product product:products) {
            Log.e("for",product.getNAME());
            String i = product.getCantidad()+"-"+product.getNAME();
            //producto
            int i_size=i.length();
            double i_pro = i_size / maximo_item;

            int pausa=0;
            int count=0;
            while (pausa==0){
                int inicio =maximo_item*count;
                int fin =maximo_item *(count + 1);

                if(inicio>=i_size || fin>=i_size){
                  if(inicio<=i_size){
                      count++;
                  }
                  break;
                }

                String prueba = i.substring(inicio,fin);

                if(prueba.isEmpty()) pausa=1;

                count++;
            }

            //price
            String p="$"+product.getPRICESELL();
            int p_size=p.length();
            double p_pro=p_size/maximo_price;

            //total
            Double total=Double.parseDouble(product.getPRICESELL())*product.getCantidad();
            String t="$"+total.toString();
            int t_size=t.length();
            double t_pro=t_size/maximo_total;


            for(int x=0;x<count;x++){
                int i_k=0;
                if(x==0){
                    SB.append(i.substring(0,(maximo_item>i_size)?i_size:maximo_item));

                    i_k=maximo_item-i_size;

                    if(i_k > 0)for (Integer q = 0; q < i_k; q++) SB.append(" ");

                    SB.append("|");

                    SB.append(p.substring(0,(maximo_price>p_size)?p_size:maximo_price));

                    int i_calc=maximo_price-p_size;

                    if(i_calc > 0)for (Integer q = 0; q < i_calc; q++) SB.append(" ");

                    SB.append("|");

                    SB.append(t.substring(0,(maximo_total>t_size)?t_size:maximo_total));

                    int t_calc=maximo_total-t_size;

                    if(t_calc > 0)for (Integer q = 0; q < t_calc; q++) SB.append(" ");

                    SB.append(Final_Linea);
                }else {

                    int inicio = maximo_item * x;
                    int fin = maximo_item * (x + 1);

                    if (inicio >= i_size || fin >= i_size) {
                        if (inicio <= i_size) {
                            SB.append(i.substring(inicio));
                        }
                        break;
                    }
                    SB.append(i.substring(inicio, fin));
                    SB.append(Final_Linea);
                }
            }
            SB.append(Final_Linea);

        }
        //StringBuilder SB = new StringBuilder(c+i_cut+p_cut);
        return SB.toString();
    }
}


