package com.ss.nytimesmostpopular.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shubham Singhal
 */
public class NewsApi {

	private static Retrofit retrofit = null;

	public static Retrofit getRetrofitClient(){
		if(retrofit == null){
			retrofit = new Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create())
					.baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
					.build();
		}
		return retrofit;
	}
}
