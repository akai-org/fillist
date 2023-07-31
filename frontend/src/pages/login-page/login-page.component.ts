import { Component, type OnInit } from '@angular/core'
import { OAuthCodeService } from '../../security/oauth-code/service/oauth-code.service'
import { Observable } from 'rxjs'

@Component({
  selector: 'fillist-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loginUrl$: Observable<string> | undefined
  constructor (protected oAuthCodeService: OAuthCodeService) {
  }

  ngOnInit (): void {
    this.loginUrl$ = this.oAuthCodeService.getAuthorizationCodeUrl()
  }
}
