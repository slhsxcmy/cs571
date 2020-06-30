// const http = require('http');

// const server = http.createServer(function(request, response) {
//     console.log(request.url);
//     console.log(request.params);
//     if (request.url === '/') {
//         response.write('Hello World');
//         response.end();
//     }
//     if (request.url === '/query') {
//         // console.log(request.query);

//         var json = JSON.stringify({
//             anObject: '1',
//             anArray: '22',
//             another: "item"
//         });
//         response.end(json);
//     }
// });

// server.on('connection', (socket) => {
//     console.log('New connection!');
// })

// server.listen(3000);
// console.log('Listening on port 3000.');

// http://zetcode.com/javascript/axios/
size = 100
max_returned = 100
YourAppID = "MingyuCu-CS571-PRD-12eae7200-3078afa9"


const express = require('express');
const cors = require('cors');
const axios = require('axios');


const app = express();
app.use(cors());

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

app.get('/', (req, res) => {
    res.send('CS571 Summer 2020<br>Server for Homeworks 8 and 9!<br>Mingyu Cui');
});


function parse(response_json) {
    let return_dict = {};
    let totalResults = 0;
    try {
        totalResults = response_json["findItemsAdvancedResponse"][0]["paginationOutput"][0]["totalEntries"][0];

    } catch (error) {
        console.log('Exception:', error.message);
        // return return_dict;
    }

    return_dict['totalResults'] = totalResults
    return_dict['searchResult'] = []

    let count = 0 // returned results to front end
    let i = 0 // received results from ebay
    
    let items = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"];
            
    while (count < max_returned && i < size && i < totalResults) {

        console.log(count + " " + i);
        
        try {
            let item_dict = {}
            let item = items[i]
            // console.log(item)
            item_dict["title"] = item["title"][0]
            try {
                item_dict["galleryURL"] = item["galleryURL"][0] // check removed. Piazza @399 
            } catch (error) {
                // replace with eBay default
                item_dict["galleryURL"] = "https://thumbs1.ebaystatic.com/pict/04040_0.jpg";
                console.log('no image found');
            }
            item_dict["price"] = item["sellingStatus"][0]["convertedCurrentPrice"][0]["__value__"]
            item_dict["location"] = item["location"][0]
            item_dict["category"] = item["primaryCategory"][0]["categoryName"][0]
            item_dict["condition"] = item["condition"][0]["conditionDisplayName"][0]
            item_dict["shippingType"] = item["shippingInfo"][0]["shippingType"][0]
            item_dict["shippingServiceCost"] = item["shippingInfo"][0]["shippingServiceCost"][0]["__value__"]
            item_dict["shipToLocations"] = item["shippingInfo"][0]["shipToLocations"][0]
            item_dict["expeditedShipping"] = item["shippingInfo"][0]["expeditedShipping"][0]
            item_dict["oneDayShippingAvailable"] = item["shippingInfo"][0]["oneDayShippingAvailable"][0]
            item_dict["handlingTime"] = item["shippingInfo"][0]["handlingTime"][0]
            item_dict["bestOfferEnabled"] = item["listingInfo"][0]["bestOfferEnabled"][0]
            item_dict["buyItNowAvailable"] = item["listingInfo"][0]["buyItNowAvailable"][0]
            item_dict["listingType"] = item["listingInfo"][0]["listingType"][0]
            item_dict["gift"] = item["listingInfo"][0]["gift"][0]
            item_dict["watchCount"] = item["listingInfo"][0]["watchCount"][0]

            item_dict["viewItemURL"] = item["viewItemURL"][0]

            item_dict["itemId"] = item["itemId"][0]

            item_dict["topRatedListing"] = item["topRatedListing"][0]

            // add shippingInfo obj for hw9
            item_dict["shippingInfo"] = item["shippingInfo"][0]
            delete item_dict["shippingInfo"] ['shippingServiceCost'];
            Object.keys(item_dict["shippingInfo"]).forEach(function(key) {
                // console.log('Key : ' + key + ', Value : ' + item_dict["shippingInfo"][key][0])
                item_dict["shippingInfo"][key] = item_dict["shippingInfo"][key][0];
            })

            // console.log(item_dict["aa"][0])

            // let valid = true;
            /*
            for (const key in item_dict) {
                if (item_dict.hasOwnProperty(key)) {
                    let dumb = item_dict[key][0]; // trigger error by taking first letter
                }
            } */

            // if (valid) {
            return_dict['searchResult'].push(item_dict)
            i += 1
            count += 1
                // } else {
                // i += 1
                // }

        } catch (error) {
            i += 1
            console.log('Exception:', error.message)
        }
    }

    return_dict['returnedResults'] = count // Should be usually 100
    return return_dict;
}


