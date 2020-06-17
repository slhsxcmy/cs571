import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { SearchService } from './search.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'hw8-client';

  registered = false;
  submitted = false;

  searchResult: Array<any>;
  totalResults: number;
  returnedResults: number;

  currPage = 1;

  no_result_flag = false;
  got_search_result_flag = false;
  kw = "";



  constructor(private fb: FormBuilder, private _searchService: SearchService) {
    this.searchResult = new Array<any>();
  }


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
    sort: ['BestMatch']
  });


  invalidKeyword() {
    return (this.submitted && this.mainForm.controls.keyword.errors != null);
  }

  invalidRange() {
    return (this.submitted && this.mainForm.controls.range.errors != null);
  }

  reset_form() {
    // console.log("start reset_form");

    // this.mainForm.reset();
    this.mainForm.patchValue({
      keyword: '',
      range: {
        from: '',
        to: '',
      },
      condition: {
        new: '',
        used: '',
        vgood: '',
        good: '',
        acceptable: ''

      },
      returns: '',
      freeshipping: '',
      expshipping: '',
      sort: 'BestMatch'
    });
  }

  onSubmit() {
    this.submitted = true;


    console.log(this.mainForm.value);


    if (this.mainForm.invalid == true) {
      return;
    } else {

      this.kw = this.mainForm.get('keyword').value;

      // this.registered = true;
      // console.log("Form submitted!");


      this._searchService.search(this.mainForm.value)
        .subscribe((data) => {
          console.log(data);

          this.got_search_result_flag = true;

          this.searchResult = data.searchResult;
          this.totalResults = data.totalResults;
          this.returnedResults = data.returnedResults;

          // console.log( this.searchResult);
          // console.log( this.totalResults);
          // console.log( this.returnedResults);

          if (this.returnedResults == 0) {
            this.no_result_flag = true;
          }



        }
          // response => console.log("Success", response),
          // error => console.error("Error", error)

        );


    }


  }
}

/** A hero's name can't match the hero's alter ego */
export const rangeValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const from = control.get('from');
  const to = control.get('to');
  // console.log(from);
  // console.log(to.value == '');

  return (from.value != null && from.value < 0)
    || (to.value != null && to.value < 0)
    || (from.value != null && to.value != null && from.value > 0 && to.value > 0 && from.value > to.value)
    ? { 'rangeError': true } : null;
};