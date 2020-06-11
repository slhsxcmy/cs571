const express = require('express');

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

	// https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000

	response.json({ username: 'Flavio' })
});

app.listen(PORT, () => console.log(`Express server currently running on port ${PORT}`));

