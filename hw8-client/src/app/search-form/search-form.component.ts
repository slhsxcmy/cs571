import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';


// function validate(control: FormControl){
//   return
// }

@Component({
  selector: 'search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent {
  form: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      keyword: [null, Validators.required],
      from: [null],
      to: [null],
      condition: [null],
      returns: [null],
      freeshipping: [null],
      expshipping: [null]
    });
  }

}
