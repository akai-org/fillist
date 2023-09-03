import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { PageProperties } from './page-properties.interface'

@Component({
  selector: 'fillist-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss']
})
export class PaginatorComponent implements OnInit {
  @Input() total: number = 0
  @Input() pageSize: number = 0
  @Output() onPageChange = new EventEmitter<PageProperties>()
  pagesIndexes: number[] = []
  activePage: number = 1
  pagesCount: number = 0
  readonly maxPages: number = 7

  ngOnInit (): void {
    this.setPagesIndexes()
  }

  changePage (pageNumber: number): void {
    this.activePage = pageNumber
    this.setPagesIndexes()
    this.onPageChange.emit({
      offset: (pageNumber - 1) * this.pageSize,
      limit: this.pageSize
    })
  }

  changeToNextPage (): void {
    if (this.activePage !== this.pagesCount) this.changePage(this.activePage + 1)
  }

  changeToPreviousPage (): void {
    if (this.activePage !== 1) this.changePage(this.activePage - 1)
  }

  setPagesIndexes (): void {
    this.pagesIndexes = []
    this.pagesCount = Math.ceil(this.total / this.pageSize)
    let firstPageNumber = 1
    let lastPageNumber = this.pagesCount
    if (this.pagesCount > this.maxPages) {
      firstPageNumber = this.activePage - Math.floor(this.maxPages / 2)
      lastPageNumber = this.activePage + Math.floor(this.maxPages / 2)
      if (firstPageNumber < 1) {
        firstPageNumber = 1
        lastPageNumber = this.maxPages
      }
      if (lastPageNumber > this.pagesCount) {
        lastPageNumber = this.pagesCount
        firstPageNumber = this.pagesCount - this.maxPages + 1
      }
    }
    for (let i = firstPageNumber; i <= lastPageNumber; i++) this.pagesIndexes.push(i)
  }
}
