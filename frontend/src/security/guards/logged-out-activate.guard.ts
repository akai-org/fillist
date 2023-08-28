import { CanActivateFn } from '@angular/router'
import { AuthService } from '../services/auth.service'
import { inject } from '@angular/core'

export const loggedOutActivate: CanActivateFn = () => {
  const oauthService: AuthService = inject(AuthService)
  if (oauthService.isLoggedIn()) {
    oauthService.redirectToDashboard()
    return false
  }
  return true
}
