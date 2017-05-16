package development.oms.com.musictempo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Omar on 15/05/2017.
 */

public class TempoModel implements Parcelable{
    private float tempo;
    private String title;

    public TempoModel(Integer tempo, String title) {
        this.tempo = tempo;
        this.title = title;
    }

    public TempoModel() {

    }

    protected TempoModel(Parcel in) {
        title = in.readString();
        tempo = in.readInt();
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

    public Float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
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
        dest.writeString(title);
        dest.writeFloat(tempo);
    }


}