function parse_android(response_json) {
    let return_dict = {};
    let totalResults = 0;
    try {
        totalResults = response_json["findItemsAdvancedResponse"][0]["paginationOutput"][0]["totalEntries"][0];

    } catch (error) {
        console.log('Exception:', error.message);
        // return return_dict;
    }

    return_dict['totalResults'] = totalResults
    return_dict['searchResult'] = []

    let count = 0 // returned results to front end
    let i = 0 // received results from ebay
    
    let items = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"];
            
    while (count < max_returned && i < size && i < totalResults) {

        console.log(count + " " + i);
        
        try {
            let item_dict = {}
            let item = items[i]
            // console.log(item)
            item_dict["title"] = item["title"][0]
            try {
                item_dict["galleryURL"] = item["galleryURL"][0] // check removed. Piazza @399 
            } catch (error) {
                // replace with eBay default
                item_dict["galleryURL"] = "https://thumbs1.ebaystatic.com/pict/04040_0.jpg";
                console.log('no image found');
            }
            item_dict["price"] = item["sellingStatus"][0]["convertedCurrentPrice"][0]["__value__"]
            item_dict["location"] = item["location"][0]
            item_dict["category"] = item["primaryCategory"][0]["categoryName"][0]
            item_dict["condition"] = item["condition"][0]["conditionDisplayName"][0]
            item_dict["shippingType"] = item["shippingInfo"][0]["shippingType"][0]
            item_dict["shippingServiceCost"] = item["shippingInfo"][0]["shippingServiceCost"][0]["__value__"]
            item_dict["shipToLocations"] = item["shippingInfo"][0]["shipToLocations"][0]
            item_dict["expeditedShipping"] = item["shippingInfo"][0]["expeditedShipping"][0]
            item_dict["oneDayShippingAvailable"] = item["shippingInfo"][0]["oneDayShippingAvailable"][0]
            item_dict["handlingTime"] = item["shippingInfo"][0]["handlingTime"][0]
            item_dict["bestOfferEnabled"] = item["listingInfo"][0]["bestOfferEnabled"][0]
            item_dict["buyItNowAvailable"] = item["listingInfo"][0]["buyItNowAvailable"][0]
            item_dict["listingType"] = item["listingInfo"][0]["listingType"][0]
            item_dict["gift"] = item["listingInfo"][0]["gift"][0]
            item_dict["watchCount"] = item["listingInfo"][0]["watchCount"][0]

            item_dict["viewItemURL"] = item["viewItemURL"][0]

            item_dict["itemId"] = item["itemId"][0]

            item_dict["topRatedListing"] = item["topRatedListing"][0]

            // add shippingInfo obj for hw9
            item_dict["shippingInfo"] = item["shippingInfo"][0]
            delete item_dict["shippingInfo"] ['shippingServiceCost'];
            Object.keys(item_dict["shippingInfo"]).forEach(function(key) {
                // console.log('Key : ' + key + ', Value : ' + item_dict["shippingInfo"][key][0])
                item_dict["shippingInfo"][key] = item_dict["shippingInfo"][key][0];
            })

            // console.log(item_dict["aa"][0])

            // let valid = true;
            /*
            for (const key in item_dict) {
                if (item_dict.hasOwnProperty(key)) {
                    let dumb = item_dict[key][0]; // trigger error by taking first letter
                }
            } */

            // if (valid) {
            return_dict['searchResult'].push(item_dict)
            i += 1
            count += 1
                // } else {
                // i += 1
                // }

        } catch (error) {
            i += 1
            console.log('Exception:', error.message)
        }
    }

    return_dict['returnedResults'] = count // Should be usually 100
    return return_dict;
}


