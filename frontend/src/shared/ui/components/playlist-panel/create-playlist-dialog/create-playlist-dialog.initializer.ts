import { DialogInitializer } from '../../../models/dialog.initializer'
import { TopViewContainerRefService } from '../../../services/top-view-container-ref.service'
import { CreatePlaylistDialogComponent } from './create-playlist-dialog.component'

export class CreatePlaylistDialogInitializer implements DialogInitializer {
  constructor (private onClose: () => void) {
  }

  init (): void {
    const componentRef = TopViewContainerRefService.getComponent<CreatePlaylistDialogComponent>(CreatePlaylistDialogComponent)
    componentRef.instance.onClose.subscribe(() => {
      componentRef.destroy()
      this.onClose()
    })
    componentRef.changeDetectorRef.detectChanges()
  }
}
