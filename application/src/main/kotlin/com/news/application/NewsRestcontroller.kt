package com.news.application

import com.news.application.api.NewsApiDelegate
import com.news.application.api.model.NewsApiDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class NewsRestcontroller: NewsApiDelegate {
    override fun getAllNews(): ResponseEntity<MutableList<NewsApiDto>> {
        return super.getAllNews()
    }
}