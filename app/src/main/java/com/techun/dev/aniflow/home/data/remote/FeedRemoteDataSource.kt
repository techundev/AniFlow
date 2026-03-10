package com.techun.dev.aniflow.home.data.remote


import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import com.techun.dev.aniflow.home.data.model.RssItemDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class FeedRemoteDataSource(
    private val httpClient: HttpClient
) {
    private val FEED_URL = "https://myanimelist.net/rss/news.xml"

    suspend fun fetchFeed(): List<RssItemDto> {
        val rawXml: String = httpClient.get(FEED_URL).bodyAsText()

        val imageUrls = extractImageUrls(rawXml)

        val feed = SyndFeedInput().build(XmlReader(rawXml.byteInputStream()))

        return feed.entries.mapIndexed { index, entry ->
            RssItemDto(
                title = entry.title.orEmpty(),
                link = entry.link.orEmpty(),
                description = entry.description?.value.orEmpty(),
                pubDate = entry.publishedDate?.toString().orEmpty(),
                author = entry.author.orEmpty(),
                imageUrl = imageUrls.getOrElse(index) { "" })
        }
    }

    private fun extractImageUrls(xml: String): List<String> {
        return Regex("""<media:thumbnail>(https?://[^<]+)</media:thumbnail>""").findAll(xml)
            .map { it.groupValues[1].trim() }.toList()
    }
}