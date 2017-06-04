package com.johan007.retrofitpackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }
    public void getData() {
        NetRequest.APIInstance2.contributorsBySimpleGetCall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    Log.d("test","result size="+results.size());

                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        Toast.makeText(MainActivity.this,"网络连接异常",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
