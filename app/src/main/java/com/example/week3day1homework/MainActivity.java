package com.example.week3day1homework;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    EditText etUserInput;
    TextView tvResultOfJavaThread;
    TextView tvResultOfAsyncTask;
    TextView tvResultOfLooper;
    AsyncThreading asyncThreading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserInput =findViewById(R.id.etUserInput);
        tvResultOfJavaThread =findViewById(R.id.tvReportResultJavaThread);
        tvResultOfAsyncTask =findViewById(R.id.tvReportResultsAsyncTask);
        tvResultOfLooper =findViewById(R.id.tvReportResultsLooper);

    }
    private Runnable runnableForThread(){
        return new Runnable() {
            @Override
            public void run() {
                String stringInput = etUserInput.getText()!=null?etUserInput.getText().toString():"";
                int sLength = stringInput.length();
                tvResultOfJavaThread.setText("run: User input size = "+sLength );

            }
        };
    }
    //StandardJavaThread
    private void startThread(){
        Thread javaThread = new Thread(runnableForThread());
        javaThread.start();
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartJavaThread:
                runOnUiThread(runnableForThread());
                break;
            case R.id.btnStartAsyncTask:
                String stringInput = etUserInput.getText()!=null?etUserInput.getText().toString():"";
                asyncThreading = new AsyncThreading(stringInput);
                asyncThreading.execute();
                break;
            case R.id.btnStartLooper:
                String userInput = etUserInput.getText()!=null?etUserInput.getText().toString():"";

                LooperDemoThread looperDemoThread;
                    looperDemoThread = new LooperDemoThread(userInput,new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle bundle = msg.getData();
                        tvResultOfLooper.setText(bundle.getString("key"));
                    }
                });
                looperDemoThread.start();
                looperDemoThread.workerHandler.sendMessage(new Message());
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAsyncMessageReceived(AsyncTaskEvent asyncTaskEvent){
        tvResultOfAsyncTask.setText(asyncTaskEvent.getMessage());
    }
}
