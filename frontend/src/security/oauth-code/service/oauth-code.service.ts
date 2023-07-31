import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { environment } from 'src/environments/environment'
import { type OauthProperties } from '../model/oauth-properties.interface'
import { map, Observable } from 'rxjs'
import { OauthCodeParams } from '../model/oauth-code-params.interface'

@Injectable({
  providedIn: 'root'
})
export class OAuthCodeService {
  constructor (private http: HttpClient) {
  }

  public getAuthorizationCodeUrl (): Observable<string> {
    return this.getOauthProperties().pipe(map((oauthProperties: OauthProperties) => {
      const oauthCodeParams: OauthCodeParams = {
        client_id: oauthProperties.clientId,
        response_type: 'code',
        redirect_uri: `${environment.frontendUrl}/callback`,
        scope: oauthProperties.scopes.join(','),
        state: '34fFs29kd09'
      }
      const spotifyUrl: string = 'https://accounts.spotify.com/authorize'
      const params: string = `?client_id=${oauthCodeParams.client_id}&response_type=${oauthCodeParams.response_type}&redirect_uri=${oauthCodeParams.redirect_uri}&scope=${oauthCodeParams.scope}&state=${oauthCodeParams.state}`
      return `${spotifyUrl}${params}`
    }))
  }

  private getOauthProperties (): Observable<OauthProperties> {
    return this.http.get<OauthProperties>(`${environment.backendUrl}/oauth/properties`)
  }
}
