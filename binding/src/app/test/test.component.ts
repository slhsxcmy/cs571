import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-test',
  template: `
  <h2> Welcome {{name}} </h2>
  <input [id]="myId" type="text" value="Vishwas">
  <input [disabled]="isDisabled" type="text" value="Vishwas">

  

  <h2 [class]="successClass">Codevo</h2>
  <h2 class="text-special" [class]="successClass">Codevo</h2>
  <h2 class="text-special" [class]="successClass">Codevo</h2>
  <h2 [class.text-danger]="hasError">Coo</h2>

  <h2 [ngClass]="messageClasses">multiple classes</h2>

  <h2 [style.color]="hasError ? 'red' : 'green'">Style binding</h2>
  <h2 [style.color]="highlightColor">Highlight Color</h2>
  <h2 [ngStyle]="titleStyles">multiple styles</h2>


  <button (click)="onClick($event)">Greet</button>
  <button (click)="greeting='Greet inline'">Greet inline</button>
  {{greeting}}

  <input #myInput type="text">
  <button (click)="logMessage(myInput)">Log</button>

  <br>
  <input [(ngModel)]="name" type="text">
  {{name}}


  `,
  styles: [`
    .text-success {
      color: green;
    }
    .text-danger {
      color: red;
    }
    .text-special {
      font-style: italic;
    }
  `]
})
export class TestComponent implements OnInit {
  // public name = "Codevolution";
  public myId = "testId";
  public isDisabled = true;

  public successClass = "text-success"
  public hasError = true;
  public isSpecial = true;
  public messageClasses = {
    "text-success": !this.hasError,
    "text-danger": this.hasError,
    "text-special": this.isSpecial
  }

  public highlightColor = "orange";
  public titleStyles = {
    color: "blue",
    fontStyle: "italic"

  }

  public greeting = "";

  public name = "";

  constructor() { }

  ngOnInit(): void {
  }
  

  onClick(event){
    console.log(event);

    this.greeting = event.type;
  }

  logMessage(value){
    console.log(value); 
  }
  
}
