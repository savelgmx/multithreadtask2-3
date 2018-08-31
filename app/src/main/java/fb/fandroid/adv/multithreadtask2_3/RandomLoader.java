package fb.fandroid.adv.multithreadtask2_3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;//Это лоадер, который выполнит свою работу асинхронно и вернет вам результат.
import android.util.Log;

import java.util.Random;

/**
 * Created by Administrator on 24.08.2018.
 *
 */



public class RandomLoader extends AsyncTaskLoader<String> {
   // Создадим класс загрузчика, который наследуется от AsyncTaskLoader. Наш загрузчик будет возвращать строку, поэтому укажем <String>:

    public static final String LOG_TAG = "AsyncTaskLoader";
    public static final String ARG_WORD = "word";
    public static final int RANDOM_STRING_LENGTH = 100;
    private String mWord;

    public RandomLoader(Context context, Bundle args) {
        super(context);
        if (args != null)
            mWord = args.getString(ARG_WORD);
    }

    @Override
    /*

onStartLoading() — срабатывает при запуске загрузчика (но это еще не означает загрузку данных)
onStopLoading() — срабатывает при остановке загрузчика
deliverResult() — получает и возвращает итоговый результат работы загрузчика


Для получения данных в методе loadInBackground() мы вызываем наш вспомогательный метод generateString(), который генерит случайную строку.
     */
    public String loadInBackground() {

        //  loadInBackground() — метод, в котором собственно и должна быть создана вся работа по загрузке данных.

        if (mWord == null) {
            return null;
        }
        Log.d(LOG_TAG, "loadInBackground");
        return generateString(mWord);
    }

    @Override
    public void forceLoad() {
        //forceLoad() — «принудительная» загрузка новых данных

        Log.d(LOG_TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(LOG_TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(LOG_TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(String data) {
        Log.d(LOG_TAG, "deliverResult");
       super.deliverResult(data);
    }


    private String generateString(String characters)
    {

        int delayTask=5000;//сначала задержим загрузку на 5000 мс.

        try {
            Thread.sleep(delayTask);// Sleep на промежуток времени 5sec.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG,"Awake at last after sleeping for " + delayTask + " milliseconds!");
        Random rand = new Random();
        char[] text = new char[RANDOM_STRING_LENGTH];
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            text[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        return new String(text);// Return a String result

     }

}
