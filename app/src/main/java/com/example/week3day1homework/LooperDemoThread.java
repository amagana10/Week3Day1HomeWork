package com.example.week3day1homework;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class LooperDemoThread extends Thread {
    Handler workerHandler;
    Handler mainThreadHandler;

    public LooperDemoThread(final String string, Handler handler) {
        this.mainThreadHandler = handler;



        workerHandler = new android.os.Handler(Looper.myLooper()){

            @Override
            public void handleMessage(Message msg) {

                String repeats = "";

                Map<Character,Integer> map = new HashMap<Character,Integer>();
                for (int i = 0; i < string.length(); i++) {
                    char c = string.charAt(i);
                    if (map.containsKey(c)) {
                        int cnt = map.get(c);
                        map.put(c, ++cnt);

                        repeats = repeats + " " + c + " repeats ";

                    } else {
                        map.put(c, 1);
                    }
                }

                super.handleMessage(msg);
                Log.i("Child_THREAD", "Receive message from main thread.");
                Message message = new Message();
                message.what = msg.what;
                Bundle bundle = new Bundle();
                bundle.putString("key", repeats);
                message.setData(bundle);
                mainThreadHandler.sendMessage(message);
            }

        };
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        Looper.loop();
    }
}
