package com.ericsson.famousquotesandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// About button
		Button btnAboutApp = (Button) findViewById(R.id.btnAboutApp);
		btnAboutApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("About Application");
				builder.setMessage("Famous Quotes\r\nVersion 1.0");
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.show();
			}

		});

		// List Quotes Button
		Button btnListQuotes = (Button) findViewById(R.id.btnListQuotes);
		btnListQuotes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this,
						QuotesListActivity.class);
				startActivity(myIntent);
			}

		});

		// Event Listener for Add Quote button
		Button btnAddQuote = (Button) findViewById(R.id.btnAddQuote);
		btnAddQuote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this,
						AddQuoteActivity.class);
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
