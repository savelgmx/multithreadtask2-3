package fb.fandroid.adv.multithreadtask2_3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;//Это лоадер, который выполнит свою работу асинхронно и вернет вам результат.
import android.util.Log;

/**
 * Created by Administrator on 24.08.2018.
 */



public class RandomLoader extends AsyncTaskLoader {

    private static final String LOG_TAG ="AsyncTaskLoader" ;

    public RandomLoader(@NonNull Context context) {
        super(context);
    }
    @Nullable
    @Override
    public Object loadInBackground() {

        Log.d(LOG_TAG,"AsyncTaskLoader started in background");
        return null;
    }
}
