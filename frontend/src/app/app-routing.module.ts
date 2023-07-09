import { NgModule } from '@angular/core'
import { RouterModule, type Routes } from '@angular/router'
import { LoginPageComponent } from '../pages/login-page/login-page.component'

const routes: Routes = [
  { path: '', component: LoginPageComponent }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
