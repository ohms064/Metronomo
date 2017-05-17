package development.oms.com.musictempo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import development.oms.com.musictempo.metronome.NoteEnum;
import development.oms.com.musictempo.model.TempoModel;
import development.oms.com.musictempo.utils.ConstantsKeywords;

/**
 * Created by Omar on 15/05/2017.
 */

public class TempoSQLHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE = "CREATE TABLE tempo (id INTEGER PRIMARY KEY, tempo INTEGER, title TEXT, duration INTEGER, note VARCHAR(5)";
    private static final String SQL_DROP = "DROP TABLE IF EXISTS tempo";
    private static final String SQL_ALTER1_V1 = "ALTER TABLE tempo ADD COLUMN duration INTEGER";
    private static final String SQL_ALTER2_V1 = "ALTER TABLE tempo ADD COLUMN note VARCHAR(5)";
    private static TempoSQLHelper instance;

    public TempoSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static TempoSQLHelper getInstance() {
        return instance;
    }

    public static TempoSQLHelper createSingleton(Context context){
        instance = new TempoSQLHelper(context, ConstantsKeywords.DB_NAME, null, ConstantsKeywords.DB_VERSION);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            db.execSQL(SQL_ALTER1_V1);
            db.execSQL(SQL_ALTER2_V1);
        }
    }

    public ArrayList<TempoModel> getModels(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TempoModel> models = new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM tempo", null);

        for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
            TempoModel tempoModel = new TempoModel(result.getInt(result.getColumnIndex("id")));
            tempoModel.setTempo(result.getLong(result.getColumnIndex("tempo")));
            tempoModel.setTitle(result.getString(result.getColumnIndex("title")));
            tempoModel.setDuration(result.getLong(result.getColumnIndex("duration")));
            tempoModel.setNote(NoteEnum.fromString(result.getString(result.getColumnIndex("note"))));
            models.add(tempoModel);
        }
        db.close();
        result.close();
        return models;
    }

    public long addModel(TempoModel tempoModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = toContentValues(tempoModel);
        long row = db.insertWithOnConflict("tempo", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if(tempoModel.getId() == -1){
            Cursor cursor = db.rawQuery("SELECT * FROM tempo", null);
            cursor.moveToPosition((int) row - 1);
            row = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }
        db.close();
        return row;
    }

    private ContentValues toContentValues(TempoModel tempoModel){
        ContentValues values = new ContentValues();
        values.put("tempo", tempoModel.getTempo());
        values.put("title", tempoModel.getTitle());
        values.put("duration", tempoModel.getDurationInSeconds());
        values.put("note", tempoModel.getNote().toString());
        if(tempoModel.getId() != -1)
            values.put("id", tempoModel.getId());
        return values;
    }

    public boolean remove(TempoModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean res = db.delete("tempo", "id=" + model.getId(), null) > 0;
        db.close();
        return res;
    }
}
