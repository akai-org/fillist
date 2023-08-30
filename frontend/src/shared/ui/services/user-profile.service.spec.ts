import { TestBed } from '@angular/core/testing'
import { UserProfileService } from './user-profile.service'
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
import { UserProfileInterface } from '../models/user-profile.interface'
import { environment } from '../../../environments/environment.development'
import { HttpClient } from '@angular/common/http'

describe('UserProfileService', () => {
  let service: UserProfileService
  let httpTestingController: HttpTestingController

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HttpClient]
    })
    service = TestBed.inject(UserProfileService)
    httpTestingController = TestBed.inject(HttpTestingController)
  })

  afterEach(() => {
    httpTestingController.verify()
  })

  it('should be created', () => {
    httpTestingController.expectOne(`${environment.backendUrl}/me`)
    expect(service).toBeTruthy()
  })

  it('Profile state works', () => {
    const mockResponse: UserProfileInterface = {
      displayName: 'name',
      email: 'email',
      largeImageUrl: 'imagel',
      smallImageUrl: 'images'
    }
    const req = httpTestingController.expectOne(`${environment.backendUrl}/me`)
    req.flush(mockResponse)
    service.userProfile.subscribe((response: UserProfileInterface) => {
      expect(response).toEqual(mockResponse)
    })
  })
})
