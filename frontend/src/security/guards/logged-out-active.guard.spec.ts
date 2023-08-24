import { TestBed } from '@angular/core/testing'
import { CanActivateFn } from '@angular/router'

import { loggedOutActivate } from './logged-out-activate.guard'

describe('loggedOutActiveGuard', () => {
// eslint-disable-next-line
  const executeGuard: CanActivateFn = (...guardParameters) =>
    // eslint-disable-next-line
    TestBed.runInInjectionContext(() => loggedOutActivate(...guardParameters))

  beforeEach(() => {
    TestBed.configureTestingModule({})
  })

  it('should be created', () => {
    expect(executeGuard).toBeTruthy()
  })
})
