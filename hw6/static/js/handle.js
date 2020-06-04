function validate() {
    // document.getElementById("total").innerHTML = "Text";
    // var xhttp = new XMLHttpRequest();
    
    // xhttp.onreadystatechange = function(){
    //     // document.getElementById("usernameMessage").innerHTML = thisvb.responseText;   
    // }

    var filter_index = 0;
    var query_string = "";

    // get keyword. Validation is done by required attribute
    query_string += "keywords=" + myform.keyword.value;

    // handle sortOrder
    query_string += "&sortOrder=" + myform.sort.value;

    // check price range
    if(myform.from.value < 0 || myform.to.value < 0) {
        alert("Price Range values cannot be negative! Please try a value greater than or equal to 0.0");
        return false;
    }
    if(myform.from.value > myform.to.value && myform.to.value > 0) {
        alert("Oops! Lower price limit cannot be greater than upper price limit! Please try again.");
        return false;
    }
    if(myform.from.value > 0){
        query_string += "&itemFilter(" + filter_index + ").name=MinPrice&itemFilter(" + filter_index + ").value=" + myform.from.value + " &itemFilter(" + filter_index + ").paramName=Currency&itemFilter(" + filter_index + ").paramValue=USD";
        ++filter_index;
    }
    if(myform.to.value > 0){
        query_string += "&itemFilter(" + filter_index + ").name=MaxPrice&itemFilter(" + filter_index + ").value=" + myform.to.value + "&itemFilter(" + filter_index + ").paramName=Currency&itemFilter(" + filter_index + ").paramValue=USD";
        ++filter_index;
    }

    // count how many conditions checked
    // alert(myform.conditions.length);
    var count = 0;
    var last = -1;
    for (var i = 0; i < myform.conditions.length; i++) {
        if(myform.conditions[i].checked) {
            ++count;
            last = myform.conditions[i].value;
        }
    }

    if(count == 0) {} 
    else if(count == 1) {
        query_string += "&itemFilter(" + filter_index + ").name=Condition&itemFilter(" + filter_index + ").value=" + last;
        ++filter_index;
    } else {
        query_string += "&itemFilter(" + filter_index + ").name=Condition";
        var j = 0;
        for (var i = 0; i < myform.conditions.length; i++) {
            if(!myform.conditions[i].checked) continue;
            query_string += "&itemFilter(" + filter_index + ").value(" + (j++) + ")=" + myform.conditions[i].value;
        }
        ++filter_index;
    }

    // check ReturnsAcceptedOnly
    if(myform.returns.checked){
        query_string += "&itemFilter(" + filter_index + ").name=ReturnsAcceptedOnly&itemFilter(" + filter_index + ").value=true"
        ++filter_index;
    }

    // check FreeShippingOnly
    if(myform.freeshipping.checked){
        query_string += "&itemFilter(" + filter_index + ").name=FreeShippingOnly&itemFilter(" + filter_index + ").value=true"
        ++filter_index;
    }

    // check ExpeditedShippingType
    if(myform.expshipping.checked){
        query_string += "&itemFilter(" + filter_index + ").name=ExpeditedShippingType&itemFilter(" + filter_index + ").value=Expedited"
        ++filter_index;
    }

    URL = "http://127.0.0.1:8080/query?" + query_string; //.substring(1);
    // alert(URL);
    console.log(URL);


    fetch(URL/*, {mode: 'no-cors'}*/)
    .then((response) => response.json())
    .then((json) => {
        console.log(json);
        // alert(json['totalResults']);
        totalResults = json['totalResults'];
        returnedResults = json['returnedResults'];
        if(totalResults == 0){
            document.getElementById('total').innerHTML = 'No Results found';
        } else {
            document.getElementById('total').innerHTML = totalResults + ' Results found';
        }
        for (var i = 0; i < 3; i++) {
            document.getElementById('item' + i).style.display = 'block';
        }
        if(returnedResults > 3) {
            document.getElementById('show_more').style.display = 'block';
        }
        
    })

    return false;
}

function showMore() {
    for (var i = 3; i < returnedResults; i++) {
        document.getElementById('item' + i).style.display = 'block';
    }
    document.getElementById('show_more').style.display = 'none';
    document.getElementById('show_less').style.display = 'block';
    window.scrollTo(0,document.body.scrollHeight);
}
function showLess() {
    for (var i = 3; i < returnedResults; i++) {
        document.getElementById('item' + i).style.display = 'none';
    }
    document.getElementById('show_more').style.display = 'block';
    document.getElementById('show_less').style.display = 'none';
    window.scrollTo(0, 0);
}