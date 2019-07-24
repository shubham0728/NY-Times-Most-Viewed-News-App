package com.ss.nytimesmostpopular.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ss.nytimesmostpopular.R;
import com.ss.nytimesmostpopular.interfaces.RecyclerViewItemClickListener;
import com.ss.nytimesmostpopular.model.NewsModel;
import com.ss.nytimesmostpopular.model.ResultsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shubham Singhal
 * <p>
 * Adapter class that displays data in {@link RecyclerView} for each item depending on the list size.
 * Layout used is <code>news_adapter</code>.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int ITEM = 0;
	private static final int LOADING = 1;
	private Context mCtx;
	private RecyclerViewItemClickListener mrecyclerViewItemClickListener;
	private List<NewsModel> newsModels;
	private boolean isLoadingAdded = false;

	/**
	 * Constructor
	 *
	 * @param _ctx
	 * @param recyclerViewItemClickListener
	 */
	public NewsAdapter(Context _ctx, RecyclerViewItemClickListener recyclerViewItemClickListener) {
		this.mCtx = _ctx;
		this.mrecyclerViewItemClickListener = recyclerViewItemClickListener;
		newsModels = new ArrayList<>();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		RecyclerView.ViewHolder viewHolder = null;
		View movieView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_adapter, viewGroup, false);
		viewHolder = new NewsViewHolder(movieView);
		return viewHolder;
	}


	/**
	 * This method is called when user scrolls through the list and new item has to be displayed.
	 *
	 * @param viewHolder
	 * @param i
	 */
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		try {

			NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
			ResultsModel resultsModel = newsModels.get(0).getResults().get(i);
			newsViewHolder.txt_source.setText(newsModels.get(0).getResults().get(i).getTitle());
			newsViewHolder.txt_news_title.setText(newsModels.get(0).getResults().get(i).getByline());
			String published_date = newsModels.get(0).getResults().get(i).getPublished_date();
			String inputPattern = "yyyy-MM-dd";
			String outputPattern = "EEE, dd MMM yyyy";
			SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
			SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

			Date date = null;
			String str = null;

			try {
				date = inputFormat.parse(published_date);
				str = outputFormat.format(date);
				newsViewHolder.txt_news_date.setText(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Glide.with(mCtx)
					.load(resultsModel.getMedia().get(0).getMediaMetaData().get(2).getUrl())
					.placeholder(R.drawable.placeholder)
					.diskCacheStrategy(DiskCacheStrategy.ALL)   // cache image
					.into(newsViewHolder.img_news);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		if (newsModels.size() > 0)
			return newsModels.get(0).getResults().size();
		else
			return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return (position == newsModels.size() && isLoadingAdded) ? LOADING : ITEM;
	}

	/**
	 * Add single entity from api call.
	 *
	 * @param newsModel
	 */
	public void add(NewsModel newsModel) {
		newsModels.add(newsModel);
		notifyItemInserted(newsModels.size() - 1);
	}

	/**
	 * Add all data from the api call.
	 *
	 * @param newsEntities
	 */
	public void addAll(List<NewsModel> newsEntities) {
		for (NewsModel result : newsEntities) {
			add(result);
		}
	}

	/**
	 * @param
	 */
	public void remove() {
		newsModels.remove(0);
	}

	public boolean isEmpty() {
		return getItemCount() == 0;
	}


	public ResultsModel getItem(int position) {
		return newsModels.get(0).getResults().get(position);
	}

	public List<NewsModel> getAll() {
		return newsModels;
	}


	/**
	 * Movie ViewHolder Class with RecyclerView click listener.
	 */
	public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView txt_news_title;
		private TextView txt_news_date;
		private TextView txt_source;
		private ProgressBar progress_news;
		private ImageView img_news;

		public NewsViewHolder(@NonNull View itemView) {
			super(itemView);
			txt_news_title = itemView.findViewById(R.id.txt_news_title);
			txt_news_date = itemView.findViewById(R.id.txt_news_date);
			txt_source = itemView.findViewById(R.id.txt_source);
			progress_news = itemView.findViewById(R.id.progress_news);
			img_news = itemView.findViewById(R.id.img_news);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mrecyclerViewItemClickListener.onRecyclerViewItemClick(getAdapterPosition());
		}
	}

}
