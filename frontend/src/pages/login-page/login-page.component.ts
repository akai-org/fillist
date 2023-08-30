import { Component } from '@angular/core'
import { AuthService } from '../../security/services/auth.service'
import { Observable } from 'rxjs'

@Component({
  selector: 'fillist-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {
  authorizationCodeUrl$: Observable<string> = this.authService.getAuthorizationCodeUrl()
  constructor (private authService: AuthService) {
  }
}
