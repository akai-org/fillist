import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { LoginPageComponent } from './login-page/login-page.component'

@NgModule({
  declarations: [
    LoginPageComponent
  ],
  exports: [
    LoginPageComponent
  ],
  imports: [
    CommonModule,
    NgOptimizedImage
  ]
})
export class PagesModule {
}
