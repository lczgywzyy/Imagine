package u.can.i.up.ui.dbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import u.can.i.up.ui.beans.Pearl;
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

    public ArrayList<Pearl> getPearls(){

        SQLiteDatabase db=this.getWritableDatabase();
        String querySql="select * from V_SMaterial";
        ArrayList<Pearl> arrayPearl=new ArrayList<Pearl>();

        Cursor cursor=db.rawQuery(querySql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Pearl pearl=new Pearl();
            pearl.setAperture(cursor.getString(cursor.getColumnIndex("aperture")));
            pearl.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            pearl.setMaterial(cursor.getString(cursor.getColumnIndex("material")));
            pearl.setMD5(cursor.getString(cursor.getColumnIndex("MD5")));
            pearl.setMerchantCode(cursor.getInt(cursor.getColumnIndex("MerchantCode")));
            pearl.setPath(cursor.getString(cursor.getColumnIndex("path")));
            pearl.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            pearl.setSize(cursor.getString(cursor.getColumnIndex("size")));
            pearl.setSMaterialId(cursor.getInt(cursor.getColumnIndex("SMaterialId")));
            pearl.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
            pearl.setType(cursor.getInt(cursor.getColumnIndex("type")));
            pearl.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
            pearl.setName(cursor.getString(cursor.getColumnIndex("name")));
            pearl.settMaterialName(cursor.getString(cursor.getColumnIndex("TMaterialName")));
            arrayPearl.add(pearl);
            cursor.moveToNext();

        }

        return arrayPearl;
    }


    public boolean addPearl(Pearl pearl){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("aperture",pearl.getAperture());
        values.put("description",pearl.getDescription());
        values.put("material",pearl.getMaterial());
        values.put("MD5",pearl.getMD5());
        values.put("MerchantCode",pearl.getMerchantCode());
        values.put("path",pearl.getPath());
        values.put("category",pearl.getCategory());
        values.put("Type",pearl.getType());
        values.put("price",pearl.getPrice());;
        values.put("size",pearl.getSize());
        values.put("weight",pearl.getWeight());
        long status=db.insert("SMaterial",null,values);

        if(status==-1){
            return false;
        }else{
            return  true;
        }
    }

    public boolean deletePearl(Pearl pearl){
        SQLiteDatabase db=this.getWritableDatabase();
        long status= db.delete("SMaterial","SMaterialId=?",new String[]{String.valueOf(pearl.getSMaterialId())});
        if(status==-1){
            return  false;
        }else{
            return true;
        }

    }

    public boolean updatePearl(Pearl pearl){
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
