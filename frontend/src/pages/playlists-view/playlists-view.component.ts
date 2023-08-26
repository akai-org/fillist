import { Component, OnInit } from '@angular/core'
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'fillist-playlists-view',
  templateUrl: './playlists-view.component.html',
  styleUrls: ['./playlists-view.component.scss']
})
export class PlaylistsViewComponent implements OnInit {
  constructor (private http: HttpClient) { }

  ngOnInit (): void {
    this.http.get('https://api.spotify.com/v1/me', {}).subscribe((response: any) => {
      console.log(response)
    })
  }
}
