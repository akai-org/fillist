import { ComponentFixture, TestBed } from '@angular/core/testing'
import { InputComponent } from './input.component'
import { FormsModule } from '@angular/forms'

describe('InputComponent', () => {
  let component: InputComponent
  let fixture: ComponentFixture<InputComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InputComponent],
      imports: [FormsModule]
    })

    fixture = TestBed.createComponent(InputComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create the component', () => {
    expect(component).toBeTruthy()
  })

  it('should emit value when input changes', () => {
    spyOn(component.valueChange, 'emit')
    const inputElement: HTMLInputElement = fixture.nativeElement.querySelector('input')
    inputElement.value = 'Test Input'
    inputElement.dispatchEvent(new Event('input'))

    expect(component.value).toEqual('Test Input')
    expect(component.valueChange.emit).toHaveBeenCalledWith('Test Input')
  })

  it('should emit value when textarea changes', () => {
    spyOn(component.valueChange, 'emit')
    component.type = 'textarea'
    fixture.detectChanges()

    const textareaElement: HTMLTextAreaElement = fixture.nativeElement.querySelector('textarea')
    textareaElement.value = 'Test Textarea'
    textareaElement.dispatchEvent(new Event('input'))

    expect(component.value).toEqual('Test Textarea')
    expect(component.valueChange.emit).toHaveBeenCalledWith('Test Textarea')
  })
})
