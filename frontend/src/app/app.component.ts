import { Component, OnInit, ViewContainerRef } from '@angular/core'
import { TopViewContainerRefService } from '../shared/ui/services/top-view-container-ref.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'fillist'

  constructor (viewContainerRef: ViewContainerRef) {
    TopViewContainerRefService.setViewContainerRef(viewContainerRef)
  }

  ngOnInit (): void {
  }
}
