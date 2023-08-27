import { Injectable, ViewContainerRef } from '@angular/core'
import { AlertComponent } from '../components/alert/alert.component'
import { AlertColor } from '../components/alert/alertColor'

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  static alertCount: number = 0

  private static viewContainerRef: ViewContainerRef | undefined

  static setViewContainerRef (viewContainerRef: ViewContainerRef): void {
    AlertService.viewContainerRef = viewContainerRef
  }

  displayAlert (message: string, type: AlertColor = AlertColor.ERROR, onClose: () => void = () => {
  }): void {
    if (message.length > 50) throw new Error('Message too long')
    if (AlertService.viewContainerRef == null) throw new Error('ViewContainerRef not set')
    const componentRef = AlertService.viewContainerRef.createComponent(AlertComponent)
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
