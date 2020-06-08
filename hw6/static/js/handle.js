function genResultsDiv(){

    for (var i = 0; i < 10; i++) {
        var newdiv = document.createElement('div');

        document.getElementById('tables').appendChild(newdiv);
        newdiv.id = "item" + i;
        newdiv.className = "items";
        newdiv.onclick = function() { expand(this); }
        
        var newtable = document.createElement('table');
        newdiv.appendChild(newtable);
        
        
        // close button x
        var but = document.createElement('button');
        newtable.appendChild(but);
        but.appendChild(document.createTextNode('❌'));
        but.className = 'close';
        but.onclick = function() {event.stopPropagation(); collapse(this.parentNode.parentNode);}
        but.style.display = 'none';
        but.style.textShadow = 'black -2px 0px, black 2px 0px, black 0px -2px, black 0px 2px';
        but.id = 'item' + i + 'close';
        // <button aria-label="Close">×</button>



        var r1 = document.createElement('tr');
        var r2 = document.createElement('tr');
        var r3 = document.createElement('tr');
        var r4 = document.createElement('tr');
        var r5 = document.createElement('tr');
        var r6 = document.createElement('tr');
        newtable.appendChild(r1);
        newtable.appendChild(r2);
        newtable.appendChild(r3);
        newtable.appendChild(r4);
        newtable.appendChild(r5);
        newtable.appendChild(r6);

        var c_pic = document.createElement('td');  
        var c_name = document.createElement('td'); 
        var c_cat = document.createElement('td');  
        var c_cond = document.createElement('td'); 
        var c_ret = document.createElement('td');  
        var c_ship = document.createElement('td'); 
        var c_price = document.createElement('td');
        c_pic.rowSpan = 6;
        c_pic.id = "item" + i + "pic";
        c_name.id = "item" + i + "name";
        c_cat.id = "item" + i + "cat";
        c_cond.id = "item" + i + "cond";
        c_ret.id = "item" + i + "ret";
        c_ship.id = "item" + i + "ship";
        c_price.id = "item" + i + "price";

        c_pic.className = 'narrow_cell';
        c_name.className = 'wide_cell top_cell';
        c_cat.className = 'wide_cell';
        c_cond.className = 'wide_cell';
        c_ret.className = 'wide_cell';
        c_ship.className = 'wide_cell';
        c_price.className = 'wide_cell';
        

        r1.appendChild(c_pic);
        r1.appendChild(c_name);
        r2.appendChild(c_cat);
        r3.appendChild(c_cond);
        r4.appendChild(c_ret);
        r5.appendChild(c_ship);
        r6.appendChild(c_price);

        // var image = document.createElement('img');
        // image.id = "item" + i + "img";
        // c_pic.appendChild(image);

    }
    
}

