import { Component, OnInit } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { PlaylistsResponseBody } from './playlists-response-body.interface'
import { Page } from '../../shared/ui/components/paginator/page.interface'

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
    this.getPlaylists({ offset: 0, limit: 19 })
  }

  getPlaylists (page: Page): void {
    this.httpClient.get<PlaylistsResponseBody>('/playlists', {
      params: {
        offset: page.offset.toString(),
        limit: page.limit.toString()
      }
    }).subscribe((data: PlaylistsResponseBody) => {
      this.playlistsResponse = data
    })
  }

  changePage (page: Page): void {
    this.getPlaylists(page)
  }
}
