import { ViewContainerRef } from '@angular/core'
import { AlertComponent } from '../components/alert/alert.component'
import { AlertColor } from '../components/alert/alertColor'

export class AlertService {
  static alertCount: number = 0
  constructor (private viewContainerRef: ViewContainerRef) {
  }

  displayAlert (message: string, type: AlertColor = AlertColor.ERROR, onClose: () => void = () => {
  }): void {
    const componentRef = this.viewContainerRef.createComponent(AlertComponent)
    componentRef.instance.message = message
    componentRef.instance.type = type
    AlertService.alertCount += 1
    console.log(AlertService.alertCount)
    componentRef.instance.order = AlertService.alertCount
    componentRef.instance.onClose.subscribe(() => {
      componentRef.destroy()
      AlertService.alertCount -= 1
      onClose()
    })
    componentRef.changeDetectorRef.detectChanges()
  }
}
