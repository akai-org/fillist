import { TestBed } from '@angular/core/testing'
import { CanActivateFn } from '@angular/router'

import { loggedInActivate } from './logged-in-activate.guard'

describe('loginActivateGuard', () => {
  // eslint-disable-next-line
  const executeGuard: CanActivateFn = (...guardParameters) =>
    // eslint-disable-next-line
    TestBed.runInInjectionContext(() => loggedInActivate(...guardParameters))

  beforeEach(() => {
    TestBed.configureTestingModule({})
  })

  it('should be created', () => {
    expect(executeGuard).toBeTruthy()
  })
})
