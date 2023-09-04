import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PlaylistTileComponent } from './playlist-tile.component'
import { NgOptimizedImage } from '@angular/common'

describe('PlaylistTileComponent', () => {
  let component: PlaylistTileComponent
  let fixture: ComponentFixture<PlaylistTileComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlaylistTileComponent],
      imports: [NgOptimizedImage]
    })
    fixture = TestBed.createComponent(PlaylistTileComponent)
    component = fixture.componentInstance
    component.name = 'name'
    component.image = 'image'
    component.owner = 'owner'
    component.id = 'id'
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })

  it('should have the correct values', () => {
    const compiled = fixture.nativeElement
    expect(compiled.querySelector('img').src).toContain('image')
    expect(compiled.querySelector('h6').textContent).toContain('name')
    expect(compiled.querySelector('small').textContent).toContain('owner')
  })
})
