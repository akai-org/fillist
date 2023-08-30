import { ComponentFixture, TestBed } from '@angular/core/testing'

import { LayoutComponent } from './layout.component'
import { UiModule } from '../../ui.module'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClient, HttpHandler } from '@angular/common/http'

describe('LayoutComponent', () => {
  let component: LayoutComponent
  let fixture: ComponentFixture<LayoutComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LayoutComponent],
      imports: [UiModule, RouterTestingModule],
      providers: [HttpClient, HttpHandler]
    })
    fixture = TestBed.createComponent(LayoutComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
