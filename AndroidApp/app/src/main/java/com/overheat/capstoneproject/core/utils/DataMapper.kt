package com.overheat.capstoneproject.core.utils

import com.overheat.capstoneproject.core.data.source.local.entity.*
import com.overheat.capstoneproject.core.data.source.remote.response.*
import com.overheat.capstoneproject.core.domain.model.*

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

    fun mapEntityToDomainDiagnose(input: List<DiagnoseEntity>) : List<Diagnose> {
        val listDiagnose = ArrayList<Diagnose>()

        input.map {
            val diagnose = Diagnose(
                id = it.id,
                userId = it.userId,
                image = it.image,
                cancerProba = it.cancerProba,
                dateAdded = it.dateAdded
            )

            listDiagnose.add(diagnose)
        }

        return listDiagnose
    }

    fun mapResponseToEntityDiagnose(input: List<DiagnoseResponse>) : List<DiagnoseEntity> {
        val listDiagnose = ArrayList<DiagnoseEntity>()

        input.map {
            val diagnose = DiagnoseEntity(
                id = it.id,
                userId = it.userId,
                image = it.image,
                cancerProba = it.cancerProba,
                dateAdded = it.dateAdded
            )

            listDiagnose.add(diagnose)
        }

        return listDiagnose
    }

    fun mapResponseToDomainListDiagnose(input: List<DiagnoseResponse>) : List<Diagnose> {
        val listDiagnose = ArrayList<Diagnose>()

        input.map {
            val diagnose = Diagnose(
                id = it.id,
                userId = it.userId,
                image = it.image,
                cancerProba = it.cancerProba,
                dateAdded = it.dateAdded
            )

            listDiagnose.add(diagnose)
        }

        return listDiagnose
    }

    fun mapResponseToDomainDiagnose(input: DiagnoseResponse) : Diagnose =
        Diagnose(
            id = input.id,
            userId = input.userId,
            image = input.image,
            cancerProba = input.cancerProba,
            dateAdded = input.dateAdded
        )

    fun mapResponseToDomainResult(input: ResultResponse) : Result =
        Result(
            cancerProba = input.cancerProba,
            email = input.email,
            name = input.name,
            userId = input.userId
        )

    fun mapResponseToDomainDetailArticle(input: DetailArticleResponse) : DetailArticle {
        val article = mapResponseToDomainSingleArticle(input.article)
        val comments = mapResponseToDomainComments(input.comments)

        return DetailArticle(
            article = article,
            comments = comments
        )
    }

    fun mapResponseToDomainComments(input: List<CommentResponse>) : List<Comment> {
        val listComment = ArrayList<Comment>()

        input.map {
            val comment = Comment(
                articleId = it.articleId,
                id = it.id,
                userId = it.userId,
                comment = it.comment,
                dateAdded = it.dateAdded
            )

            listComment.add(comment)
        }

        return listComment
    }

    private fun mapResponseToDomainSingleArticle(input: ArticleResponse) : Article =
        Article(
            id = input.id,
            title = input.title,
            image = input.image,
            article = input.article
        )

    fun mapResponseToEntityUser(input: UserResponse) : UserEntity =
        UserEntity(
            id = input.id,
            name = input.name,
            email = input.email,
            passHash = input.passHash,
            dateAdded = input.dateAdded
        )

    fun mapEntityToDomainComment(input: List<CommentEntity>) : List<Comment> {
        val listComment = ArrayList<Comment>()

        input.map {
            val comment = Comment(
                id = it.id,
                articleId = it.articleId,
                userId = it.user_id,
                comment = it.comment,
                dateAdded = it.dateAdded
            )

            listComment.add(comment)
        }

        return listComment
    }

    fun mapResponseToEntityComment(input: List<CommentResponse>) : List<CommentEntity> {
        val listComment = ArrayList<CommentEntity>()

        input.map {
            val comment = CommentEntity(
                id = it.id,
                user_id = it.userId,
                comment = it.comment,
                articleId = it.articleId,
                dateAdded = it.dateAdded
            )

            listComment.add(comment)
        }

        return listComment
    }

    fun mapResponseToEntitySingleArticle(input: ArticleResponse) : ArticleEntity =
        ArticleEntity(
            id = input.id,
            title = input.title,
            image = input.image,
            article = input.article
        )

    fun mapEntityToDomainSingleArticle(input: ArticleEntity) : Article =
        Article(
            id = input.id,
            title = input.title,
            image = input.image,
            article = input.article
        )
}