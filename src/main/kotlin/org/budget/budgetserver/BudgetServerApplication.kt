package org.budget.budgetserver

import com.google.common.cache.CacheBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

@SpringBootApplication
class BudgetServerApplication

fun main(args: Array<String>) {
    runApplication<BudgetServerApplication>(*args)
}

@Bean
fun cacheManager(): CacheManager {
    return object : ConcurrentMapCacheManager() {
        override fun createConcurrentMapCache(name: String): Cache {
            return ConcurrentMapCache(
                name,
                CacheBuilder.newBuilder()
                    .expireAfterWrite(1, TimeUnit.SECONDS)
                    .build<Any, Any>().asMap(),
                false)
        }
    }
}