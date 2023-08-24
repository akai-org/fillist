import { TestBed } from '@angular/core/testing'

import { Oauth2SsoService } from './oauth2-sso.service'
import { HttpClient, HttpHandler } from '@angular/common/http'

describe('Oauth2SsoService', () => {
  let service: Oauth2SsoService

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpClient, HttpHandler]
    })
    service = TestBed.inject(Oauth2SsoService)
  })

  it('should be created', () => {
    expect(service).toBeTruthy()
  })
})
