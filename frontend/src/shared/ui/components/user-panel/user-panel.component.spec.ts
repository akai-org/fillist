import { ComponentFixture, TestBed } from '@angular/core/testing'

import { UserPanelComponent } from './user-panel.component'
import { HttpClient, HttpHandler } from '@angular/common/http'

describe('UserPanelComponent', () => {
  let component: UserPanelComponent
  let fixture: ComponentFixture<UserPanelComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserPanelComponent],
      providers: [HttpClient, HttpHandler]
    })
    fixture = TestBed.createComponent(UserPanelComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
