package fb.fandroid.adv.multithreadtask2_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

            }
        });


    }




}
