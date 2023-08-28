import { Component, OnInit, ViewContainerRef } from '@angular/core'
import { AlertService } from '../shared/ui/services/alert.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'fillist'

  constructor (viewContainerRef: ViewContainerRef) {
    AlertService.setViewContainerRef(viewContainerRef)
  }

  ngOnInit (): void {
  }
}
