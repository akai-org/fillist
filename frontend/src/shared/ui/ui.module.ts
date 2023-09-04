import { NgModule } from '@angular/core'
import { CommonModule, NgOptimizedImage } from '@angular/common'
import { SpinnerComponent } from './components/spinner/spinner.component'
import { AlertComponent } from './components/alert/alert.component'
import { NgIconsModule } from '@ng-icons/core'
import { cssArrowLeft, cssArrowRight, cssClose, cssDanger } from '@ng-icons/css.gg'
import { HeaderComponent } from './components/header/header.component'
import { RouterLink, RouterOutlet } from '@angular/router'
import { LayoutComponent } from './components/layout/layout.component'
import { UserPanelComponent } from './components/user-panel/user-panel.component'
import { UserPanelDialogComponent } from './components/user-panel/user-panel-dialog/user-panel-dialog.component'
import { PaginatorComponent } from './components/paginator/paginator.component'

@NgModule({
  declarations: [
    SpinnerComponent,
    AlertComponent,
    HeaderComponent,
    LayoutComponent,
    UserPanelComponent,
    UserPanelDialogComponent,
    PaginatorComponent
  ],
  exports: [
    SpinnerComponent,
    AlertComponent,
    HeaderComponent,
    PaginatorComponent
  ],
  imports: [
    CommonModule,
    NgOptimizedImage,
    NgIconsModule.withIcons({
      cssClose,
      cssDanger,
      cssArrowLeft,
      cssArrowRight
    }),
    RouterLink,
    RouterOutlet
  ]
})
export class UiModule {
}
