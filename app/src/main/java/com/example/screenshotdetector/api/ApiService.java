package com.example.screenshotdetector.api;

import com.example.screenshotdetector.model.Product;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @POST("similar_product")
    Observable<List<Product>> getProducts(@Body  MultipartBody image);

}
