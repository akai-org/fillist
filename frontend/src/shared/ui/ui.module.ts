import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { SpinnerComponent } from './components/spinner/spinner.component'
import { AlertComponent } from './components/alert/alert.component'
import { NgIconsModule } from '@ng-icons/core'
import { cssClose, cssDanger } from '@ng-icons/css.gg'
import { HeaderComponent } from './components/header/header.component'
import { RouterLink } from '@angular/router'

@NgModule({
  declarations: [
    SpinnerComponent,
    AlertComponent,
    HeaderComponent
  ],
  exports: [
    SpinnerComponent,
    AlertComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    NgOptimizedImage,
    NgIconsModule.withIcons({
      cssClose,
      cssDanger
    }),
    RouterLink
  ]
})
export class UiModule {
}
