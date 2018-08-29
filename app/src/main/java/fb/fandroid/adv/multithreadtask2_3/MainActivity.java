package fb.fandroid.adv.multithreadtask2_3;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;





public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    /*
     Класс реализует интерфейс LoaderManager.LoaderCallbacks, то есть мы добавляем несколько callback-методов,
      позволяющих нам «участвовать» в жизненном цикле загрузчика и взаимодействовать с LoaderManager.
      */


    public static final String LOG_TAG = "AsyncTaskLoader";
    private TextView mResultTxt;
    private Bundle mBundle;
    public static final int LOADER_RANDOM_ID = 1;
    private Loader<String> mLoader = getSupportLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, this);

    ProgressBar progressBar;
    Button startLoadingBtn;
    TextView loadTextView;

/*    На разметке определить круглый ProgressBar (изначально c Visibility.GONE), TextView (без текста) и Button.
    При нажатии на кнопку:
            - кнопка становится задизейсбленной (setEnabled(false))
            - ProgressBar становится видимым ( Visibility.VISIBLE )
 - в TextView появляется текст - “Loading..”
            - запускается задача.
            - имитация работы в фоновом потоке с помощью перевода этого потока в спящий режим на 5 секунд.
            ( TimeUnit.MILLISECONDS.sleep(5000); )
            Состояние экрана во время загрузки должно переживать переворот экрана ( пересоздание активити).
             Уже запущенный поток не должен запускаться заново.
             использовать AsynсTaskLoader
            */

    private int progressStatus=0;


    private void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        startLoadingBtn=(Button)findViewById(R.id.startLoadingBtn);
        loadTextView=(TextView)findViewById(R.id.loadTextView);




        startLoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                showToast("Loading...");
                loadTextView.setText("Loading ...");

                //******************
                mBundle = new Bundle();
                mBundle.putString(RandomLoader.ARG_WORD, "test");
                //*********************

            }
        });
   }

    public Loader<String> onCreateLoader(int id, Bundle args) {
        Loader<String> mLoader = null;
        // условие можно убрать, если вы используете только один загрузчик
        if (id == LOADER_RANDOM_ID) {
            mLoader = new RandomLoader(this, args);
            Log.d(LOG_TAG, "onCreateLoader");
        }
        return mLoader;
    }

    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(LOG_TAG, "onLoadFinished");
        mResultTxt.setText(data);
    }

    public void onLoaderReset(Loader<String> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
    }

    public void startLoad(View v) {
        Log.d(LOG_TAG, "startLoad");
        mLoader.onContentChanged();
    }
}
