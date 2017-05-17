package development.oms.com.musictempo.metronome;

import development.oms.com.musictempo.R;

/**
 * Created by Omar on 16/05/2017.
 */

public enum NoteEnum {

    QUARTER(1, R.string.note_quarter, "1/4"), MEDIUM(2, R.string.note_half, "1/2"), FULL(4, R.string.note_full, "1"), OCTAVE(0.5f, R.string.note_octave, "1/8");

    private float value;
    private int stringId;
    private String repr;

    NoteEnum(float value, int stringId, String repr){
        this.value = value;
        this.stringId = stringId;
        this.repr = repr;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString(){
        return repr;
    }

    public static NoteEnum fromString(String r){
        for(NoteEnum value:NoteEnum.values()){
            if(value.toString().equals(r)){
                return value;
            }
        }
        return null;
    }

    public int getStringId() {
        return stringId;
    }
}
