import { Component, OnInit } from '@angular/core'
import { UserProfileService } from '../../services/user-profile.service'
import { map, Observable } from 'rxjs'

@Component({
  selector: 'fillist-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent implements OnInit {
  smallImage: Observable<string> = this.userPanelService.userProfile.pipe(map(it => it.smallImageUrl))

  constructor (private userPanelService: UserProfileService) {
  }

  ngOnInit (): void {
    this.userPanelService.userProfile.subscribe(it => { console.log(it) })
  }
}