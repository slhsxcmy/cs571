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
YourAppID = "MingyuCu-CS571-PRD-12eae7200-3078afa9"


const express = require('express');

const axios = require('axios');


const app = express();
const PORT = 3000;

app.get(`/hello`, (request, response) => {
    console.log(request.url);
    response.send('Hello World!!!!!!!!!!!!!!!!');
});

app.route('/api/cats').get((req, res) => {
    res.send({
        cats: [{ name: 'lilly' }, { name: 'lucy' }],
    })
})

app.route('/api/cats/:name').get((req, res) => {
    const requestedCatName = req.params['name']
    res.send({ name: requestedCatName })
})

app.get(`/`, (request, response) => {
    response.send(`
		<div>
			<h1>Todo List</h1>
			<ul>
				<li style="text-decoration:line-through">Learn about Express routing</li>
				<li style="text-decoration:line-through">Create my own routes</li>
			</ul>
		</div>
	`);
});

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
        response.json(api_response.data);
    }).catch(function(error) {
        console.log(error);
    });


});

app.listen(PORT, () => console.log(`Express server currently running on port ${PORT}`));