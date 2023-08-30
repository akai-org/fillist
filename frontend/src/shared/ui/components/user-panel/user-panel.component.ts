import { Component } from '@angular/core'
import { UserProfileService } from '../../services/user-profile.service'
import { map, Observable } from 'rxjs'
import { TopViewContainerRefService } from '../../services/top-view-container-ref.service'

@Component({
  selector: 'fillist-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent {
  smallImage: Observable<string> = this.userPanelService.userProfile.pipe(map(it => it.smallImageUrl))

  constructor (
    private userPanelService: UserProfileService,
    private topViewContainerRef: TopViewContainerRefService
  ) {}

  openUserPanel (): void {
    this.topViewContainerRef.displayProfilePanel()
  }
}
