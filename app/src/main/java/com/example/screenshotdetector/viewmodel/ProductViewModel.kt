package com.example.screenshotdetector.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baseproject.api.RetrofitClient
import com.example.screenshotdetector.model.Product
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProductViewModel : ViewModel() {
    private val TAG = "HomeViewModel"
    var mediaList: MutableLiveData<List<Product?>?> = MutableLiveData()



    fun getProducts(file:File) {
        val MEDIA_TYPE =
            if (file.endsWith("png")) MediaType.parse("image/png") else MediaType.parse("image/jpeg")

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
            .build()
        RetrofitClient.aPIService.getProducts(
            body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                object : Observer<List<Product>> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "onSubscribe: ")
                    }

                    override fun onNext(t: List<Product>) {
                        mediaList.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: " + e.message)
                        mediaList.value = mutableListOf()
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                    }

                }
            )
    }


}