import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";

// function validate(control: FormControl){
//   return
// }

@Component({
  selector: 'search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent {
  range_invalid_flag = false;

  registered = false;
  submitted = false;

  constructor(private fb: FormBuilder, private http: HttpClient) { }
  mainForm = this.fb.group({
    keyword: ['',
      {
        validators: Validators.required, updateOn: "submit"
      }],
    range: this.fb.group({
      from: [''],
      to: ['']
    }, {
      validators: rangeValidator, updateOn: "submit"
    }),
    condition: this.fb.group({
      new: [''],
      used: [''],
      vgood: [''],
      good: [''],
      acceptable: ['']

    }),
    returns: [''],
    freeshipping: [''],
    expshipping: [''],
    sort: ['']
  });


  invalidKeyword() {
    return (this.submitted && this.mainForm.controls.keyword.errors != null);
  }

  invalidRange() {
    return (this.submitted && this.mainForm.controls.range.errors != null);
  }

  onSubmit() {
    this.submitted = true;


    // validate here?
    // this.mainForm.controls['range'].value.setErrors({'incorrect': true});



    if (this.mainForm.invalid == true) {
      return;
    } else {
      // let data: any = Object.assign({ guid: this.guid }, this.userForm.value);

      // construct URL


      // this.http.get('/api/v1/customer', data).subscribe((data: any) => {

      //   let path = '/user/' + data.customer.uid;

      //   this.router.navigate([path]);
      // }, error => {
      //   this.serviceErrors = error.error.error;
      // });

      this.registered = true;
      console.log("Form submitted!");


      // console.warn(this.mainForm.value);

      // if (this.mainForm.get(['range', 'from']).value > this.mainForm.get(['range', 'to']).value) {
      //   // console.log('Incorrect');
      //   this.range_invalid_flag = true;
      // }
      // console.warn(this.mainForm.controls['keyword'].value);
      // console.warn(this.mainForm.get(['range','from']).value);
      // console.warn(this.mainForm.get('range').get('to').value);

    }


  }
}

/** A hero's name can't match the hero's alter ego */
export const rangeValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const from = control.get('from');
  const to = control.get('to');
  // console.log(from);
  console.log(to.value == '');

  return (from.value != null && from.value < 0)
    || (to.value != null && to.value < 0)
    || (from.value != null && to.value != null && from.value > 0 && to.value > 0 && from.value > to.value)
    ? { 'rangeError': true } : null;
};