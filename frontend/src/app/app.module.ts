import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { PagesModule } from '../pages/pages.module'
import { AuthInterceptor } from '../security/interceptor/auth.interceptor'
import { HTTP_INTERCEPTORS } from '@angular/common/http'
import { NgOptimizedImage } from '@angular/common'
import { UiModule } from '../shared/ui/ui.module'

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    PagesModule,
    NgOptimizedImage,
    UiModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
