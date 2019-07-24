package com.ss.nytimesmostpopular.api;

import com.ss.nytimesmostpopular.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shubham Singhal
 */
public interface NewsApiClient {

	@GET("shared/{period}/email.json")
	Call<NewsModel> getTopHeadlines(@Path ("period") String period,@Query("api-key") String key);
}
