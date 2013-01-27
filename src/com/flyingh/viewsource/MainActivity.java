package com.flyingh.viewsource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private EditText editText;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.site);
		textView = (TextView) findViewById(R.id.source);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
		}
	}

	public void viewSource(View view) {
		try {
			InputStream is = new URL(editText.getText().toString()).openStream();
			textView.setText(new String(getBytes(is)));
		} catch (Exception e) {
			Log.i(TAG, e.toString());
			Toast.makeText(getApplicationContext(), R.string.error_msg, Toast.LENGTH_LONG).show();
		}

	}

	private byte[] getBytes(InputStream is) throws IOException {
		byte[] buf = new byte[1024];
		int len = -1;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		while ((len = is.read(buf)) != -1) {
			os.write(buf, 0, len);
		}
		return os.toByteArray();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
