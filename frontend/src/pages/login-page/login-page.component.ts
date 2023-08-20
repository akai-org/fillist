import { Component } from '@angular/core'
import { Oauth2SsoService } from '../../security/services/oauth2-sso.service'
import { Observable } from 'rxjs'

@Component({
  selector: 'fillist-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {
  authorizationCodeUrl$: Observable<string> = this.oauth2SsoService.getAuthorizationCodeUrl()
  constructor (private oauth2SsoService: Oauth2SsoService) {
  }
}
