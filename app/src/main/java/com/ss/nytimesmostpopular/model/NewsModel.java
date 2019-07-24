package com.ss.nytimesmostpopular.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewsModel implements Parcelable {

	private String status;
	private List<ResultsModel> results;


	protected NewsModel(Parcel in) {
		this.status = in.readString();
		this.results = in.createTypedArrayList(ResultsModel.CREATOR);
	}

	public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
		@Override
		public NewsModel createFromParcel(Parcel in) {
			return new NewsModel(in);
		}

		@Override
		public NewsModel[] newArray(int size) {
			return new NewsModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(this.status);
		parcel.writeTypedList(this.results);
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ResultsModel> getResults() {
		return results;
	}

	public void setResults(List<ResultsModel> results) {
		this.results = results;
	}
}
