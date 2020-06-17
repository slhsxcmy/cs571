import { Component, OnInit } from '@angular/core';
import { SearchService } from '../search.service';

@Component({
  selector: 'items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {
  data: Array<any>;
  totalRecords: Number;
  // page
  constructor(private searchService: SearchService) {
    this.data = new Array<any>();
   }

  ngOnInit(): void {
    // this.searchService.
  }

}
