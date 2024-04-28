package com.news.application

import com.news.application.api.NewsApiDelegate
import com.news.application.api.model.NewsApiDto
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class NewsRestcontroller(val db: NewsService): NewsApiDelegate {
    override fun getAllNews(): ResponseEntity<MutableList<NewsApiDto>> {
//        return super.getAllNews()
        var results = db.getAll()
        return ResponseEntity.ok(results)
    }

    override fun getSingleNews(id: Int?): ResponseEntity<NewsApiDto> {
        return ResponseEntity.ok(id?.let { db.getSingle(it) })
    }
}