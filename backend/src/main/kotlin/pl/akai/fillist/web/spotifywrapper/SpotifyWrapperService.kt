package pl.akai.fillist.web.spotifywrapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService

@Service
class SpotifyWrapperService @Autowired constructor(
    val user: SpotifyUserService,
)
