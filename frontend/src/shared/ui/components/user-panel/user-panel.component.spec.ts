import { ComponentFixture, TestBed } from '@angular/core/testing'

import { UserPanelComponent } from './user-panel.component'

describe('UserPanelComponent', () => {
  let component: UserPanelComponent
  let fixture: ComponentFixture<UserPanelComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserPanelComponent]
    })
    fixture = TestBed.createComponent(UserPanelComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
