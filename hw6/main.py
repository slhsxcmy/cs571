from flask import * #Flask, request, jsonify, send_from_directory
import requests
import json

app = Flask(__name__, static_url_path='/static')

size = 100

@app.route('/')
def entry():
    # print(app.root_path)
    return send_from_directory(app.root_path, 'index.html')

@app.route('/query')
def query():
    # keyword = request.args.get('keyword')
    # query_string
    YourAppID = "MingyuCu-CS571-PRD-12eae7200-3078afa9"
    
    payload = {
        'OPERATION-NAME': 'findItemsAdvanced', 
        'SERVICE-VERSION': '1.0.0',
        'SECURITY-APPNAME': YourAppID,
        'RESPONSE-DATA-FORMAT': 'JSON',
        'REST-PAYLOAD': 'true',
        # 'keywords': keywords,
        'paginationInput.entriesPerPage': size,
        # 'sortOrder': sortOrder,
    }
    # request.args is a dict
    payload.update(request.args) 
    
    # Call eBay API
    response = requests.get('https://svcs.ebay.com/services/search/FindingService/v1', params=payload)
    
    # check JSON from eBay
    return_json = parse(response)
    # print(response)
    # print(response.json())

    return return_json

# helper function
def parse(response):

    response_json = response.json()
    return_dict = {}

    totalResults = int(response_json["findItemsAdvancedResponse"][0]["paginationOutput"][0]["totalEntries"][0])
    return_dict['totalResults'] = totalResults
    return_dict['searchResult'] = []  # list
    count = 0
    i = 0
    while count < 10 and count < size and count < totalResults:
        # print(i)
        try:
            item_dict = {}
            # return_dict['searchResult'].append({}) # dict
            item_dict["galleryURL"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["galleryURL"][0]
            item_dict["title"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["title"][0]
            item_dict["category"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["primaryCategory"][0]["categoryName"][0]
            item_dict["viewItemURL"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["viewItemURL"][0]
            item_dict["condition"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["condition"][0]["conditionDisplayName"][0]
            item_dict["topRatedListing"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["topRatedListing"][0]
            item_dict["price"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["sellingStatus"][0]["convertedCurrentPrice"][0]["__value__"]
            item_dict["shipping"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["shippingInfo"][0]["shippingServiceCost"][0]["__value__"]
            return_dict['searchResult'].append(item_dict)
            i += 1
            count += 1

        except KeyError as e:
            i += 1
        
    return jsonify(return_dict)
    # print(type(response))
    # print(type(json))
    # dict = []
    # print(json["findItemsAdvancedResponse"]["ack"])
    # return json

# https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=harry%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000

# http://127.0.0.1:8080/query?keywords=harry%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100
if __name__ == '__main__':
    # This is used when running locally only. When deploying to Google App
    # Engine, a webserver process such as Gunicorn will serve the app. This
    # can be configured by adding an `entrypoint` to app.yaml.
    app.run(host='127.0.0.1', port=8080, debug=True)
# [END gae_python37_app]

# TODO ASK
'''
1. How to run HTML with send_static_file()
2. What's called valid item from eBay
3. Default eBay image is 404
'''
