import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  baseurl = 'http://localhost:3000/query?';
  constructor(private _http: HttpClient) { }

  search(myform) {


    var filter_index = 0;
    var query_string = "";

    // get keyword. Validation is done by required attribute
    var kw = myform.keyword;
    query_string += "keywords=" + myform.keyword;

    // handle sortOrder
    query_string += "&sortOrder=" + myform.sort;

    if (myform.from != null && myform.from != '') {
      query_string += "&itemFilter(" + filter_index + ").name=MinPrice&itemFilter(" + filter_index + ")=" + myform.from + " &itemFilter(" + filter_index + ").paramName=Currency&itemFilter(" + filter_index + ").paramValue=USD";
      ++filter_index;
    }
    if (myform.to != null && myform.to != '') {
      query_string += "&itemFilter(" + filter_index + ").name=MaxPrice&itemFilter(" + filter_index + ")=" + myform.to + "&itemFilter(" + filter_index + ").paramName=Currency&itemFilter(" + filter_index + ").paramValue=USD";
      ++filter_index;
    }

    // count how many conditions checked
    // alert(myform.conditions.length);


    // var count = 0;
    // var last = -1;

    let checked = [];
    if (myform.condition.new != "") checked.push("1000");
    if (myform.condition.used != "") checked.push("3000");
    if (myform.condition.vgood != "") checked.push("4000");
    if (myform.condition.good != "") checked.push("5000");
    if (myform.condition.acceptable != "") checked.push("6000");


    // console.log("count: " + checked.length);
    if (checked.length == 0) { }
    else if (checked.length == 1) {
      query_string += "&itemFilter(" + filter_index + ").name=Condition&itemFilter(" + filter_index + ").value=" + checked[0];
      ++filter_index;
    }
    else {
      query_string += "&itemFilter(" + filter_index + ").name=Condition";
      var j = 0;
      for (var i = 0; i < checked.length; i++) {

        query_string += "&itemFilter(" + filter_index + ").value(" + (j++) + ")=" + checked[i];
      }
      ++filter_index;
    }

    // check ReturnsAcceptedOnly
    if (myform.returns) {
      query_string += "&itemFilter(" + filter_index + ").name=ReturnsAcceptedOnly&itemFilter(" + filter_index + ").value=true"
      ++filter_index;
    }

    // check FreeShippingOnly
    if (myform.freeshipping) {
      query_string += "&itemFilter(" + filter_index + ").name=FreeShippingOnly&itemFilter(" + filter_index + ").value=true"
      ++filter_index;
    }

    // check ExpeditedShippingType
    if (myform.expshipping) {
      query_string += "&itemFilter(" + filter_index + ").name=ExpeditedShippingType&itemFilter(" + filter_index + ").value=Expedited"
      ++filter_index;
    }

    let fullurl = this.baseurl + query_string;
    console.log(fullurl);
    return this._http.get<any>(fullurl, {

    });
  }
}
