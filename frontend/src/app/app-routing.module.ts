import { NgModule } from '@angular/core'
import { RouterModule, type Routes } from '@angular/router'
import { LoginPageComponent } from '../pages/login-page/login-page.component'
import { CallbackPageComponent } from '../pages/callback-page/callback-page.component'

const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'callback', component: CallbackPageComponent }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
