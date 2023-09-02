import { Component, Input } from '@angular/core'

@Component({
  selector: 'fillist-playlist-tile',
  templateUrl: './playlist-tile.component.html',
  styleUrls: ['./playlist-tile.component.scss']
})
export class PlaylistTileComponent {
  @Input() name: string = ''
  @Input() owner: string = ''
  @Input() image: string = ''
  @Input() id: string = ''
}
