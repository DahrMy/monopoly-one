package my.dahr.monopolyone.data.source.auth.local

sealed interface SessionLocalDataSource {
    var session: ParcelableSession
}