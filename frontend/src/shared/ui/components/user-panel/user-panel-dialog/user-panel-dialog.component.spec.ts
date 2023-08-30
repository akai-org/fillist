import { ComponentFixture, TestBed } from '@angular/core/testing'

import { UserPanelDialogComponent } from './user-panel-dialog.component'

describe('UserPanelDialogComponent', () => {
  let component: UserPanelDialogComponent
  let fixture: ComponentFixture<UserPanelDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserPanelDialogComponent]
    })
    fixture = TestBed.createComponent(UserPanelDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
