import { CanActivateFn } from '@angular/router'
import { inject } from '@angular/core'
import { AuthService } from '../services/auth.service'

export const loggedInActivate: CanActivateFn = () => {
  const oauthService: AuthService = inject(AuthService)
  if (!oauthService.isLoggedIn()) {
    oauthService.redirectToLoginIn()
    return false
  }
  return true
}
