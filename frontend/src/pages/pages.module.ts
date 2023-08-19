import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { LoginPageComponent } from './login-page/login-page.component'
import { HttpClientModule } from '@angular/common/http'
import { RouterLink } from '@angular/router'
import { CallbackPageComponent } from './callback-page/callback-page.component'
import { PlaylistsViewComponent } from './playlists-view/playlists-view.component'

@NgModule({
  declarations: [
    LoginPageComponent,
    CallbackPageComponent,
    PlaylistsViewComponent
  ],
  exports: [
    LoginPageComponent
  ],
  imports: [
    CommonModule,
    NgOptimizedImage,
    HttpClientModule,
    RouterLink
  ]
})
export class PagesModule {
}
