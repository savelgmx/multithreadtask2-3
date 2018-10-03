            package fb.fandroid.adv.multithreadtask2_3;


            import android.content.BroadcastReceiver;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.ServiceConnection;
            import android.os.IBinder;
            import android.support.v4.content.LocalBroadcastManager;
            import android.support.v7.app.AppCompatActivity;
            import android.os.Bundle;
            import android.util.Log;
            import android.view.View;
            import android.widget.Button;
            import android.widget.ProgressBar;
            import android.widget.TextView;






            public class MainActivity extends AppCompatActivity {

                public static final String LOG_TAG = "ImitateLoadingService";
                public static final String IS_LOADING = "IS_LOADING";
                private static boolean sIsLoading;

                private int progressStatus=0;

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
                         использовать BindService

                        */



                private ImitateLoadingService mImitateLoadingService;
                private boolean mBound;

                private ServiceConnection mServiceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        ImitateLoadingService.ImitateLoadingBinder binder = (ImitateLoadingService.ImitateLoadingBinder) iBinder;
                        mImitateLoadingService = binder.getService();
                        mBound = true;
                    }
                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {
                        mImitateLoadingService = null;
                        mBound = false;
                    }
                };

                private BroadcastReceiver mLoadingCompleteReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        showLoadingFinished();
                        sIsLoading = false;
                    }
                };

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    progressBar=(ProgressBar)findViewById(R.id.progressBar);
                    startLoadingBtn=(Button)findViewById(R.id.startLoadingBtn);
                    loadTextView=(TextView)findViewById(R.id.loadTextView);


                    if (sIsLoading) {
                        showLoadingStarted();
                    }



                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                    manager.registerReceiver(mLoadingCompleteReceiver, new IntentFilter(ImitateLoadingService.LOADING_COMPLETE_ACTION));
                }

                @Override
                protected void onStart() {
                    super.onStart();
                    Intent intent = new Intent(MainActivity.this, ImitateLoadingService.class);
                    startService(intent);
                    bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);


                    startLoadingBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showLoadingStarted();
                            sIsLoading = true;
                             mImitateLoadingService.imitateLoading();
                        }
                    });


                 }

                @Override
                protected void onStop() {
                    super.onStop();
                    if (mBound) {
                        unbindService(mServiceConnection);
                        mBound = false;
                    }
                }

                @Override
                protected void onDestroy() {
                    super.onDestroy();
                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                    manager.unregisterReceiver(mLoadingCompleteReceiver);
                }

                @Override
                protected void onSaveInstanceState(Bundle outState) {
                    outState.putBoolean(IS_LOADING, sIsLoading);
                    super.onSaveInstanceState(outState);
                }


                private void showLoadingStarted() {

                    startLoadingBtn.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                     loadTextView.setText("Loading...");

                    Log.d(LOG_TAG, "startLoad");
                }

                private void showLoadingFinished() {

                    startLoadingBtn.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    loadTextView.setText("Ready.");

                    Log.d(LOG_TAG, "finishedLoad");

                }


            }
