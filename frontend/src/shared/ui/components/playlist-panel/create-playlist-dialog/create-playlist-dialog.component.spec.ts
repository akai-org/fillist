import { ComponentFixture, TestBed } from '@angular/core/testing'
import { CreatePlaylistDialogComponent } from './create-playlist-dialog.component'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { InputComponent } from '../../input/input.component'
import { NgIconsModule } from '@ng-icons/core'
import { cssClose } from '@ng-icons/css.gg'

describe('CreatePlaylistDialogComponent', () => {
  let component: CreatePlaylistDialogComponent
  let fixture: ComponentFixture<CreatePlaylistDialogComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreatePlaylistDialogComponent, InputComponent],
      imports: [HttpClientTestingModule,
        NgIconsModule.withIcons({
          cssClose
        })]
    })
    fixture = TestBed.createComponent(CreatePlaylistDialogComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
  it('should post playlist', () => {
    spyOn(component.httpClient, 'post').and.callThrough()

    component.playlistName = 'Test Playlist'
    component.playlistDescription = 'Test Description'
    component.postPlaylist()

    expect(component.httpClient.post).toHaveBeenCalledWith('/playlists', {
      name: 'Test Playlist',
      description: 'Test Description',
      public: true
    })
  })

  it('should change description value', () => {
    const newValue = 'New Description'
    component.changeDescriptionValue(newValue)
    expect(component.playlistDescription).toEqual(newValue)
  })

  it('should change name value', () => {
    const newValue = 'New Name'
    component.changeNameValue(newValue)
    expect(component.playlistName).toEqual(newValue)
  })

  it('should emit close event', () => {
    spyOn(component.onClose, 'emit')
    component.close()
    expect(component.onClose.emit).toHaveBeenCalled()
  })
})
