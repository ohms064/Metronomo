package development.oms.com.musictempo.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import development.oms.com.musictempo.metronome.NoteEnum;

/**
 * Created by Omar on 15/05/2017.
 */

public class TempoModel implements Parcelable{
    private long tempo, duration;
    private String title;
    private int id;
    private NoteEnum note;

    private static final String TEMPO = "tempo", TITLE = "title", ID = "id", DURATION = "duration", NOTE = "note";

    public TempoModel() {
        tempo = 0;
        title = "";
        id = -1;
        duration = 5000;
        note = NoteEnum.QUARTER;
    }

    public TempoModel(int id){
        this.id = id;
        tempo = 0;
        title = "";
        duration = 5000;
        note = NoteEnum.QUARTER;
    }

    private TempoModel(Parcel in) {
        Bundle args = in.readBundle(getClass().getClassLoader());
        title = args.getString(TITLE);
        tempo = args.getLong(TEMPO);
        id = args.getInt(ID);
        duration = args.getLong(DURATION);
        note = (NoteEnum) args.getSerializable(NOTE);
    }

    public static final Creator<TempoModel> CREATOR = new Creator<TempoModel>() {
        @Override
        public TempoModel createFromParcel(Parcel in) {
            return new TempoModel(in);
        }

        @Override
        public TempoModel[] newArray(int size) {
            return new TempoModel[size];
        }
    };

    public Long getTempo() {
        return tempo;
    }

    public void setTempo(Long tempo) {
        this.tempo = tempo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putLong(TEMPO, tempo);
        args.putSerializable(NOTE, note);
        args.putInt(ID, id);
        args.putLong(DURATION, duration);
        dest.writeBundle(args);
    }


    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public NoteEnum getNote() {
        return note;
    }

    public void setNote(NoteEnum note) {
        if(note == null){
            this.note = NoteEnum.QUARTER;
        }
        this.note = note;
    }

    public long getDuration() {
        return duration;
    }

    public long getDurationInSeconds(){
        return duration / 1000;
    }

    public void setDuration(long duration) {
        this.duration = duration * 1000;
    }
}
