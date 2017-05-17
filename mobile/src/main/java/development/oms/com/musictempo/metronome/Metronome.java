package development.oms.com.musictempo.metronome;

import android.content.Context;
import android.os.Vibrator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Omar on 16/05/2017.
 */

public class Metronome {
    private Vibrator vibrator;
    private long vibDuration;
    private boolean isActive;
    private MetronomeListener metronomeListener;

    public Metronome(Context context){
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibDuration = 100;
        isActive = false;
    }

    public void setMetronomeListener(MetronomeListener metronomeListener){
        this.metronomeListener = metronomeListener;
    }

    public MetronomeListener getMetronomeListener(){
        return metronomeListener;
    }

    public boolean start(long tempo, long duration, NoteEnum note){
        if(tempo <= 0){
            return false;
        }
        long tempoMs = tempoToMs(tempo, note);
        long[] pattern = {0, vibDuration, tempoMs - vibDuration};
        vibrator.vibrate(pattern, 0);
        isActive = true;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(isActive) {
                    stop();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, duration);
        if(getMetronomeListener() != null){
            metronomeListener.onMetronomeStart();
        }
        return true;
    }

    public void stop(){
        vibrator.cancel();
        isActive = false;
        if(getMetronomeListener() != null){
            metronomeListener.onMetronomeStop();
        }
    }

    private long tempoToMs(long tempo, NoteEnum note){
        //source: http://www.sengpielaudio.com/calculator-bpmtempotime.htm
        return (long) ((60000 / tempo) * note.getValue());
    }

    public boolean isActive(){
        return isActive;
    }
}
