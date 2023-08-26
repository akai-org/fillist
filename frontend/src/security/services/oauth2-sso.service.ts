import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { catchError, map, Observable } from 'rxjs'
import { AuthorizationCodeUrlResponseBodyInterface } from '../models/authorization-code-url-response-body.interface'
import { environment } from 'src/environments/environment'
import { AccessTokenResponseBodyInterface } from '../models/access-token-response-body.interface'
import { Router } from '@angular/router'
import { AlertService } from '../../shared/ui/services/alert.service'
import { AlertColor } from '../../shared/ui/components/alert/alertColor'

@Injectable({
  providedIn: 'root'
})
export class Oauth2SsoService {
  static readonly OAUTH_STATE_KEY: string = 'oauthState'
  static readonly REFRESH_TOKEN_KEY: string = 'refreshToken'
  static readonly ACCESS_TOKEN_KEY: string = 'accessToken'
  static readonly EXPIRES_IN_KEY: string = 'expiresIn'

  constructor (private http: HttpClient, private router: Router, private alertService: AlertService) {
  }

  getAuthorizationCodeUrl (): Observable<string> {
    return this.http.get<AuthorizationCodeUrlResponseBodyInterface>(`${environment.backendUrl}/oauth2/authorization`, {
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
    this.alertService.displayAlert('Something went wrong with authentication. Please try again later.', AlertColor.ERROR, () => {
      this.logout()
      this.redirectToLoginIn()
    })
    return new Error(err)
  }

  refreshTokenErrorHandler (err: any): Error {
    this.alertService.displayAlert('Session refresh error. Please try login in again.', AlertColor.WARNING, () => {
      this.logout()
      this.redirectToLoginIn()
    })
    return new Error(err)
  }

  getNewAccessToken (code: string, state: string): Observable<AccessTokenResponseBodyInterface> {
    if (state !== this.getOauthState()) {
      throw this.loginInErrorHandler('Invalid state')
    }
    return this.http.post<AccessTokenResponseBodyInterface>(`${environment.backendUrl}/oauth2/token`, {
      code,
      grantType: 'authorization_code',
      redirectUri: `${environment.frontendUrl}${environment.redirectPath}`
    })
  }

  getNewRefreshedToken (): Observable<AccessTokenResponseBodyInterface> {
    return this.http.post<AccessTokenResponseBodyInterface>(`${environment.backendUrl}/oauth2/refresh`, {
      grantType: 'refresh_token',
      refreshToken: localStorage.getItem(Oauth2SsoService.REFRESH_TOKEN_KEY)
    })
  }

  setSession (accessTokenResponseBody: AccessTokenResponseBodyInterface): void {
    localStorage.setItem(Oauth2SsoService.ACCESS_TOKEN_KEY, accessTokenResponseBody.accessToken)
    if (accessTokenResponseBody.refreshToken != null) {
      localStorage.setItem(Oauth2SsoService.REFRESH_TOKEN_KEY, accessTokenResponseBody.refreshToken)
    }
    const now: number = new Date().getTime()
    const expiresAt: number = now + (accessTokenResponseBody.expiresIn * 1000)
    localStorage.setItem(Oauth2SsoService.EXPIRES_IN_KEY, expiresAt.toString())
  }

  logout (): void {
    localStorage.removeItem(Oauth2SsoService.ACCESS_TOKEN_KEY)
    localStorage.removeItem(Oauth2SsoService.REFRESH_TOKEN_KEY)
    localStorage.removeItem(Oauth2SsoService.EXPIRES_IN_KEY)
  }

  isLoggedIn (): boolean {
    const accessToken: string | null = localStorage.getItem(Oauth2SsoService.ACCESS_TOKEN_KEY)
    const expiresIn: string | null = localStorage.getItem(Oauth2SsoService.EXPIRES_IN_KEY)
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
    return localStorage.getItem(Oauth2SsoService.OAUTH_STATE_KEY) ?? this.saveOauthState()
  }

  private saveOauthState (): string {
    const state: string = btoa(Math.random().toString().substring(2, 10))
    localStorage.setItem(Oauth2SsoService.OAUTH_STATE_KEY, state)
    return state
  }

  redirectToDashboard (): void {
    void this.router.navigate(['/']).then()
  }

  redirectToLoginIn (): void {
    void this.router.navigate(['/login']).then()
  }
}