function validate() {

    // clear tables first
    document.getElementById('tables').innerHTML = '';
    genResultsDiv();

    document.getElementById('total').innerHTML = '';
    document.getElementById('show_more').style.display = 'none';
    document.getElementById('show_less').style.display = 'none';

    // document.getElementById("total").innerHTML = "Text";
    // var xhttp = new XMLHttpRequest();
    
    // xhttp.onreadystatechange = function(){
    //     // document.getElementById("usernameMessage").innerHTML = thisvb.responseText;   
    // }

    var filter_index = 0;
    var query_string = "";

    // get keyword. Validation is done by required attribute
    var kw = myform.keyword.value;
    query_string += "keywords=" + myform.keyword.value;

    // handle sortOrder
    query_string += "&sortOrder=" + myform.sort.value;

    // check price range
    // console.log("from: " + myform.from.value + " to: " + myform.to.value + " comp val: " + (myform.from.value > myform.to.value) + " comp num: " + (myform.from.valueAsNumber > myform.to.valueAsNumber))
        
    if(myform.from.value < 0 || myform.to.value < 0) {
        alert("Price Range values cannot be negative! Please try a value greater than or equal to 0.0");
        return false;
    }
    if(myform.from.valueAsNumber > myform.to.valueAsNumber && myform.to.value > 0) {
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

    URL = "/query?" + query_string; //.substring(1);
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
            document.getElementById('total').innerHTML = totalResults + ' Results found for <i>' + kw + '</i>';
        }
        for (var i = 0; i < 3 && i < returnedResults; i++) {
            document.getElementById('item' + i).style.display = 'block';
        }
        if(returnedResults > 3) {
            document.getElementById('show_more').style.display = 'block';
            document.getElementById('show_less').style.display = 'none';
        } else {
            document.getElementById('show_more').style.display = 'none';
            document.getElementById('show_less').style.display = 'none';
        }

        for (var i = 0; i < returnedResults; i++) {
            var item = json['searchResult'][i];
            // document.getElementById("item" + i + "img").src = item['viewItemURL']
            var c_pic = document.getElementById("item" + i + "pic");
            var c_name = document.getElementById("item" + i + "name");
            var c_cat = document.getElementById("item" + i + "cat");
            var c_ret = document.getElementById("item" + i + "ret");
            var c_ship = document.getElementById("item" + i + "ship");
            var c_cond = document.getElementById("item" + i + "cond");
            var c_price = document.getElementById("item" + i + "price");

            c_pic.textContent = '';
            c_name.textContent = '';
            c_cat.textContent = '';
            c_cond.textContent = '';
            c_ret.textContent = '';
            c_ship.textContent = '';
            c_price.textContent = '';

            var image = document.createElement('img');
            // image.id = "item" + i + "img";
            c_pic.appendChild(image);
            image.src = item['galleryURL'];
            // console.log(image.src)
            // console.log(image.src == "https://thumbs1.ebaystatic.com/pict/04040_0.jpg");
            if(image.src == "https://thumbs1.ebaystatic.com/pict/04040_0.jpg") {
                image.src = '/static/images/ebay_default.jpg';
            }
            image.className = 'photo';

            var name_link = document.createElement('a');
            c_name.appendChild(name_link);
            name_link.href = item['viewItemURL'];
            // name_link.target = "_blank";
            var name = document.createTextNode(item['title']);
            name_link.appendChild(name);

            var cat = document.createTextNode("Category: " + item['category']);
            c_cat.appendChild(cat);
            var link = document.createElement('a');
            c_cat.appendChild(link);
            link.href = item['viewItemURL'];
            var redir = document.createElement('img');
            link.appendChild(redir);
            redir.src = "/static/images/redirect.png";
            redir.className = "redirect";

            var cond = document.createTextNode("Condition: " + item['condition']);
            c_cond.appendChild(cond);
            if(item['topRatedListing'] == 'true') {
                var top = document.createElement('img');
                c_cond.appendChild(top);
                top.className = "topRated";
                top.src = "/static/images/topRatedImage.png";
            }




            var seller = document.createTextNode('Seller ');
            c_ret.appendChild(seller);
            var bold = document.createElement('b');
            c_ret.appendChild(bold);

            if(item['returnsAccepted'] == 'true'){
                var bold_words = document.createTextNode('accepts');
            } else {
                var bold_words = document.createTextNode('does not accept returns');
            }
            bold.appendChild(bold_words);

            
            if(item['returnsAccepted'] == 'true'){
                var returns = document.createTextNode(' returns');
            } else {
                var returns = document.createTextNode('');
            }
            c_ret.appendChild(returns);




            if(item['shippingServiceCost'] > 0) {
                var freeshipping = document.createTextNode('No Free Shipping');
            } else {
                var freeshipping = document.createTextNode('Free Shipping');           
            }
            c_ship.appendChild(freeshipping);

            if(item['expeditedShipping'] == 'true') {
                // console.log('exp!!!!')
                var expedit = document.createTextNode(' -- Expedited Shipping available');
            } else {
                // console.log('NO exp!!!!')
                var expedit = document.createTextNode('');
            }
            c_ship.appendChild(expedit);



            // Price: $20 (+ $3 for shipping)
            var boldprice = document.createElement('b');
            c_price.appendChild(boldprice);

            var rawprice = document.createTextNode('Price: $' + item['price']);
            boldprice.appendChild(rawprice);
            if(item['shippingServiceCost'] > 0) {
                var shipprice = document.createTextNode(' (+ $' + item['shippingServiceCost'] + ' for shipping)');
            } else {
                var shipprice = document.createTextNode('');
            }
            boldprice.appendChild(shipprice);

            var loc_span = document.createElement('span');
            c_price.appendChild(loc_span);
            loc_span.id = 'item' + i + 'loc_span';
            var loc = document.createTextNode(' From ' + item['location']);
            // 
            loc_span.appendChild(loc);
            

            c_ret.style.display = 'none';
            c_ship.style.display = 'none';
            loc_span.style.display = 'none';




            // document.getElementById(loc.id).style.display = 'none';
            // alert(loc.id);
            // loc.display = 'none';

            // document.getElementById("item" + i + "pic").innerHTML = '';

            // // <img class="photo" src="' + item['galleryURL'] + '"></img>';
            // document.getElementById("item" + i + "name").innerHTML = '<a href="' + item['viewItemURL'] + '">' + item['title'] + '</a>';
            // document.getElementById("item" + i + "cat").innerHTML = "Category: " + item['category'] + '<a href="' + item['viewItemURL'] + '">' + '<img class="redirect" src="/static/images/redirect.png"></img>' + '</a>';
            // document.getElementById("item" + i + "cond").innerHTML = "Condition: " + item['condition'];
            // document.getElementById("item" + i + "price").innerHTML = "<b>Price: $" + item['price'] + "</b>";

            
            // var division = document.getElementById('item' + i);
            // var table = division.getElementsByTagName('table');
            // console.log(table[0].innerHTML);
            // tds.childNodes[0].getElementsByTagName('img').src = item['viewItemURL'];
        }
        
    })

    // console.log('validate() return true');

    return true;
}

