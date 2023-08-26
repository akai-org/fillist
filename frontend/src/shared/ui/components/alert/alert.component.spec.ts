import { ComponentFixture, TestBed } from '@angular/core/testing'

import { AlertComponent } from './alert.component'
import { NgIconsModule } from '@ng-icons/core'
import { cssClose, cssDanger } from '@ng-icons/css.gg'

describe('ErrorAlertComponent', () => {
  let component: AlertComponent
  let fixture: ComponentFixture<AlertComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlertComponent],
      imports: [
        NgIconsModule.withIcons({ cssClose, cssDanger })
      ]
    })
    fixture = TestBed.createComponent(AlertComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
