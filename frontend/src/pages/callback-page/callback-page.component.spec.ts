import { ComponentFixture, TestBed } from '@angular/core/testing'

import { CallbackPageComponent } from './callback-page.component'
import { ActivatedRoute } from '@angular/router'
import { HttpClient, HttpHandler } from '@angular/common/http'
import { UiModule } from '../../shared/ui/ui.module'
import { of } from 'rxjs'

describe('CallbackPageComponent', () => {
  let component: CallbackPageComponent
  let fixture: ComponentFixture<CallbackPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CallbackPageComponent],
      providers: [{ provide: ActivatedRoute, useValue: { queryParams: of({ code: 'code', state: 'state' }) } }, HttpClient, HttpHandler],
      imports: [UiModule]
    })
    fixture = TestBed.createComponent(CallbackPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
