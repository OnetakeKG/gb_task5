package com.gb.advanced2.externals.repo.articles

import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Article
import com.gb.advanced2.entities.Articles
import com.gb.advanced2.externals.repo.articles.retrofit.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException

class RemoteRepository : Contract.ArticlesModel {
    private val retrofitService: ApiService by lazy { makeRetrofit() }

    private fun makeRetrofit(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    override suspend fun getArticles(searchString: String): Articles {
        // конвертация retrofit объектов в entities.Article
        val out = Articles()
        val resp = retrofitService.search(searchString)
        val body = resp.body()
            ?: throw ConnectException(resp.errorBody()?.toString() ?: "No response from server")
        for (entry in body) {
            if (entry.text == null || entry.meanings == null)
                continue
            val sb = StringBuilder()
            for (meaning in entry.meanings) {
                val trans = meaning.translation ?: continue

                // разделение нескольких смыслов через ;
                if (sb.isNotEmpty()) {
                    sb.append("; ")
                }

                // основной текст
                trans.text?.let { sb.append(it) }

                // пояснительный текст
                if (trans.note != null && trans.note != "") {
                    sb.append(" (${trans.note})")
                }
            }
            out.add(Article(entry.text, sb.toString()))
        }
        return out
    }
}