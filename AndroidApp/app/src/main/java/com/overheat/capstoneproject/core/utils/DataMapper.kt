package com.overheat.capstoneproject.core.utils

import com.overheat.capstoneproject.core.data.source.local.entity.ArticleEntity
import com.overheat.capstoneproject.core.data.source.local.entity.FaqEntity
import com.overheat.capstoneproject.core.data.source.remote.response.ArticleResponse
import com.overheat.capstoneproject.core.data.source.remote.response.FaqResponse
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.Faq

object DataMapper {

    fun mapResponseToEntityFaq(input: List<FaqResponse>) : List<FaqEntity> {
        val listFaqs = ArrayList<FaqEntity>()

        input.map {
            val faq = FaqEntity(
                id = it.id,
                question = it.question,
                answer = it.answer
            )

            listFaqs.add(faq)
        }

        return listFaqs
    }

    fun mapEntityToDomainFaq(input: List<FaqEntity>) : List<Faq> {
        val listFaqs = ArrayList<Faq>()

        input.map {
            val faq = Faq(
                id = it.id,
                question = it.question,
                answer = it.answer
            )

            listFaqs.add(faq)
        }

        return listFaqs
    }

    fun mapResponseToEntityArticle(input: List<ArticleResponse>) : List<ArticleEntity> {
        val listArticles = ArrayList<ArticleEntity>()

        input.map {
            val article = ArticleEntity(
                id = it.id,
                title = it.title,
                image = it.image,
                article = it.article
            )

            listArticles.add(article)
        }

        return listArticles
    }

    fun mapEntityToDomainArticle(input: List<ArticleEntity>) : List<Article> {
        val listArticles = ArrayList<Article>()

        input.map {
            val article = Article(
                id = it.id,
                title = it.title,
                image = it.image,
                article = it.article
            )

            listArticles.add(article)
        }

        return listArticles
    }
}