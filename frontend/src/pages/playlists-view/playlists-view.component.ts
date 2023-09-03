import { Component, OnInit } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { PlaylistsResponseBody } from './playlists-response-body.interface'

@Component({
  selector: 'fillist-playlists-view',
  templateUrl: './playlists-view.component.html',
  styleUrls: ['./playlists-view.component.scss']
})
export class PlaylistsViewComponent implements OnInit {
  playlistsResponse: PlaylistsResponseBody | undefined

  constructor (private httpClient: HttpClient) {
  }

  ngOnInit (): void {
    this.getPlaylists()
  }

  getPlaylists (): void {
    this.httpClient.get<PlaylistsResponseBody>('/playlists').subscribe((data: PlaylistsResponseBody) => {
      this.playlistsResponse = data
    })
  }
}
