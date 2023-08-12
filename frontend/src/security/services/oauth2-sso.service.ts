import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { map, Observable } from 'rxjs'
import { AuthorizationCodeUrlResponseBodyInterface } from '../models/authorization-code-url-response-body.interface'
import { environment } from 'src/environments/environment'

@Injectable({
  providedIn: 'root'
})
export class Oauth2SsoService {
  static readonly OAUTH_STATE_KEY: string = 'oauthState'
  constructor (private http: HttpClient) {
  }

  getAuthorizationCodeUrl (): Observable<string> {
    return this.http.get<AuthorizationCodeUrlResponseBodyInterface>(`${environment.backendUrl}/oauth2/authorization`, {
      params: {
        state: this.getOauthState()
      }
    }).pipe(map((response: AuthorizationCodeUrlResponseBodyInterface) => response.url))
  }

  public getOauthState (): string {
    return localStorage.getItem(Oauth2SsoService.OAUTH_STATE_KEY) ?? this.saveOauthState()
  }

  private saveOauthState (): string {
    const state: string = btoa(Math.random().toString().substring(2, 10))
    localStorage.setItem(Oauth2SsoService.OAUTH_STATE_KEY, state)
    return state
  }
}
