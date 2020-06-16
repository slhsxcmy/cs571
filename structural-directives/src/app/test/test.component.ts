import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-test',
  template: `
  <h2 *ngIf="displayName; else elseBlock">Name here</h2>
  <h2 *ngIf="displayName; then thenBlock; else elseBlock">Name here</h2>

  <ng-template #thenBlock>
    <h2>
      Then Block
    </h2>
  </ng-template>

  <ng-template #elseBlock>
    <h2>
      Name is hidden
    </h2>
  </ng-template>




  <div [ngSwitch]="color">
    <div *ngSwitchCase="'red'">You picked red</div>
    <div *ngSwitchCase="'green'">You picked green</div>
    <div *ngSwitchCase="'blue'">You picked blue</div>
    <div *ngSwitchDefault>You picked nothing</div>
  </div>

  <div *ngFor="let color of colors; index as i; first as f; last as l; odd as o">
    <h2>{{o}} {{f}} {{l}} {{i}} {{color}}</h2>
  </div>




  `,

  
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  public displayName = false;

  public color = "rexxxd";

  public colors = ["red", "blue", "green", "yellow"];

  constructor() { }

  ngOnInit(): void {
  }

}
