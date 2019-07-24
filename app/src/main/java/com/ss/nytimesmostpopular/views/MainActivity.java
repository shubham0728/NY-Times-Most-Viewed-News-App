package com.ss.nytimesmostpopular.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.ss.nytimesmostpopular.R;
import com.ss.nytimesmostpopular.adapter.NewsAdapter;
import com.ss.nytimesmostpopular.api.NewsApi;
import com.ss.nytimesmostpopular.api.NewsApiClient;
import com.ss.nytimesmostpopular.interfaces.RecyclerViewItemClickListener;
import com.ss.nytimesmostpopular.model.NewsModel;
import com.ss.nytimesmostpopular.model.ResultsModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.IOException;
import java.net.SocketTimeoutException;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shubham Singhal
 *
 * {@link MainActivity} displays most viewed news in {@link RecyclerView}.
 * {@link Toolbar} has a settings icon which helps in changing news period.
 * Also displays fresh list of taxi's if map bounds are changed.
 */

public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.news_list)
	RecyclerView newsList;
	@BindView(R.id.progress)
	ProgressBar progressBar;
	@BindView(R.id.layout_nothing_to_show)
	RelativeLayout layoutNothingToShow;

	private NewsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		init();
		checkConnection();
	}

	/**
	 * Initialise all objects in this method.
	 */
	private void init() {
		setSupportActionBar(toolbar);
		adapter = new NewsAdapter(MainActivity.this, MainActivity.this);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
		newsList.setLayoutManager(linearLayoutManager);
		newsList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem menuOneDay = menu.findItem(R.id.menu_cart_one_day);
		MenuItem menuSevenDay = menu.findItem(R.id.menu_cart_seven_day);
		MenuItem menuThirtyDay = menu.findItem(R.id.menu_cart_thirty_day);
		menuOneDay.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
			//	List<NewsModel> newsModel = adapter.getAll();
				adapter.remove();
				adapter.notifyDataSetChanged();
				progressBar.setVisibility(View.VISIBLE);
				getMostPopularNews("1");
				return false;
			}
		});

		menuSevenDay.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//List<NewsModel> newsModel = adapter.getAll();
				adapter.remove();
				adapter.notifyDataSetChanged();
				progressBar.setVisibility(View.VISIBLE);
				getMostPopularNews("7");
				return false;
			}
		});

		menuThirtyDay.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//List<NewsModel> newsModel = adapter.getAll();
				adapter.remove();
				adapter.notifyDataSetChanged();
				progressBar.setVisibility(View.VISIBLE);
				getMostPopularNews("30");
				return false;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * To check if the device is connected to the internet.
	 *
	 * @return
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Performs actions based on network status.
	 * If Online -  Fetch first batch of movies.
	 * If offline - display nothing.
	 */
	public void checkConnection() {
		if (isOnline()) {
			getMostPopularNews("7");
		} else {
			progressBar.setVisibility(View.GONE);
			layoutNothingToShow.setVisibility(View.VISIBLE);
			Toast.makeText(this, getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Fetch most viewed news through api call.
	 */
	private void getMostPopularNews(String period) {
		try {
			NewsApiClient newsApiClient = NewsApi.getRetrofitClient().create(NewsApiClient.class);
			Call<NewsModel> newsModelCall = newsApiClient.getTopHeadlines(period, getResources().getString(R.string.api_key));
			newsModelCall.enqueue(new Callback<NewsModel>() {
				@Override
				public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
					if (response.isSuccessful()) {
						NewsModel newsModel = response.body();
						progressBar.setVisibility(View.GONE);
						adapter.add(newsModel);
					} else {
						progressBar.setVisibility(View.GONE);
						layoutNothingToShow.setVisibility(View.VISIBLE);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(Call<NewsModel> call, Throwable t) {
					progressBar.setVisibility(View.GONE);
					layoutNothingToShow.setVisibility(View.VISIBLE);
					if (t instanceof SocketTimeoutException || t instanceof IOException) {
						Toast.makeText(getApplicationContext(), getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
						Log.e("ERROR", getString(R.string.no_netowrk), t);
					} else {
						progressBar.setVisibility(View.GONE);
						Log.e("ERROR", getString(R.string.error), t);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}
			});





		} catch (Exception e) {
			e.printStackTrace();
			progressBar.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRecyclerViewItemClick(int position) {
		ResultsModel resultsModel = adapter.getItem(position);
		Intent in = new Intent(MainActivity.this, NewsWebVIew.class);
		in.putExtra("url", resultsModel.getUrl() );
		startActivity(in);
	}
}
