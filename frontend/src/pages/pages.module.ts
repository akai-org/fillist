import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { LoginPageComponent } from './login-page/login-page.component'
import { HttpClientModule } from '@angular/common/http'
import { RouterLink, RouterModule, Routes } from '@angular/router'
import { CallbackPageComponent } from './callback-page/callback-page.component'
import { PlaylistsViewComponent } from './playlists-view/playlists-view.component'
import { UiModule } from '../shared/ui/ui.module'

const routes: Routes = [
  {
    path: '',
    component: PlaylistsViewComponent
  }
]
@NgModule({
  declarations: [
    LoginPageComponent,
    CallbackPageComponent,
    PlaylistsViewComponent
  ],
  exports: [
    LoginPageComponent,
    CallbackPageComponent,
    RouterModule
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    NgOptimizedImage,
    HttpClientModule,
    RouterLink,
    UiModule
  ]
})
export class PagesModule {
}
