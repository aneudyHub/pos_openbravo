package com.example.aneudy.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable
{

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("REFERENCE")
    @Expose
    private String rEFERENCE;
    @SerializedName("CODE")
    @Expose
    private String cODE;
    @SerializedName("CODETYPE")
    @Expose
    private Object cODETYPE;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("PRICEBUY")
    @Expose
    private String pRICEBUY;
    @SerializedName("PRICESELL")
    @Expose
    private String pRICESELL;
    @SerializedName("CATEGORY")
    @Expose
    private String cATEGORY;
    @SerializedName("TAXCAT")
    @Expose
    private String tAXCAT;
    @SerializedName("ATTRIBUTESET_ID")
    @Expose
    private Object aTTRIBUTESETID;
    @SerializedName("STOCKCOST")
    @Expose
    private Object sTOCKCOST;
    @SerializedName("STOCKVOLUME")
    @Expose
    private Object sTOCKVOLUME;
    @SerializedName("IMAGE")
    @Expose
    private Object iMAGE;
    @SerializedName("ISCOM")
    @Expose
    private String iSCOM;
    @SerializedName("ISSCALE")
    @Expose
    private String iSSCALE;
    @SerializedName("ATTRIBUTES")
    @Expose
    private Object aTTRIBUTES;

    @SerializedName("COUNT")
    @Expose
    private int Cantidad;


    public final static Parcelable.Creator<Product> CREATOR = new Creator<Product>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            Product instance = new Product();
            instance.iD = ((String) in.readValue((String.class.getClassLoader())));
            instance.rEFERENCE = ((String) in.readValue((String.class.getClassLoader())));
            instance.cODE = ((String) in.readValue((String.class.getClassLoader())));
            instance.cODETYPE = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.nAME = ((String) in.readValue((String.class.getClassLoader())));
            instance.pRICEBUY = ((String) in.readValue((String.class.getClassLoader())));
            instance.pRICESELL = ((String) in.readValue((String.class.getClassLoader())));
            instance.cATEGORY = ((String) in.readValue((String.class.getClassLoader())));
            instance.tAXCAT = ((String) in.readValue((String.class.getClassLoader())));
            instance.aTTRIBUTESETID = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.sTOCKCOST = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.sTOCKVOLUME = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.iMAGE = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.iSCOM = ((String) in.readValue((String.class.getClassLoader())));
            instance.iSSCALE = ((String) in.readValue((String.class.getClassLoader())));
            instance.aTTRIBUTES = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    }
            ;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param tAXCAT
     * @param cATEGORY
     * @param pRICEBUY
     * @param nAME
     * @param sTOCKVOLUME
     * @param aTTRIBUTES
     * @param iD
     * @param pRICESELL
     * @param iSCOM
     * @param sTOCKCOST
     * @param iSSCALE
     * @param rEFERENCE
     * @param cODE
     * @param aTTRIBUTESETID
     * @param iMAGE
     * @param cODETYPE
     */
    public Product(String iD, String rEFERENCE, String cODE, Object cODETYPE, String nAME, String pRICEBUY, String pRICESELL, String cATEGORY, String tAXCAT, Object aTTRIBUTESETID, Object sTOCKCOST, Object sTOCKVOLUME, Object iMAGE, String iSCOM, String iSSCALE, Object aTTRIBUTES) {
        super();
        this.iD = iD;
        this.rEFERENCE = rEFERENCE;
        this.cODE = cODE;
        this.cODETYPE = cODETYPE;
        this.nAME = nAME;
        this.pRICEBUY = pRICEBUY;
        this.pRICESELL = pRICESELL;
        this.cATEGORY = cATEGORY;
        this.tAXCAT = tAXCAT;
        this.aTTRIBUTESETID = aTTRIBUTESETID;
        this.sTOCKCOST = sTOCKCOST;
        this.sTOCKVOLUME = sTOCKVOLUME;
        this.iMAGE = iMAGE;
        this.iSCOM = iSCOM;
        this.iSSCALE = iSSCALE;
        this.aTTRIBUTES = aTTRIBUTES;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getREFERENCE() {
        return rEFERENCE;
    }

    public void setREFERENCE(String rEFERENCE) {
        this.rEFERENCE = rEFERENCE;
    }

    public String getCODE() {
        return cODE;
    }

    public void setCODE(String cODE) {
        this.cODE = cODE;
    }

    public Object getCODETYPE() {
        return cODETYPE;
    }

    public void setCODETYPE(Object cODETYPE) {
        this.cODETYPE = cODETYPE;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getPRICEBUY() {
        return pRICEBUY;
    }

    public void setPRICEBUY(String pRICEBUY) {
        this.pRICEBUY = pRICEBUY;
    }

    public String getPRICESELL() {
        return pRICESELL;
    }

    public void setPRICESELL(String pRICESELL) {
        this.pRICESELL = pRICESELL;
    }

    public String getCATEGORY() {
        return cATEGORY;
    }

    public void setCATEGORY(String cATEGORY) {
        this.cATEGORY = cATEGORY;
    }

    public String getTAXCAT() {
        return tAXCAT;
    }

    public void setTAXCAT(String tAXCAT) {
        this.tAXCAT = tAXCAT;
    }

    public Object getATTRIBUTESETID() {
        return aTTRIBUTESETID;
    }

    public void setATTRIBUTESETID(Object aTTRIBUTESETID) {
        this.aTTRIBUTESETID = aTTRIBUTESETID;
    }

    public Object getSTOCKCOST() {
        return sTOCKCOST;
    }

    public void setSTOCKCOST(Object sTOCKCOST) {
        this.sTOCKCOST = sTOCKCOST;
    }

    public Object getSTOCKVOLUME() {
        return sTOCKVOLUME;
    }

    public void setSTOCKVOLUME(Object sTOCKVOLUME) {
        this.sTOCKVOLUME = sTOCKVOLUME;
    }

    public Object getIMAGE() {
        return iMAGE;
    }

    public void setIMAGE(Object iMAGE) {
        this.iMAGE = iMAGE;
    }

    public String getISCOM() {
        return iSCOM;
    }

    public void setISCOM(String iSCOM) {
        this.iSCOM = iSCOM;
    }

    public String getISSCALE() {
        return iSSCALE;
    }

    public void setISSCALE(String iSSCALE) {
        this.iSSCALE = iSSCALE;
    }

    public Object getATTRIBUTES() {
        return aTTRIBUTES;
    }

    public void setATTRIBUTES(Object aTTRIBUTES) {
        this.aTTRIBUTES = aTTRIBUTES;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(rEFERENCE);
        dest.writeValue(cODE);
        dest.writeValue(cODETYPE);
        dest.writeValue(nAME);
        dest.writeValue(pRICEBUY);
        dest.writeValue(pRICESELL);
        dest.writeValue(cATEGORY);
        dest.writeValue(tAXCAT);
        dest.writeValue(aTTRIBUTESETID);
        dest.writeValue(sTOCKCOST);
        dest.writeValue(sTOCKVOLUME);
        dest.writeValue(iMAGE);
        dest.writeValue(iSCOM);
        dest.writeValue(iSSCALE);
        dest.writeValue(aTTRIBUTES);
    }

    public int describeContents() {
        return 0;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
}