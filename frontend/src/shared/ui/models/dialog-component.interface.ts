import { EventEmitter } from '@angular/core'

export interface DialogComponent {
  onClose: EventEmitter<() => void>
}
