import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { SpinnerComponent } from './components/spinner/spinner.component'
import { AlertComponent } from './components/alert/alert.component'
import { NgIconsModule } from '@ng-icons/core'
import { cssClose, cssDanger } from '@ng-icons/css.gg'

@NgModule({
  declarations: [
    SpinnerComponent,
    AlertComponent
  ],
  exports: [
    SpinnerComponent,
    AlertComponent
  ],
  imports: [
    CommonModule,
    NgOptimizedImage,
    NgIconsModule.withIcons({ cssClose, cssDanger })
  ]
})
export class UiModule {
}
