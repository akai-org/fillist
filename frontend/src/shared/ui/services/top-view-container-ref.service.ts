import { Injectable, ViewContainerRef } from '@angular/core'
import { AlertComponent } from '../components/alert/alert.component'
import { AlertColor } from '../components/alert/alertColor'
import { UserPanelDialogComponent } from '../components/user-panel/user-panel-dialog/user-panel-dialog.component'

@Injectable({
  providedIn: 'root'
})
export class TopViewContainerRefService {
  static alertCount: number = 0

  private static viewContainerRef: ViewContainerRef | undefined

  static setViewContainerRef (viewContainerRef: ViewContainerRef): void {
    TopViewContainerRefService.viewContainerRef = viewContainerRef
  }

  displayAlert (message: string, type: AlertColor = AlertColor.ERROR, onClose: () => void = () => {
  }): void {
    if (message.length > 50) throw new Error('Message too long')
    if (TopViewContainerRefService.viewContainerRef == null) throw new Error('ViewContainerRef not set')
    const componentRef = TopViewContainerRefService.viewContainerRef.createComponent(AlertComponent)
    componentRef.instance.message = message
    componentRef.instance.type = type
    TopViewContainerRefService.alertCount += 1
    console.log(TopViewContainerRefService.alertCount)
    componentRef.instance.order = TopViewContainerRefService.alertCount
    componentRef.instance.onClose.subscribe(() => {
      componentRef.destroy()
      TopViewContainerRefService.alertCount -= 1
      onClose()
    })
    componentRef.changeDetectorRef.detectChanges()
  }

  displayProfilePanel (onClose: () => void = () => {}): void {
    if (TopViewContainerRefService.viewContainerRef == null) throw new Error('ViewContainerRef not set')
    const componentRef = TopViewContainerRefService.viewContainerRef.createComponent(UserPanelDialogComponent)
    componentRef.instance.onClose.subscribe(() => {
      componentRef.destroy()
      onClose()
    })
    componentRef.changeDetectorRef.detectChanges()
  }
}
