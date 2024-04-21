package com.news.application

import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface  NewsRepository : CrudRepository<News, BigInteger> {}

