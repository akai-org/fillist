import { ComponentRef, Injectable, Type, ViewContainerRef } from '@angular/core'
import { DialogInitializer } from '../models/dialog.initializer'

@Injectable({
  providedIn: 'root'
})
export class TopViewContainerRefService {
  static alertCount: number = 0

  private static viewContainerRef: ViewContainerRef | undefined

  static setViewContainerRef (viewContainerRef: ViewContainerRef): void {
    TopViewContainerRefService.viewContainerRef = viewContainerRef
  }

  static getComponent<T>(componentType: Type<T>): ComponentRef<T> {
    if (TopViewContainerRefService.viewContainerRef == null) throw new Error('ViewContainerRef not set')
    return TopViewContainerRefService.viewContainerRef.createComponent<T>(componentType)
  }

  displayDialog (dialogInitializer: DialogInitializer): void {
    dialogInitializer.init()
  }
}
