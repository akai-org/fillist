import { TestBed } from '@angular/core/testing'

import { OAuthCodeService } from './oauth-code.service'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('OAuthCodeService', () => {
  let service: OAuthCodeService

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OAuthCodeService]
    })
    service = TestBed.inject(OAuthCodeService)
  })

  it('should be created', () => {
    expect(service).toBeTruthy()
  })

  it('should get oauth authorization code url', () => {
    service.getAuthorizationCodeUrl().subscribe((url: string) => {
      const requiredParams: string[] = ['client_id', 'response_type', 'redirect_uri', 'scope', 'state']
      requiredParams.forEach((param: string) => {
        expect(url).toContain(param)
      })
    })
  })
})
