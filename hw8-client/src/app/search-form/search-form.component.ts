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
  range_invalid_flag = false;
  
  constructor(private fb: FormBuilder) { }
  mainForm = this.fb.group({ 
    keyword: ['', Validators.required],
    range: this.fb.group({
      from: [''],
      to: ['']
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
  })

  onSubmit() {


    console.warn(this.mainForm.value);

    if (this.mainForm.get(['range','from']).value > this.mainForm.get(['range','to']).value) {
      // console.log('Incorrect');
      this.range_invalid_flag = true;
    }
    // console.warn(this.mainForm.controls['keyword'].value);
    // console.warn(this.mainForm.get(['range','from']).value);
    // console.warn(this.mainForm.get('range').get('to').value);

  }


}
