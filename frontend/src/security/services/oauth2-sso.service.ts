import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { map, Observable } from 'rxjs'
import { AuthorizationCodeUrlResponseBodyInterface } from '../models/authorization-code-url-response-body.interface'
import { environment } from 'src/environments/environment'
import { AccessTokenResponseBodyInterface } from '../models/access-token-response-body.interface'
import { Router } from '@angular/router'

@Injectable({
  providedIn: 'root'
})
export class Oauth2SsoService {
  static readonly OAUTH_STATE_KEY: string = 'oauthState'
  static readonly REFRESH_TOKEN_KEY: string = 'refreshToken'
  static readonly ACCESS_TOKEN_KEY: string = 'accessToken'
  static readonly EXPIRES_IN_KEY: string = 'expiresIn'
  constructor (private http: HttpClient, private router: Router) {
  }

  getAuthorizationCodeUrl (): Observable<string> {
    return this.http.get<AuthorizationCodeUrlResponseBodyInterface>(`${environment.backendUrl}/oauth2/authorization`, {
      params: {
        state: this.getOauthState()
      }
    }).pipe(map((response: AuthorizationCodeUrlResponseBodyInterface) => response.url))
  }

  getAccessTokenAndSetSession (code: string, state: string): void {
    this.getAccessToken(code, state).subscribe((accessToken: AccessTokenResponseBodyInterface) => {
      this.setSession(accessToken)
    })
  }

  getAccessToken (code: string, state: string): Observable<AccessTokenResponseBodyInterface> {
    if (state !== this.getOauthState()) throw new Error('Invalid state')
    return this.http.post<AccessTokenResponseBodyInterface>(`${environment.backendUrl}/oauth2/token`, {
      code
    })
  }

  setSession (AccessTokenResponseBody: AccessTokenResponseBodyInterface): void {
    localStorage.setItem(Oauth2SsoService.ACCESS_TOKEN_KEY, AccessTokenResponseBody.accessToken)
    localStorage.setItem(Oauth2SsoService.REFRESH_TOKEN_KEY, AccessTokenResponseBody.refreshToken)
    const now: number = new Date().getTime()
    const expiresAt: number = now + (AccessTokenResponseBody.expiresIn * 1000)
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
    return now < expiresAt
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
