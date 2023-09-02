import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PlaylistTileComponent } from './playlist-tile.component'

describe('PlaylistTileComponent', () => {
  let component: PlaylistTileComponent
  let fixture: ComponentFixture<PlaylistTileComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlaylistTileComponent]
    })
    fixture = TestBed.createComponent(PlaylistTileComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
