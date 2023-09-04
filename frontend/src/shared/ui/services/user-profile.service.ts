import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { UserProfileInterface } from '../models/user-profile.interface'
import { BehaviorSubject, Observable } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {
  private _userProfile = new BehaviorSubject<UserProfileInterface>({
    displayName: '',
    email: '',
    largeImageUrl: '',
    smallImageUrl: ''
  })

  get userProfile (): Observable<UserProfileInterface> {
    return this._userProfile.asObservable()
  }

  constructor (httpClient: HttpClient) {
    httpClient.get<UserProfileInterface>('/me')
      .subscribe((response: UserProfileInterface) => {
        this._userProfile.next(response)
      })
  }
}
