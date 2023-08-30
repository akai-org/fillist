import { ComponentFixture, TestBed } from '@angular/core/testing'

import { UserPanelDialogComponent } from './user-panel-dialog.component'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClient, HttpHandler } from '@angular/common/http'
import { NgIconsModule } from '@ng-icons/core'
import { cssClose } from '@ng-icons/css.gg'
import { NgOptimizedImage } from '@angular/common'

describe('UserPanelDialogComponent', () => {
  let component: UserPanelDialogComponent
  let fixture: ComponentFixture<UserPanelDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserPanelDialogComponent],
      imports: [
        RouterTestingModule,
        NgIconsModule.withIcons({
          cssClose
        }),
        NgOptimizedImage
      ],
      providers: [HttpClient, HttpHandler]
    })
    fixture = TestBed.createComponent(UserPanelDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
