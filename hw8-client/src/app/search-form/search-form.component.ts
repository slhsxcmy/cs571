import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';


// function validate(control: FormControl){
//   return
// }

@Component({
  selector: 'search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent {
  log(x) { console.log(x); }
  onSubmit(data) {
    console.log(data);
  }
}
