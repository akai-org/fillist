import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { SpinnerComponent } from './spinner/spinner.component'

@NgModule({
  declarations: [
    SpinnerComponent
  ],
  exports: [
    SpinnerComponent
  ],
  imports: [
    CommonModule,
    NgOptimizedImage
  ]
})
export class UiModule { }
