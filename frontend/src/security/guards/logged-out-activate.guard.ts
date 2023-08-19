import { CanActivateFn } from '@angular/router'
import { Oauth2SsoService } from '../services/oauth2-sso.service'
import { inject } from '@angular/core'

export const loggedOutActivate: CanActivateFn = () => {
  const oauthService: Oauth2SsoService = inject(Oauth2SsoService)
  if (oauthService.isLoggedIn()) {
    oauthService.redirectToDashboard()
    return false
  }
  return true
}
