import { Component, EventEmitter, Input, Output } from '@angular/core'
import { AlertColor } from './alert-color'

@Component({
  selector: 'fillist-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent {
  @Output() public onClose = new EventEmitter<() => void>()
  @Input() public message: string = 'An error occurred!'
  @Input() public type: AlertColor = AlertColor.ERROR
  @Input() public order = 1

  public buttonHandler (): void {
    this.onClose.emit()
  }
}
