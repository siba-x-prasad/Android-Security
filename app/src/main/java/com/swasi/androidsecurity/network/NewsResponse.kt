package com.swasi.androidsecurity.network

import androidx.annotation.Keep

@Keep
data class NewsResponse(val listNews: List<News>)

@Keep
data class News(val name: String, val title: String, val desc: String)
