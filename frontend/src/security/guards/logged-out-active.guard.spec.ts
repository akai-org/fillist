import { TestBed } from '@angular/core/testing'
import { CanActivateFn } from '@angular/router'

import { loggedOutActivate } from './logged-out-activate.guard'

describe('loggedOutActiveGuard', () => {
  const executeGuard: CanActivateFn = async (...guardParameters) =>
    await TestBed.runInInjectionContext(async () => await loggedOutActivate(...guardParameters))

  beforeEach(() => {
    TestBed.configureTestingModule({})
  })

  it('should be created', () => {
    expect(executeGuard).toBeTruthy()
  })
})
