import { DialogInitializer } from '../../models/dialog.initializer'
import { AlertComponent } from './alert.component'
import { TopViewContainerRefService } from '../../services/top-view-container-ref.service'
import { AlertColor } from './alert-color'

export class AlertInitializer implements DialogInitializer {
  constructor (
    private message: string,
    private type: AlertColor = AlertColor.INFO,
    private onClose: () => void = () => {}) {
  }

  static alertCounter: number = 0

  init (): void {
    if (this.message.length > 50) throw new Error('Message too long')
    const componentRef = TopViewContainerRefService.getComponent<AlertComponent>(AlertComponent)
    componentRef.instance.message = this.message
    componentRef.instance.type = this.type
    AlertInitializer.alertCounter += 1
    componentRef.instance.order = AlertInitializer.alertCounter
    componentRef.instance.onClose.subscribe(() => {
      componentRef.destroy()
      AlertInitializer.alertCounter -= 1
      this.onClose()
    })
    componentRef.changeDetectorRef.detectChanges()
  }
}
