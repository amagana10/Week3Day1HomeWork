package com.example.week3day1homework;

import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class AsyncThreading extends AsyncTask<String,String,String> {
    String string;
    public AsyncThreading(String stringToReverse) {
        string = stringToReverse;
    }

    @Override
    protected void onPreExecute() {
        Log.d("TAG", "onPreExecute: ABOUT TO RUN");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        publishProgress("REVERSING STRING");
        String reverse = "";
        for (int i =0;i < string.length();i++){
            reverse = string.charAt(i)+ reverse;
            publishProgress("REVERSING STRING");
        }

        return reverse;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        EventBus.getDefault().post(new AsyncTaskEvent(values[0]));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EventBus.getDefault().post(new AsyncTaskEvent(s));
    }
}
