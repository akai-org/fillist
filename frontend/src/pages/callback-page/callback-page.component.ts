import { Component, OnInit } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { Oauth2SsoService } from '../../security/services/oauth2-sso.service'

@Component({
  selector: 'fillist-callback-page',
  templateUrl: './callback-page.component.html',
  styleUrls: ['./callback-page.component.scss']
})
export class CallbackPageComponent implements OnInit {
  static readonly STATE_KEY: string = 'state'
  static readonly CODE_KEY: string = 'code'

  constructor (
    private activatedRoute: ActivatedRoute,
    private oauth2SsoService: Oauth2SsoService
  ) {
  }

  ngOnInit (): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.oauth2SsoService.getAccessTokenAndSetSession(
        params[CallbackPageComponent.CODE_KEY],
        params[CallbackPageComponent.STATE_KEY]
      )
      this.oauth2SsoService.redirectToDashboard()
    })
  }
}
