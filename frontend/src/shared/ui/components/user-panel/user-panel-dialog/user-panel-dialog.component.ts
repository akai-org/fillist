import { Component, EventEmitter, Output } from '@angular/core'
import { UserProfileService } from '../../../services/user-profile.service'
import { map, Observable } from 'rxjs'
import { UserProfileInterface } from '../../../models/user-profile.interface'
import { AuthService } from '../../../../../security/services/auth.service'
import { DialogComponent } from '../../../models/dialog-component.interface'

@Component({
  selector: 'fillist-user-panel-dialog',
  templateUrl: './user-panel-dialog.component.html',
  styleUrls: ['./user-panel-dialog.component.scss']
})
export class UserPanelDialogComponent implements DialogComponent {
  image$: Observable<string> = this.userProfileService.userProfile.pipe(map((profile: UserProfileInterface) => profile.largeImageUrl))
  displayName$: Observable<string> = this.userProfileService.userProfile.pipe(map((profile: UserProfileInterface) => profile.displayName))
  email$: Observable<string> = this.userProfileService.userProfile.pipe(map((profile: UserProfileInterface) => profile.email))

  constructor (private userProfileService: UserProfileService, private authService: AuthService) {
  }

  @Output() public onClose = new EventEmitter<() => void>()

  logout (): void {
    this.authService.logout()
    this.onClose.emit()
    this.authService.redirectToLoginIn()
  }

  close (): void {
    this.onClose.emit()
  }
}