app.get(`/query`, (request, response) => {

    api_params = {

        'OPERATION-NAME': 'findItemsAdvanced',
        'SERVICE-VERSION': '1.0.0',
        'SECURITY-APPNAME': YourAppID,
        'RESPONSE-DATA-FORMAT': 'JSON',
        'REST-PAYLOAD': 'true',
        'paginationInput.entriesPerPage': size

    }


    //get query string
    // console.log(request.query);

    for (const key in request.query) {
        let value = request.query[key];
        api_params[key] = value;
    }

    // console.log(api_params);

    // // https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000

    // axios.get('https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000').then(resp => {

    //     console.log(resp.data);
    //     response.json(resp.data);
    // });


    axios.get('https://svcs.ebay.com/services/search/FindingService/v1', {
        params: api_params
    }).then(function(api_response) {
        // console.log(res.request._header);
        console.log(api_response.data);
        // response.json(api_response.data);

        return_json = parse(api_response.data);
        response.json(return_json)


    }).catch(function(error) {
        console.log(error);
    });


});





app.get(`/query_android`, (request, response) => {

    api_params = {

        'OPERATION-NAME': 'findItemsAdvanced',
        'SERVICE-VERSION': '1.0.0',
        'SECURITY-APPNAME': YourAppID,
        'RESPONSE-DATA-FORMAT': 'JSON',
        'REST-PAYLOAD': 'true',
        'paginationInput.entriesPerPage': size

    }


    //get query string
    // console.log(request.query);

    for (const key in request.query) {
        let value = request.query[key];
        api_params[key] = value;
    }

    // console.log(api_params);

    // // https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000

    // axios.get('https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000').then(resp => {

    //     console.log(resp.data);
    //     response.json(resp.data);
    // });


    axios.get('https://svcs.ebay.com/services/search/FindingService/v1', {
        params: api_params
    }).then(function(api_response) {
        // console.log(res.request._header);
        // console.log(api_response.data);
       
        return_json = parse_android(api_response.data);
        response.json(return_json)


    }).catch(function(error) {
        console.log(error);
    });


});


// app.route('/cats').get((req, res) => {
//     res.send({
//         cats: [{ name: 'lilly' }, { name: 'lucy' }],
//     })
// })

// app.route('/cats/:name').get((req, res) => {
//     const requestedCatName = req.params['name']
//     res.send({ name: requestedCatName })
// })


function parse_single(response_json) {
    let item_dict = {};
    try {
        item_dict.Subtitle = response_json.Item.Subtitle;
    } catch (error) {}

    let NameValueList = response_json.Item.ItemSpecifics.NameValueList;

    // console.log(NameValueList);
    item_dict.ItemSpecifics = [];
    try {
        for (let i = 0, j = 0; i < NameValueList.length && j < 5; i++) {
            let pair = NameValueList[i];
            // console.log("pair: " + pair);

            let name = pair.Name;
            let value = pair.Value[0];
            // console.log(name + ": " + value);

            if (name == "Brand") item_dict.Brand = value;
            else {
                item_dict.ItemSpecifics.push(value);
                j++;
            }
        }
    } catch (error) { console.log(error); }

    try {
        item_dict.Seller = response_json.Item.Seller;
        item_dict.ReturnPolicy = response_json.Item.ReturnPolicy;
        item_dict.PictureURL = response_json.Item.PictureURL;

    } catch (error) { console.log(error); }


    // return response_json;
    return item_dict;
}

app.get(`/single/:itemID`, (request, response) => {

    const itemID = request.params['itemID']

    api_params = {
        'callname': 'GetSingleItem',
        'responseencoding': 'JSON',
        'appid': YourAppID,
        'siteid': '0',
        'version': '967',
        'ItemID': itemID,
        'IncludeSelector': 'Description,Details,ItemSpecifics'

    }


    for (const key in request.query) {
        let value = request.query[key];
        api_params[key] = value;
    }


    axios.get('https://open.api.ebay.com/shopping', {
        params: api_params
    }).then(function(api_response) {
        // console.log(res.request._header);
        console.log(api_response.data);

        return_json = parse_single(api_response.data);
        response.json(return_json)

    }).catch(function(error) {
        console.log(error);
    });


});



// single item: https://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=MingyuCu-CS571-PRD-12eae7200-3078afa9&siteid=0&version=967&ItemID=254603172743&IncludeSelector=Description,Details,ItemSpecifics
// http://localhost:3000/single/253462854618

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server listening on port ${PORT}...`);
});