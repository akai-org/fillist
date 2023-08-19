import { CanActivateFn } from '@angular/router'
import { inject } from '@angular/core'
import { Oauth2SsoService } from '../services/oauth2-sso.service'

export const loggedInActivate: CanActivateFn = () => {
  const oauthService: Oauth2SsoService = inject(Oauth2SsoService)
  if (!oauthService.isLoggedIn()) {
    oauthService.redirectToLoginIn()
    return false
  }
  return true
}
