import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-list',
  template: `
    <h2>Employee List</h2>
    <ul *ngFor="let e of employees">
      <li> {{ e.name }} </li>
    </ul>
  `,
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  public employees = []
  constructor(private _employeeService: EmployeeService) { }

  ngOnInit(): void {
    // this.employees = this._employeeService.getEmployees();

    this._employeeService.getEmployees()
      .subscribe(data => this.employees = data);
  }

}
