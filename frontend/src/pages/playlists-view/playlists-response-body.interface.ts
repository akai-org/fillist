export interface PlaylistsResponseBody {
  total: number
  limit: number
  offset: number
  playlists: PlaylistInterface[]
}

interface PlaylistInterface {
  name: string
  id: string
  image: string
  ownerDisplayName: string
}
