import { TestBed } from '@angular/core/testing'

import { Oauth2SsoService } from './oauth2-sso.service'
import { HttpClient, HttpHandler } from '@angular/common/http'
import { AccessTokenResponseBodyInterface } from '../models/access-token-response-body.interface'
import { Observable } from 'rxjs'

describe('Oauth2SsoService', () => {
  let service: Oauth2SsoService

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpClient, HttpHandler]
    })
    service = TestBed.inject(Oauth2SsoService)
  })

  it('should be created', () => {
    expect(service).toBeTruthy()
  })

  it('should have a getAuthorizationCodeUrl method', () => {
    expect(service.getAuthorizationCodeUrl).toBeTruthy()
  })

  it('should have a getAccessTokenAndSetSession method', () => {
    expect(service.getNewAccessTokenAndSetSession).toBeTruthy()
  })

  it('should have a getAccessToken method', () => {
    expect(service.getNewAccessToken).toBeTruthy()
  })

  it('should have a getRefreshToken method', () => {
    expect(service.getNewRefreshedToken).toBeTruthy()
  })

  it('should setSession works', () => {
    service.setSession({
      accessToken: 'accessToken',
      expiresIn: 200,
      refreshToken: 'refreshToken',
      tokenType: 'test'
    })
    expect(localStorage.getItem(Oauth2SsoService.ACCESS_TOKEN_KEY)).toBe('accessToken')
    const now: number = new Date().getTime()
    expect(parseInt(localStorage.getItem(Oauth2SsoService.EXPIRES_IN_KEY) ?? '1')).toBe(now + 200000)
    expect(localStorage.getItem(Oauth2SsoService.REFRESH_TOKEN_KEY)).toBe('refreshToken')
    service.setSession({
      accessToken: 'accessToken2',
      expiresIn: 200,
      refreshToken: null,
      tokenType: 'test'
    })
    expect(localStorage.getItem(Oauth2SsoService.ACCESS_TOKEN_KEY)).toBe('accessToken2')
    expect(localStorage.getItem(Oauth2SsoService.REFRESH_TOKEN_KEY)).toBe('refreshToken')
  })

  it('should logout works', () => {
    service.setSession({
      accessToken: 'accessToken',
      expiresIn: 200,
      refreshToken: 'refreshToken',
      tokenType: 'test'
    })
    service.logout()
    expect(localStorage.getItem(Oauth2SsoService.ACCESS_TOKEN_KEY)).toBeNull()
    expect(localStorage.getItem(Oauth2SsoService.REFRESH_TOKEN_KEY)).toBeNull()
    expect(localStorage.getItem(Oauth2SsoService.EXPIRES_IN_KEY)).toBeNull()
  })

  it('should isLoggedIn works', () => {
    service.logout()
    service.getNewRefreshedToken = (): Observable<AccessTokenResponseBodyInterface> => {
      throw new Error('test')
    }
    service.refreshTokenErrorHandler = (err: any) => {
      return err
    }
    expect(service.isLoggedIn()).toBeFalse()
    service.setSession({
      accessToken: 'accessToken',
      expiresIn: 5,
      refreshToken: 'refreshToken',
      tokenType: 'test'
    })
    expect(service.isLoggedIn()).toBeTrue()
  })
})
