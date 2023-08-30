import { Component, OnInit } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { AuthService } from '../../security/services/auth.service'

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
    private authService: AuthService
  ) {
  }

  ngOnInit (): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.authService.getNewAccessTokenAndSetSession(
        params[CallbackPageComponent.CODE_KEY],
        params[CallbackPageComponent.STATE_KEY]
      )
    })
  }
}
