package development.oms.com.musictempo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import development.oms.com.musictempo.model.TempoModel;

/**
 * Created by Omar on 15/05/2017.
 */

public class TempoSQLHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE = "CREATE TABLE tempo (id INTEGER PRIMARY KEY, tempo REAL, title TEXT)";
    private static final String SQL_DROP = "DROP TABLE IF EXISTS tempo";

    public TempoSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TempoSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Aquí deberíamos migrar los datos y no borrarlos simplemente
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }

    public ArrayList<TempoModel> getModels(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TempoModel> models = new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM tempo", null);
        result.moveToFirst();
        for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
            TempoModel tempoModel = new TempoModel();
            tempoModel.setTempo(result.getFloat(result.getColumnIndex("tempo")));
            tempoModel.setTitle(result.getString(result.getColumnIndex("title")));
            models.add(tempoModel);
        }
        //db.close();
        return models;
    }

    public void addModel(TempoModel tempoModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = toContentValues(tempoModel);
        db.insert("tempo", null, values);
        //db.close();
    }

    private ContentValues toContentValues(TempoModel tempoModel){
        ContentValues values = new ContentValues();
        values.put("tempo", tempoModel.getTempo());
        values.put("title", tempoModel.getTitle());
        return values;
    }
}
