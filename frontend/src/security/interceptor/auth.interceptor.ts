import { Injectable } from '@angular/core'
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http'
import { Observable } from 'rxjs'
import { Oauth2SsoService } from '../services/oauth2-sso.service'

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept (request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const accessToken: string | null = localStorage.getItem(Oauth2SsoService.ACCESS_TOKEN_KEY)
    if (accessToken != null) {
      const cloned = request.clone({
        headers: request.headers.set('Authorization',
          'Bearer ' + accessToken)
      })
      return next.handle(cloned)
    } else {
      return next.handle(request)
    }
  }
}
