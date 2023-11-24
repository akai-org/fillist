import { Component } from '@angular/core'
import { UserProfileService } from '../../services/user-profile.service'
import { map, Observable } from 'rxjs'
import { TopViewContainerRefService } from '../../services/top-view-container-ref.service'
import { UserPanelDialogInitializer } from './user-panel-dialog/user-panel-dialog.initializer'

@Component({
  selector: 'fillist-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent {
  smallImage: Observable<string> = this.userPanelService.userProfile.pipe(map(it => it.smallImageUrl))
  dataIsReady: Observable<boolean> = this.userPanelService.userProfile.pipe(map(it => it.displayName !== ''))
  panelIsOpen = false

  constructor (
    protected userPanelService: UserProfileService,
    private topViewContainerRef: TopViewContainerRefService
  ) {
  }

  openUserPanel (): void {
    if (this.panelIsOpen) return
    this.panelIsOpen = true
    this.topViewContainerRef.displayDialog(new UserPanelDialogInitializer(
      () => {
        this.panelIsOpen = false
      })
    )
  }
}
