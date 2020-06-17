import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-test',
  template: `
  <h2>{{"Hello " + myname}}</h2>
   <button (click)="fireEvent()">Send Event</button>

   <h2>{{name}}</h2>
   <h2>{{name | lowercase}}</h2>
   <h2>{{name | uppercase}}</h2>
   <h2>{{message | titlecase}}</h2>
   <h2>{{name | slice:3:5}}</h2>
   <h2>{{person | json}}</h2>

   <h2>{{5.678 | number:'1.2-3'}}</h2>
   <h2>{{5.678 | number:'3.4-5'}}</h2>
   <h2>{{5.678 | number:'3.1-2'}}</h2>
   <h2>{{0.25 | percent}}</h2>
   <h2>{{0.25 | currency}}</h2>
   <h2>{{0.25 | currency: 'GBP': 'code'}}</h2>

   <h2>{{date}}</h2>
   <h2>{{date | date:'short' }}</h2>
   <h2>{{date | date:'shortDate' }}</h2>
   <h2>{{date | date:'shortTime' }}</h2>
  `,
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  @Input('parentData') public myname;

  @Output() public childEvent = new EventEmitter();


  public name = "Codevolution";
  public message = "aa ee ii oo uu";
  public person = {
    "firstName": "John",
    "lastName": "Doe"
  }

  public date = new Date()
  constructor() { }

  ngOnInit(): void {
  }

  fireEvent() {
    this.childEvent.emit("Hey Parent");
  }

}