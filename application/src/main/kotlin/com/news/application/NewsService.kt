package com.news.application

import com.news.application.api.model.NewsApiDto
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class NewsService(val db: NewsRepository) {
    fun create(news: NewsApiDto) : NewsApiDto {
        var result = db.save(convertFromDTO(news))
        return convertToDTO(result)
    }

    fun getAll() : MutableList<NewsApiDto> {
        var results = db.findAll()
        val newList : MutableList<NewsApiDto> = mutableListOf()
        for(newsElement in results){
            newList.add(convertToDTO(newsElement))
        }
        return newList
    }
    fun getSingle(id: Int) : NewsApiDto{
        var result = db.findById(id.toBigInteger())
        return convertToDTO(result.get())
    }

    fun convertToDTO(news: News) : NewsApiDto {
        val result : NewsApiDto = NewsApiDto()
        result.id = news.id
        result.date = news.date
        result.text = news.text
        result.author = news.author
        result.title = news.title

        return result
    }

    fun convertFromDTO(news: NewsApiDto) : News {
        val result : News = News()
        result.id = news.id
        result.date = news.date
        result.text = news.text
        result.author = news.author
        result.title = news.title

        return result
    }
}