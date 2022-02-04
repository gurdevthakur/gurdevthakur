package com.example.fcm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import static com.example.fcm.R.id.txt;

public class MainActivity extends AppCompatActivity {
	private static final String AUTH_KEY = "AAAABiL40zc:APA91bER_TOxycIiQPmA1dmz5uTaZxnd2FAmst6dZJgHFlTkqhVinMzpwvNNDjDfas2DyDFcxMouRN5spMt-B8AhPjmPAIH2eIlFTECmi732j-H-lZdVjBzAvtU47HlEy_e5zCo9f8q6";
	private TextView mTextView;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = findViewById(txt);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String tmp = "";
			for (String key : bundle.keySet()) {
				Object value = bundle.get(key);
				tmp += key + ": " + value + "\n\n";
			}
			mTextView.setText(tmp);
		}

		FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
			@Override
			public void onComplete(@NonNull Task<InstanceIdResult> task) {
				if (!task.isSuccessful()) {
					token = task.getException().getMessage();
					Log.w("FCM TOKEN Failed", task.getException());
				} else {
					token = task.getResult().getToken();
					Log.i("FCM TOKEN", token);
				}
			}
		});
	}

	public void showToken(View view) {
		Log.d("SSSSS","SSSSSSGuru"+token);
		mTextView.setText(token);
	}
}