# Musify demo service

Simple implementation of the coding challenge using MusicBrainz, Wikidata/Wikipedia and Cover Art Archive.

## How to run
From the root of the project run: `mvn spring-boot:run`

```
curl http://localhost:8081/musify/music-artist/details/f27ec8db-af05-4f36-916e-3d57f91ecf5e
curl http://localhost:8081/musify/music-artist/details/a04e7da2-998b-4e36-abaa-014c00b93622
curl http://localhost:8081/musify/music-artist/details/daeec599-bf5d-4428-994a-e76ba5f86e2f
```

## Dependencies
* Java 17
* Kotlin 1.6.10
* Spring Boot starter web
* Squareup okhttp (http client)
* Square Moshi json library for Kotlin (https://github.com/square/moshi)
* Square OkHttp HTTP client (https://github.com/square/okhttp)

## Design
The Musify service is calling the external services (mostly) synchronously at request time, as there is a tight dependency 
between the data. Given an artist mbid we first check if we have it cached, if not we check MusicBrainz for the main artist data. We take the Q-id and ask WikiData for the Wikipedia title name to extract the artist summary. Lastly we check Cover Art archive for images for each album. This could be done completely separately from Wikidata/Wikipedia.

We could also prepopulate Musify with the most popular artist. 

## Limitations
* Not 100% production ready due to time constraints 
* New language and libs means some parts can be improved, mainly concurrency
* Trade-off between productivity and testability
* Cache misses result in long response times
* Only mapping required source data to fulfil requirement

## Todo
* Improve test coverage
* Add metrics (prometheus/influx)
* Scale horizontally (just spin up more instances)
* Use a shared cache between instances (redis) or a central permanent storage (nosql)
* Improve error handling
  * Gracefully handle corrupt data from external services
  * Add timeouts
* Revisit the async bits (Kotlin concurrency is new to me)
* Add logging (sl4j, logback)
* Add http client caching defaults
* Maybe add interfaces to improve testability
* Add proper response headers incl cache