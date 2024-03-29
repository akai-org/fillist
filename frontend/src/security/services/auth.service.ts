import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { catchError, map, Observable } from 'rxjs'
import { AuthorizationCodeUrlResponseBodyInterface } from '../models/authorization-code-url-response-body.interface'
import { environment } from 'src/environments/environment'
import { AccessTokenResponseBodyInterface } from '../models/access-token-response-body.interface'
import { Router } from '@angular/router'
import { TopViewContainerRefService } from '../../shared/ui/services/top-view-container-ref.service'
import { AlertColor } from '../../shared/ui/components/alert/alert-color'
import { AlertInitializer } from '../../shared/ui/components/alert/alert.initializer'

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  static readonly OAUTH_STATE_KEY: string = 'oauthState'
  static readonly REFRESH_TOKEN_KEY: string = 'refreshToken'
  static readonly ACCESS_TOKEN_KEY: string = 'accessToken'
  static readonly EXPIRES_IN_KEY: string = 'expiresIn'

  constructor (private http: HttpClient, private router: Router, private topViewContainerRef: TopViewContainerRefService) {
  }

  getAuthorizationCodeUrl (): Observable<string> {
    return this.http.get<AuthorizationCodeUrlResponseBodyInterface>('/oauth2/authorization', {
      params: {
        state: this.getOauthState()
      }
    }).pipe(map((response: AuthorizationCodeUrlResponseBodyInterface) => response.url)).pipe(catchError((err: any) => {
      throw this.loginInErrorHandler(err)
    }))
  }

  getNewAccessTokenAndSetSession (code: string, state: string): void {
    this.getNewAccessToken(code, state).pipe(catchError((err: any) => {
      throw this.loginInErrorHandler(err)
    })).subscribe((accessToken: AccessTokenResponseBodyInterface) => {
      this.setSession(accessToken)
      this.redirectToDashboard()
    })
  }

  loginInErrorHandler (err: any): Error {
    const initializer = new AlertInitializer(
      'Something went wrong. Please try again later.', AlertColor.ERROR, () => {
        this.logout()
        this.redirectToLoginIn()
      }
    )
    this.topViewContainerRef.displayDialog(initializer)
    return new Error(err)
  }

  refreshTokenErrorHandler (err: any): Error {
    this.topViewContainerRef.displayDialog(new AlertInitializer(
      'Session refresh error. Please try login in again.', AlertColor.WARNING, () => {
        this.logout()
        this.redirectToLoginIn()
      }
    ))
    return new Error(err)
  }

  getNewAccessToken (code: string, state: string): Observable<AccessTokenResponseBodyInterface> {
    if (state !== this.getOauthState()) {
      throw this.loginInErrorHandler('Invalid state')
    }
    return this.http.post<AccessTokenResponseBodyInterface>('/oauth2/token', {
      code,
      grantType: 'authorization_code',
      redirectUri: `${environment.frontendUrl}${environment.redirectPath}`
    })
  }

  getNewRefreshedToken (): Observable<AccessTokenResponseBodyInterface> {
    return this.http.post<AccessTokenResponseBodyInterface>('/oauth2/refresh', {
      grantType: 'refresh_token',
      refreshToken: localStorage.getItem(AuthService.REFRESH_TOKEN_KEY)
    })
  }

  setSession (accessTokenResponseBody: AccessTokenResponseBodyInterface): void {
    localStorage.setItem(AuthService.ACCESS_TOKEN_KEY, accessTokenResponseBody.accessToken)
    if (accessTokenResponseBody.refreshToken != null) {
      localStorage.setItem(AuthService.REFRESH_TOKEN_KEY, accessTokenResponseBody.refreshToken)
    }
    const now: number = new Date().getTime()
    const expiresAt: number = now + (accessTokenResponseBody.expiresIn * 1000)
    localStorage.setItem(AuthService.EXPIRES_IN_KEY, expiresAt.toString())
  }

  logout (): void {
    localStorage.removeItem(AuthService.ACCESS_TOKEN_KEY)
    localStorage.removeItem(AuthService.REFRESH_TOKEN_KEY)
    localStorage.removeItem(AuthService.EXPIRES_IN_KEY)
  }

  isLoggedIn (): boolean {
    const accessToken: string | null = localStorage.getItem(AuthService.ACCESS_TOKEN_KEY)
    const expiresIn: string | null = localStorage.getItem(AuthService.EXPIRES_IN_KEY)
    if ((accessToken == null) || (expiresIn == null)) return false
    const now: number = new Date().getTime()
    const expiresAt: number = parseInt(expiresIn)
    if (!(now < expiresAt)) {
      this.getNewRefreshedToken()
        .pipe(catchError((err: any) => {
          throw this.refreshTokenErrorHandler(err)
        }))
        .subscribe((accessTokenResponseBody: AccessTokenResponseBodyInterface) => {
          this.setSession(accessTokenResponseBody)
        })
    }
    return true
  }

  public getOauthState (): string {
    return localStorage.getItem(AuthService.OAUTH_STATE_KEY) ?? this.saveOauthState()
  }

  private saveOauthState (): string {
    const state: string = btoa(Math.random().toString().substring(2, 10))
    localStorage.setItem(AuthService.OAUTH_STATE_KEY, state)
    return state
  }

  redirectToDashboard (): void {
    void this.router.navigate(['/']).then()
  }

  redirectToLoginIn (): void {
    void this.router.navigate(['/login']).then()
  }
}
