import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PlaylistsViewComponent } from './playlists-view.component'
import { HttpClient } from '@angular/common/http'
import { UiModule } from '../../shared/ui/ui.module'
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
import { PlaylistsResponseBody } from './playlists-response-body.interface'

describe('PlaylistsViewComponent', () => {
  let component: PlaylistsViewComponent
  let fixture: ComponentFixture<PlaylistsViewComponent>
  let httpTestingController: HttpTestingController
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlaylistsViewComponent],
      providers: [HttpClient],
      imports: [UiModule, HttpClientTestingModule]
    })
    fixture = TestBed.createComponent(PlaylistsViewComponent)
    httpTestingController = TestBed.inject(HttpTestingController)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  afterEach(() => {
    httpTestingController.verify()
  })

  it('should create', () => {
    const mockResponse: PlaylistsResponseBody = {
      limit: 1,
      offset: 0,
      total: 1,
      playlists: [
        {
          name: 'name',
          id: 'id',
          image: 'image',
          ownerDisplayName: 'ownerDisplayName'
        }
      ]
    }
    httpTestingController.expectOne('/playlists').flush(mockResponse)
    expect(component).toBeTruthy()
    expect(component.playlistsResponse).toEqual(mockResponse)
  })
})
