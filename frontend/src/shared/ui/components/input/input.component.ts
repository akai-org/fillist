import { Component, EventEmitter, Input, Output } from '@angular/core'

@Component({
  selector: 'fillist-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent {
  @Input() type: string = 'text'
  @Input() labelText: string = 'Label'
  @Input() placeholder: string = 'placeholder'
  value: string = ''
  @Output() valueChange = new EventEmitter<string>()
  onValueChange (e: any): void {
    this.value = e.target.value
    this.valueChange.emit(this.value)
  }
}
