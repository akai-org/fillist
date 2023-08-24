import { Component, ViewContainerRef } from '@angular/core'
import { Oauth2SsoService } from '../security/services/oauth2-sso.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'fillist'

  constructor (viewContainerRef: ViewContainerRef) {
    Oauth2SsoService.setViewContainerRef(viewContainerRef)
  }
}
