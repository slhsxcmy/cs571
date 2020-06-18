import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { SearchService } from './search.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  DEBUG = true;

  title = 'hw8-client';

  registered = false;
  submitted = false;

  searchResult: Array<any>;
  totalResults: number;
  returnedResults: number;

  currPage = 1;

  // no_result_flag = false;
  got_search_result_flag = false; // true for debug
  kw = "";

  // show_flags = []

  constructor(private fb: FormBuilder, private _searchService: SearchService) {
    this.searchResult = new Array<any>();
    this.searchResult[0] = {
      "title": "8-MOVIE HARRY POTTER 4K ULTRA HD 8-DISC COLLECTION COMPLETE ✔☆MINT☆✔ NO DIGITAL",
      "galleryURL": "https://thumbs2.ebaystatic.com/m/mzHoDpvFQ3IaKgJT9BrNssg/140.jpg",
      "price": "88.81",
      "location": "Anaheim,CA,USA",
      "category": "DVDs & Blu-ray DiscsDVDs & Blu-ray DiscsDVDs & Blu-ray Discs",
      "condition": "Like New Like New Like New Like New",
      "shippingType": "Free",
      "shippingServiceCost": "0.0",
      "shipToLocations": "Worldwide",
      "expeditedShipping": "false",
      "oneDayShippingAvailable": "false",
      "bestOfferEnabled": "true",
      "buyItNowAvailable": "false",
      "listingType": "FixedPrice",
      "gift": "false",
      "watchCount": "8",
      "viewItemURL": "https://www.ebay.com/itm/8-MOVIE-HARRY-POTTER-4K-ULTRA-HD-8-DISC-COLLECTION-COMPLETE-MINT-NO-DIGITAL-/353100781233",
      // "testicon": '<span class="material-icons">clear</span>',
    };

    this.searchResult[0].showing_detail = false;  // flag for show hide button
    // console.log(this.searchResult[0].galleryURL);

    if (this.DEBUG) {
      this.searchResult[0].showing_detail = true;
      this.got_search_result_flag = true;
    }

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

  show(index) {
    console.log('showing ' + index);

  }

  hide(index) {
    console.log('hiding ' + index);
  }

  invalidKeyword() {
    return (this.submitted && this.mainForm.controls.keyword.errors != null);
  }

  invalidRange() {
    return (this.submitted && this.mainForm.controls.range.errors != null);
  }

  reset_form() {
    // console.log("start reset_form");
    this.submitted = false;
    this.got_search_result_flag = false;
    this.currPage = 1;
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
    this.got_search_result_flag = false;
    this.currPage = 1;


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

          // console.log(this.searchResult);
          // console.log( this.totalResults);
          // console.log( this.returnedResults);

          // if (this.returnedResults == 0) {
          //   this.no_result_flag = true;
          // }

          // console.log(this.searchResult[0].galleryURL);
          for (let item of this.searchResult) {
            // console.log(item);

            if (item["galleryURL"] == undefined || item["galleryURL"] == "https://thumbs1.ebaystatic.com/pict/04040_0.jpg") {
              item["galleryURL"] = 'assets/ebay_default.png';
            }

            item.showing_detail = false;  // flag for show hide button
          }
          // console.log(this.searchResult[0]["xxx"]);



        }
          // response => console.log("Success", response),
          // error => console.error("Error", error)

        );


    }


  }
}

/** A hero's name can't match the hero's alter ego */
export const rangeValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const from = control.get('from').value;
  const to = control.get('to').value;
  console.log(from);
  console.log(to);


  let patt = new RegExp("^[+]?([.]\\d+|\\d+([.]\\d+)?)$");

  if (from != '' && !patt.test(from)) {
    console.log('from not number error');
    return { 'rangeError': true };
  }

  if (to != '' && !patt.test(to)) {
    console.log('to not number error');
    return { 'rangeError': true };
  }

  if (from != '' && to != '') {
    if (Number(from) > Number(to)) {
      console.log("from > to error");
      return { 'rangeError': true };
    }
  }

  return null;

  return (from != null && from < 0)
    || (to != null && to < 0)
    || (from != null && to != null && from >= 0 && to >= 0 && from > to)
    ? { 'rangeError': true } : null;
};
/*
form-contrl font size
OK: max = 0 = inf
OK: currPage reset on search
OK: bold warning bold Result for
Fail: dropdown gray background
OK: min max price 1 line on mobile
*/