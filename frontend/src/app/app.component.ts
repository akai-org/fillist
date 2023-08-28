import { Component, OnInit, ViewContainerRef } from '@angular/core'
import { AlertService } from '../shared/ui/services/alert.service'
import { Router } from '@angular/router'
import { environment } from '../environments/environment'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'fillist'
  showHeader = true

  constructor (viewContainerRef: ViewContainerRef, private router: Router) {
    AlertService.setViewContainerRef(viewContainerRef)
  }

  ngOnInit (): void {
    const frontendUrlLength = environment.frontendUrl.length + 1
    const currentUrl = window.location.href.substring(frontendUrlLength)
    const currentRouterConfig = this.router.config.filter(route => route.path === currentUrl)[0]
    currentRouterConfig.canActivate?.forEach(guard => {
      if (guard.name === 'loggedOutActivate') this.showHeader = false
    })
  }
}
