import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PlaylistsViewComponent } from './playlists-view.component'
import { HttpClient, HttpHandler } from '@angular/common/http'
import { UiModule } from '../../shared/ui/ui.module'

describe('PlaylistsViewComponent', () => {
  let component: PlaylistsViewComponent
  let fixture: ComponentFixture<PlaylistsViewComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlaylistsViewComponent],
      providers: [HttpClient, HttpHandler],
      imports: [UiModule]
    })
    fixture = TestBed.createComponent(PlaylistsViewComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