function showMore() {
    for (var i = 3; i < returnedResults; i++) {

        
        document.getElementById('item' + i).style.display = 'block';
    }
    document.getElementById('show_more').style.display = 'none';
    document.getElementById('show_less').style.display = 'block';
    // window.scrollTo(0,document.body.scrollHeight);
    window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' })
    // console.log("document.body.scrollHeight="+document.body.scrollHeight);
}
function showLess() {
    for (var i = 3; i < returnedResults; i++) {
        document.getElementById('item' + i).style.display = 'none';
    }
    document.getElementById('show_more').style.display = 'block';
    document.getElementById('show_less').style.display = 'none';
    // window.scrollTo(0, 0);
    
    window.scrollTo({ top: -1, behavior: 'smooth' })
    // console.log("document.body.scrollHeight="+document.body.scrollHeight);
}

function expand(div) {
    // console.log("expand");
    id = div.id; 
    var c_ret = document.getElementById(id + "ret");
    var c_ship = document.getElementById(id + "ship");
    var loc_span = document.getElementById(id + "loc_span");
    var but = document.getElementById(id + "close");

    c_ret.style.display = 'block';
    c_ship.style.display = 'block';
    loc_span.style.display = 'inline';
    but.style.display = 'block';

    // console.log(div.onclick);
    // if(div.onclick == function() {}){
    //     div.onclick = function() { expand(this); }
    // } else {
        div.onclick = function() {}
    // }
    
    // alert("HAAA");loc_span.id = 'item' + i + 'loc_span';

    // use this
  // statements
  // var id = div.id; // or however you want to use the id
  // console.log(id);
  // alert(id);// statements


}
function collapse(div) {
    // console.log("collapse");
    id = div.id; 
    // console.log(div);
    var c_ret = document.getElementById(id + "ret");
    var c_ship = document.getElementById(id + "ship");
    var loc_span = document.getElementById(id + "loc_span");
    var but = document.getElementById(id + "close");
         
    c_ret.style.display = 'none';
    c_ship.style.display = 'none';
    loc_span.style.display = 'none';
    but.style.display = 'none';

    div.onclick = function() { expand(this); }
        

}

function reset_form() {
    // console.log('here');
    var dropDown = document.getElementById("sort");
    dropDown.selectedIndex = 0;
    // console.log('dropDown.selectedIndex: ' + dropDown.selectedIndex);
}