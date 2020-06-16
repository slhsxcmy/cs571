import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-detail',
  template: `
  <h2>Employee List</h2>
  <ul *ngFor="let e of employees">
    <li> {{ e.id }}. {{ e.name }} - {{ e.age }} </li>
  </ul>
  `,
  styleUrls: ['./employee-detail.component.css']
})
export class EmployeeDetailComponent implements OnInit {

  public employees = []
  constructor(private _employeeService: EmployeeService) { }

  ngOnInit(): void {
    // this.employees = this._employeeService.getEmployees();

    this._employeeService.getEmployees()
      .subscribe(data => this.employees = data);
  }

}
