import { NgModule } from '@angular/core'
import { RouterModule, type Routes } from '@angular/router'
import { LoginPageComponent } from '../pages/login-page/login-page.component'
import { CallbackPageComponent } from '../pages/callback-page/callback-page.component'
import { PlaylistsViewComponent } from '../pages/playlists-view/playlists-view.component'
import { loggedInActivate } from '../security/guards/logged-in-activate.guard'
import { loggedOutActivate } from '../security/guards/logged-out-activate.guard'

const routes: Routes = [
  { path: 'login', component: LoginPageComponent, canActivate: [loggedOutActivate] },
  { path: 'callback', component: CallbackPageComponent, canActivate: [loggedOutActivate] },
  { path: '', component: PlaylistsViewComponent, canActivate: [loggedInActivate] }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
