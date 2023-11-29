import { Injectable } from '@angular/core'
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http'
import { catchError, Observable } from 'rxjs'
import { AuthService } from '../services/auth.service'
import { environment } from '../../environments/environment'
import { TopViewContainerRefService } from '../../shared/ui/services/top-view-container-ref.service'
import { AlertInitializer } from '../../shared/ui/components/alert/alert.initializer'
import { AlertColor } from '../../shared/ui/components/alert/alert-color'

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor (private topViewContainerRefService: TopViewContainerRefService) {
  }

  intercept (request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const accessToken: string | null = localStorage.getItem(AuthService.ACCESS_TOKEN_KEY)
    let cloned = request.clone({
      url: `${environment.backendUrl}${request.url}`
    })
    if (accessToken != null) {
      cloned = cloned.clone({
        headers: request.headers.set('Authorization',
          'Bearer ' + accessToken)
      })
      return next.handle(cloned).pipe(catchError((err) => {
        this.topViewContainerRefService.displayDialog(
          new AlertInitializer('Something went wrong', AlertColor.ERROR)
        )
        throw err
      }))
    }
    return next.handle(cloned)
  }
}
