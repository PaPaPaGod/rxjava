package com.example.administrator.rxjava.service;

import com.example.administrator.rxjava.bean.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IGetTranslation {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getTranslation();
}
