package com.ericsson.famousquotesandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ericsson.famousquotes.entity.quoteendpoint.Quoteendpoint;
import com.ericsson.famousquotes.entity.quoteendpoint.model.CollectionResponseQuote;
import com.ericsson.famousquotes.entity.quoteendpoint.model.Quote;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class QuotesListActivity extends ListActivity {
	private TextView tv = null;
    private ArrayList<Map<String,String>> list = null;
    private SimpleAdapter adapter = null;
    private String[] from = { "author", "message" };
    private int[] to = { android.R.id.text1, android.R.id.text2 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		tv.setText("List of Famous Quotes");
		tv.setGravity(Gravity.CENTER);
		getListView().addHeaderView(tv);
		new QuotesListAsyncTask(this).execute();
	}

	private class QuotesListAsyncTask extends
			AsyncTask<Void, Void, CollectionResponseQuote> {

		Context context;
		private ProgressDialog pd;

		public QuotesListAsyncTask(Context context) {
			this.context = context;
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(context);
			pd.setMessage("Getting Quotes");
			pd.show();
		}

		@Override
		protected CollectionResponseQuote doInBackground(Void... params) {
			CollectionResponseQuote quotes = null;
			try {
				Quoteendpoint.Builder builder = new Quoteendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new GsonFactory(), null);
				Quoteendpoint service = builder.build();
				quotes = service.listQuote().execute();
			} catch (Exception e) {
				Log.d("Could not retrieve Quotes", e.getMessage(), e);
			}
			return quotes;
		}

		protected void onPostExecute(CollectionResponseQuote quotes) {
			pd.dismiss();
			list = new ArrayList<Map<String, String>>();
			List<Quote> _list = quotes.getItems();
			for (Quote quote : _list) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("author", quote.getAuthor());
				item.put("message", quote.getMessage());
				list.add(item);
			}
			adapter = new SimpleAdapter(QuotesListActivity.this, list,android.R.layout.simple_list_item_2, from, to);
            setListAdapter(adapter);
		}
	}

}
