import { ComponentFixture, TestBed } from '@angular/core/testing'
import { HeaderComponent } from './header.component'
import { UiModule } from '../../ui.module'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClient, HttpHandler } from '@angular/common/http'

describe('HeaderComponent', () => {
  let component: HeaderComponent
  let fixture: ComponentFixture<HeaderComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      imports: [UiModule, RouterTestingModule],
      providers: [HttpClient, HttpHandler]
    })
    fixture = TestBed.createComponent(HeaderComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
