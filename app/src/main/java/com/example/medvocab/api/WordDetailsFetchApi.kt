package com.example.medvocab.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WordDetailsFetchApi {

    //https://opentdb.com/api.php?amount=3&difficulty=medium&type=boolean
    //@GET("api.php?amount=3&type=boolean")

    //https://www.dictionaryapi.com/api/v3/references/collegiate/json/doctor?key=bc6eb0ce-9151-4492-be3b-2dc815ad797a
    @GET("api/v3/references/collegiate/json/{word}?")
    suspend fun getWordDetails(@Path("word") word : String, @Query("key") key: String) : List<MedicalTerm>

    //suspend fun getThree(@Query("difficulty") level: String, @Query("amount") amount: Int, @Query("type") b: String) : TriviaResponse

    data class MedicalTermResponse(val results: List<MedicalTerm>)

    companion object {
        // Leave this as a simple, base URL.  That way, we can have many different
        // functions (above) that access different "paths" on this server
        // https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/
        //https://www.dictionaryapi.com/api/v3/references/collegiate/json/doctor?key=bc6eb0ce-9151-4492-be3b-2dc815ad797a
//        var url = HttpUrl.Builder()
//            .scheme("https")
//            .host("opentdb.com")
//            .build()

        var url = HttpUrl.Builder()
            .scheme("https")
            .host("www.dictionaryapi.com")
            .build()

        fun create(): WordDetailsFetchApi = create(url)

        private fun create(httpUrl: HttpUrl): WordDetailsFetchApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WordDetailsFetchApi::class.java)
        }

    }

}