package com.ss.nytimesmostpopular.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class ResultsModel implements Parcelable {

	private String url;
	private String byline;
	private String title;
	private String published_date;
	private List<Media> media;


	protected ResultsModel(Parcel in) {
		this.url = in.readString();
		this.byline = in.readString();
		this.title = in.readString();
		this.published_date = in.readString();
		this.media = in.createTypedArrayList(Media.CREATOR);
	}

	public static final Creator<ResultsModel> CREATOR = new Creator<ResultsModel>() {
		@Override
		public ResultsModel createFromParcel(Parcel in) {
			return new ResultsModel(in);
		}

		@Override
		public ResultsModel[] newArray(int size) {
			return new ResultsModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(this.url);
		parcel.writeString(this.byline);
		parcel.writeString(this.title);
		parcel.writeString(this.published_date);
		parcel.writeTypedList(this.media);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getByline() {
		return byline;
	}

	public void setByline(String byline) {
		this.byline = byline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublished_date() {
		return published_date;
	}

	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}

	public List<Media> getMedia() {
		return media;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	public static class Media implements Parcelable {

		@SerializedName("media-metadata")
		@Expose
		private List<MediaMetaData> mediaMetaData;

		protected Media(Parcel in) {
			this.mediaMetaData = in.createTypedArrayList(MediaMetaData.CREATOR);
		}

		public static final Creator<Media> CREATOR = new Creator<Media>() {
			@Override
			public Media createFromParcel(Parcel in) {
				return new Media(in);
			}

			@Override
			public Media[] newArray(int size) {
				return new Media[size];
			}
		};

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int i) {
			parcel.writeTypedList(this.mediaMetaData);
		}

		public List<MediaMetaData> getMediaMetaData() {
			return mediaMetaData;
		}

		public void setMediaMetaData(List<MediaMetaData> mediaMetaData) {
			this.mediaMetaData = mediaMetaData;
		}
	}

	public static class MediaMetaData implements Parcelable {

		private String url;
		private String format;
		private String height;
		private String width;

		protected MediaMetaData(Parcel in) {
			this.url = in.readString();
			this.format = in.readString();
			this.height = in.readString();
			this.width = in.readString();
		}

		public static final Creator<MediaMetaData> CREATOR = new Creator<MediaMetaData>() {
			@Override
			public MediaMetaData createFromParcel(Parcel in) {
				return new MediaMetaData(in);
			}

			@Override
			public MediaMetaData[] newArray(int size) {
				return new MediaMetaData[size];
			}
		};

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int i) {

			parcel.writeString(this.url);
			parcel.writeString(this.format);
			parcel.writeString(this.height);
			parcel.writeString(this.width);
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}
	}

}
