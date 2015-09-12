package u.can.i.up.ui.dbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.TMaterial;

/**
 * Created by Pengp on 2015/7/29.
 */
public class PSQLiteOpenHelper extends SQLiteOpenHelper {


    private static final String strTMaterial="create table if not exists TMaterial(TMaterialId INTEGER primary key autoincrement,TMaterialName Text not null,TMaterialMd Text not null,Description Text);";

    private static final  String strSMaterial="create table if not exists SMaterial(SMaterialId integer primary key autoincrement,MD5 Text not null,category integer,path Text not null,name not null,type integer default 1 not null,material Text not null,size Text not null,weight TEXT not null,aperture TEXT not null,price TEXT not null,description Text not null,MerchantCode integer default 0,foreign key(category) references TMaterial(TMaterialId));";

    private static final String strSPearl="create table if not exists SPearl(SpearlId integer primary key autoincrement,MD5 Text not null,PearsList Text not null,PicDirectory Text not null,Description Text not null,Name Text not null ,Sync boolean default false,Price real not null);";

    private static final String strVSMaterial="create view if not exists V_SMaterial as select SMaterial.*,TMaterial.TMaterialName from SMaterial,TMaterial where SMaterial.TMaterialId=TMaterial.TMaterialId;";

    public static final String DB_NAME="pearls.db";


    public PSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strTMaterial);
        db.execSQL(strSMaterial);
        db.execSQL(strSPearl);
        db.execSQL(strVSMaterial);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        super.setWriteAheadLoggingEnabled(enabled);
    }

    public ArrayList<PearlBeans> getPearls(){

        SQLiteDatabase db=this.getWritableDatabase();
        String querySql="select * from V_SMaterial";
        ArrayList<PearlBeans> arrayPearlBeans =new ArrayList<PearlBeans>();

        Cursor cursor=db.rawQuery(querySql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            PearlBeans pearlBeans =new PearlBeans();
            pearlBeans.setAperture(cursor.getString(cursor.getColumnIndex("aperture")));
            pearlBeans.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            pearlBeans.setMaterial(cursor.getString(cursor.getColumnIndex("material")));
            pearlBeans.setMD5(cursor.getString(cursor.getColumnIndex("MD5")));
            pearlBeans.setMerchantCode(cursor.getInt(cursor.getColumnIndex("MerchantCode")));
            pearlBeans.setPath(cursor.getString(cursor.getColumnIndex("path")));
            pearlBeans.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            pearlBeans.setSize(cursor.getString(cursor.getColumnIndex("size")));
            pearlBeans.setSMaterialId(cursor.getInt(cursor.getColumnIndex("SMaterialId")));
            pearlBeans.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
            pearlBeans.setType(cursor.getInt(cursor.getColumnIndex("type")));
            pearlBeans.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
            pearlBeans.setName(cursor.getString(cursor.getColumnIndex("name")));
            pearlBeans.settMaterialName(cursor.getString(cursor.getColumnIndex("TMaterialName")));
            arrayPearlBeans.add(pearlBeans);
            cursor.moveToNext();

        }

        return arrayPearlBeans;
    }


    public boolean addPearl(PearlBeans pearlBeans){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("aperture", pearlBeans.getAperture());
        values.put("description", pearlBeans.getDescription());
        values.put("material", pearlBeans.getMaterial());
        values.put("MD5", pearlBeans.getMD5());
        values.put("MerchantCode", pearlBeans.getMerchantCode());
        values.put("path", pearlBeans.getPath());
        values.put("category", pearlBeans.getCategory());
        values.put("type", pearlBeans.getType());
        values.put("price", pearlBeans.getPrice());;
        values.put("size", pearlBeans.getSize());
        values.put("weight", pearlBeans.getWeight());
        values.put("name", pearlBeans.getWeight());
        long status=db.insert("SMaterial",null,values);

        if(status==-1){
            return false;
        }else{
            return  true;
        }
    }

    public boolean deletePearl(PearlBeans pearlBeans){
        SQLiteDatabase db=this.getWritableDatabase();
        long status= db.delete("SMaterial","SMaterialId=?",new String[]{String.valueOf(pearlBeans.getSMaterialId())});
        if(status==-1){
            return  false;
        }else{
            return true;
        }

    }

    public boolean updatePearl(PearlBeans pearlBeans){
        return  true;
    }

    public  ArrayList<TMaterial> getTMaterials(){

        ArrayList<TMaterial> arrayTMaterial=new ArrayList<TMaterial>();

        String strSql="select * from TMaterial";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(strSql, null);
        while(cursor.moveToNext()){
            TMaterial tMaterial=new TMaterial();
            tMaterial.setTMaterialId(cursor.getInt(cursor.getColumnIndex("TMaterialId")));
            tMaterial.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
            tMaterial.setTMaterialMd(cursor.getString(cursor.getColumnIndex("TMaterialMd")));
            tMaterial.setTMaterialName(cursor.getString(cursor.getColumnIndex("TMaterialName")));
            arrayTMaterial.add(tMaterial);
        }

        return arrayTMaterial;
    }

    public boolean addTMaterial(TMaterial tMaterial){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("Description",tMaterial.getDescription());
        values.put("TMaterialMd",tMaterial.getTMaterialMd());
        values.put("TMaterialName",tMaterial.getTMaterialName());
        long status=db.insert("SMaterial",null,values);
        if(status==-1){
            return false;
        }
        return true;
    }

    public boolean deleteTMaterial(TMaterial tMaterial){
        SQLiteDatabase db=this.getWritableDatabase();
        long status= db.delete("SMaterial", "TMaterialId=?", new String[]{String.valueOf(tMaterial.getTMaterialId())});

        if(status==-1){
            return false;
        }

        return true;
    }
    public boolean updateTMaterial(TMaterial tMaterial){
        return true;
    }


    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }
}
