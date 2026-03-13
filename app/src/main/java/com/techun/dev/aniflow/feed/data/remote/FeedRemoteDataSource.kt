package com.techun.dev.aniflow.feed.data.remote


import android.util.Log
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import com.techun.dev.aniflow.core.model.RssSource
import com.techun.dev.aniflow.feed.data.model.RssItemDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class FeedRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun fetchFeed(): List<RssItemDto> {
        return coroutineScope {
            RssSource.entries.map { source ->
                async {
                    try {
                        fetchSource(source)
                    } catch (e: Exception) {
                        Log.e(
                            "FeedRemoteDataSource", "Error fetching ${source.label}: ${e.message}"
                        )
                        emptyList()
                    }
                }
            }.awaitAll().flatten()
        }
    }

    private suspend fun fetchSource(source: RssSource): List<RssItemDto> {
        val rawXml = httpClient.get(source.url).bodyAsText()
        val feed = SyndFeedInput().build(XmlReader(rawXml.byteInputStream()))

        return when (source) {
            RssSource.MAL -> parseMal(feed, rawXml, source.label)
            RssSource.CRUNCHYROLL -> parseCrunchyroll(feed, rawXml, source.label)
        }
    }

    private fun parseMal(feed: SyndFeed, rawXml: String, label: String): List<RssItemDto> {
        val imageUrls =
            Regex("""<media:thumbnail>(https?://[^<]+)</media:thumbnail>""").findAll(rawXml)
                .map { it.groupValues[1].trim() }.toList()

        return feed.entries.mapIndexed { index, entry ->
            RssItemDto(
                title = entry.title.orEmpty(),
                link = entry.link.orEmpty(),
                description = entry.description?.value.orEmpty(),
                pubDate = entry.publishedDate?.toString().orEmpty(),
                author = entry.author.orEmpty(),
                imageUrl = imageUrls.getOrElse(index) { "" },
                source = label
            )
        }
    }

    private fun parseCrunchyroll(feed: SyndFeed, rawXml: String, label: String): List<RssItemDto> {
        val imageUrls =
            Regex("""<media:thumbnail url="(https?://[^"]+)" width="640"""").findAll(rawXml)
                .map { it.groupValues[1].trim() }.toList()

        return feed.entries.mapIndexed { index, entry ->
            val cleanDescription =
                entry.description?.value?.replace(Regex("<[^>]+>"), "")?.trim().orEmpty()

            RssItemDto(
                title = entry.title.orEmpty(),
                link = entry.link.orEmpty(),
                description = cleanDescription,
                pubDate = entry.publishedDate?.toString().orEmpty(),
                author = entry.author.orEmpty(),
                imageUrl = imageUrls.getOrElse(index) { "" },
                source = label
            )
        }
    }
}