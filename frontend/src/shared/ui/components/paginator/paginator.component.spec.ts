import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PaginatorComponent } from './paginator.component'
import { NgIconsModule } from '@ng-icons/core'
import { cssArrowLeft, cssArrowRight } from '@ng-icons/css.gg'

describe('PaginatorComponent', () => {
  let component: PaginatorComponent
  let fixture: ComponentFixture<PaginatorComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaginatorComponent],
      imports: [NgIconsModule.withIcons({
        cssArrowLeft,
        cssArrowRight
      })]
    })
    fixture = TestBed.createComponent(PaginatorComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })

  it('should set indexes maxPages>pagesCount', () => {
    component.total = 33
    component.pageSize = 10
    component.ngOnInit()
    expect(component.pagesIndexes).toEqual([1, 2, 3, 4])
  })

  it('should set indexes first active', () => {
    component.total = 200
    component.pageSize = 10
    for (let i = 1; i <= 4; i++) {
      component.activePage = 1
      component.ngOnInit()
      expect(component.pagesIndexes).toEqual([1, 2, 3, 4, 5, 6, 7])
    }
  })

  it('should set indexes middle active', () => {
    component.total = 200
    component.pageSize = 10
    component.activePage = 5
    component.ngOnInit()
    expect(component.pagesIndexes).toEqual([2, 3, 4, 5, 6, 7, 8])
  })

  it('should set indexes last active', () => {
    component.total = 200
    component.pageSize = 10
    for (let i = 17; i <= 20; i++) {
      component.activePage = i
      component.ngOnInit()
      expect(component.pagesIndexes).toEqual([14, 15, 16, 17, 18, 19, 20])
    }
  })

  it('should not render paginator', () => {
    component.pagesCount = 1
    fixture.detectChanges()
    const compiled = fixture.nativeElement
    expect(compiled.querySelector('button')).toBeNull()
  })

  it('should change page', (done) => {
    component.total = 200
    component.pageSize = 10
    component.activePage = 5
    component.ngOnInit()
    component.onPageChange.subscribe((page) => {
      expect(page.offset).toEqual(50)
      expect(page.limit).toEqual(10)
      expect(component.activePage).toEqual(6)
      done()
    })
    component.changePage(6)
  })
})
