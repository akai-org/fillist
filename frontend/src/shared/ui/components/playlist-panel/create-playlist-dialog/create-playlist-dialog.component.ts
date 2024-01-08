import { Component, EventEmitter, Output } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { PlaylistsResponseBody } from '../../../../../pages/playlists-view/playlists-response-body.interface'

@Component({
  selector: 'fillist-create-playlist-dialog',
  templateUrl: './create-playlist-dialog.component.html',
  styleUrls: ['./create-playlist-dialog.component.scss']
})
export class CreatePlaylistDialogComponent {
  playlistName: string = ''
  playlistDescription: string = ''
  constructor (public httpClient: HttpClient) {
  }

  @Output() public onClose = new EventEmitter<() => void>()
  postPlaylist (): void {
    console.log(this.playlistDescription)
    this.httpClient.post<PlaylistsResponseBody>('/playlists', {
      name: this.playlistName,
      description: this.playlistDescription,
      public: true
    }).subscribe((PlaylistsResponseBody) => {
      this.close()
    })
  }

  changeDescriptionValue (newValue: string): void {
    this.playlistDescription = newValue
  }

  changeNameValue (newValue: string): void {
    this.playlistName = newValue
  }

  close (): void {
    this.onClose.emit()
  }
}
