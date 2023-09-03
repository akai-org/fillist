import { TestBed } from '@angular/core/testing'

import { AuthInterceptor } from './auth.interceptor'
import { HttpEvent, HttpRequest, HttpResponse } from '@angular/common/http'
import { last, Observable, of } from 'rxjs'
import { environment } from '../../environments/environment'
import { AuthService } from '../services/auth.service'

describe('AuthInterceptor', () => {
  let interceptor: AuthInterceptor
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthInterceptor
      ]
    })
    interceptor = TestBed.inject(AuthInterceptor)
  })

  it('should be created', () => {
    expect(interceptor).toBeTruthy()
  })

  const next: any = {
    handle: (req: HttpRequest<any>): Observable<HttpEvent<any>> => of(new HttpResponse(req))
  }

  it('should add backendUrl', (done) => {
    const request = new HttpRequest('GET', '/test')
    interceptor.intercept(request, next).pipe(last()).subscribe((result: any) => {
      expect(result.url).toEqual(`${environment.backendUrl}${request.url}`)
      done()
    })
  })

  it('should add Authorization header', (done) => {
    const request = new HttpRequest('GET', '/test')
    localStorage.setItem(AuthService.ACCESS_TOKEN_KEY, 'accessToken')
    interceptor.intercept(request, next).pipe(last()).subscribe((result: any) => {
      expect(result.headers.lazyUpdate.filter((object: any) => {
        return object.name === 'Authorization'
      })[0].value).toEqual('Bearer accessToken')
      done()
    })
  })
})
