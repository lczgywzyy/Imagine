package u.can.i.up.ui.dbs;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pengp on 2015/7/29.
 */
public class PSQLiteOpenHelper extends SQLiteOpenHelper {


    private static final String strTMaterial="create table TMaterial(TMaterrialId INTEGER primary key autoincrement,TMaterialName Text not null,TMaterialMd Text not null,TmaterialDiscription Text);";

    private static final  String strSMaterdial="create table SMaterial(SMaterialId integer primary key autoincrement,MD5 Text not null,TMaterrialId integer,PicDirectory Text not null,Name not null,Type integer default 0 not null,Material Text not null,Size Real not null,Weight integer not null,Aperture Real not null,Price Real not null,Discription Text not null,MerchantCode integer not null,foreign key(TMaterrialId) references TMaterial(TMaterrialId));";

    private static final String strSPearl="create table SPearl(SpeatlId integer primary key autoincrement,MD5 Text not null,PearsList Text not null,PicDirectory Text not null,Description Text not null,Name Text not null ,Sync boolean default false,Price real not null)";

    public static final String DB_NAME="pearls.db";


    public PSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    public PSQLiteOpenHelper(Context context){
            super(context,DB_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strTMaterial);
        db.execSQL(strSMaterdial);
        db.execSQL(strSPearl);
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
