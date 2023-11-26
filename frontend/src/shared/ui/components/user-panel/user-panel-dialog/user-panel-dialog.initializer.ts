import { DialogInitializer } from '../../../models/dialog.initializer'
import { TopViewContainerRefService } from '../../../services/top-view-container-ref.service'
import { UserPanelDialogComponent } from './user-panel-dialog.component'

export class UserPanelDialogInitializer implements DialogInitializer {
  constructor (private onClose: () => void) {
  }

  init (): void {
    const componentRef = TopViewContainerRefService.getComponent<UserPanelDialogComponent>(UserPanelDialogComponent)
    componentRef.instance.onClose.subscribe(() => {
      componentRef.destroy()
      this.onClose()
    })
    componentRef.changeDetectorRef.detectChanges()
  }
}
