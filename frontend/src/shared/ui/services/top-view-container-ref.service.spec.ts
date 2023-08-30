import { TestBed } from '@angular/core/testing'

import { TopViewContainerRefService } from './top-view-container-ref.service'

describe('TopViewContainerRefService', () => {
  let service: TopViewContainerRefService

  beforeEach(() => {
    TestBed.configureTestingModule({})
    service = TestBed.inject(TopViewContainerRefService)
  })

  it('should be created', () => {
    expect(service).toBeTruthy()
  })
})
