        package fb.fandroid.adv.multithreadtask2_3;

        import android.app.Service;
        import android.content.Intent;
        import android.os.Binder;
        import android.os.IBinder;
        import android.support.v4.content.LocalBroadcastManager;
        import android.util.Log;

        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
        import java.util.concurrent.TimeUnit;

        //Это BindService которая имитирует загрузку
        public class ImitateLoadingService extends Service {

            private static final String TAG = "ImitateLoadingService";
            public static final String LOADING_COMPLETE_ACTION = "fb.fandroid.adv.multithreadtask2_3.LOADING_COMPLETE_ACTION";

            private ImitateLoadingBinder mBinder = new ImitateLoadingBinder();
            private ExecutorService mExecutor;

            public class ImitateLoadingBinder extends Binder {
                ImitateLoadingService getService() {
                    return ImitateLoadingService.this;
                }
            }

            @Override
            public void onCreate() {
                super.onCreate();
                Log.d(TAG, "onCreate: ");
                mExecutor = Executors.newFixedThreadPool(1);
            }

            @Override
            public int onStartCommand(Intent intent, int flags, int startId) {
                Log.d(TAG, "onStartCommand: ");
                return START_STICKY;
            }

            @Override
            public IBinder onBind(Intent intent) {
                return mBinder;
            }


            public void imitateLoading() {
                mExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(5000); //загрузка имитируется "засыпанием" потока на 5 сек.
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendLoadingCompleteBroadcast();

                    }
                });
            }

            private void sendLoadingCompleteBroadcast() {
                //посылаем извещение-Intent в MainActivity
                Intent intent = new Intent(LOADING_COMPLETE_ACTION);
                LocalBroadcastManager.getInstance(ImitateLoadingService.this).sendBroadcast(intent);
            }
        }
