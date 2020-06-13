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
  registered = false;
  submitted = false;
  userForm: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  invalidKeyword() {
    return (this.submitted && this.userForm.controls.keyword.errors != null);
  }

  invalidRange() {
    if(!this.submitted) return false;

    return this.userForm.controls.from.errors != null;
  }

  ngOnInit() {
    this.userForm = this.formBuilder.group({
      keyword: ['', Validators.required],
      from: ['', ],

    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.userForm.invalid == true) {
      return;
    }
    else {
      this.registered = true;
    }
  }
}
