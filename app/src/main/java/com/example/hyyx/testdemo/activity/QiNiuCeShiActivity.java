package com.example.hyyx.testdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hyyx.testdemo.BuildConfig;
import com.example.hyyx.testdemo.R;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QiNiuCeShiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_niu_ce_shi);
        init();
    }


    public static String getQinNiuKey(String token, String fileName, String fileUrl) {

        final String[] getKey = new String[1];

        UploadManager manager = new UploadManager();
        File file = new File(fileUrl);
        manager.put(file, fileName, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.statusCode == 200) {
                    getKey[0] = response.optString("key");
                }

            }
        }, null);


        return null;
    }


    private void init() {


        //外链地址http://7xwhc1.com1.z0.glb.clouddn.com/andoridImage


        TextView viewById = (TextView) findViewById(R.id.tv_post);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = "Mj9h5dy31m2WFFn7Pp3rKOPzQ6sfSIGMZib86mdr:cplWvUNgvJaNjTw4CMpLu07nl-g=:eyJzY29wZSI6ImZvdW5kZXI6aWNvbl8yMDE2MTExOTIwMTQ1OCIsImRlYWRsaW5lIjoxNDc5NTYxMzMxfQ==";
                String fileUrl = "/storage/emulated/0/myimage/1461291168340.jpg";
                String fileName = "icon_20161119201458";
                UploadManager manager = new UploadManager();
                File file = new File(fileUrl);
                manager.put(file, fileName, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.statusCode == 200) {
                            String key1 = response.optString("key");
                            String s = "http://7xwhc1.com1.z0.glb.clouddn.com/" + key1;
                            Log.e("QiNiuCeShiActivity", s);

                        }

                    }
                }, null);


            }
        });

    }
}

