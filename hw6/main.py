from flask import * #Flask, request, jsonify, send_from_directory
import requests
import json

app = Flask(__name__, static_url_path='/static')

size = 100

@app.route('/')
def entry():
    # print(app.root_path)
    return app.send_static_file('index.html')

@app.route('/hello')
def hello():
    """Return a friendly HTTP greeting."""
    return 'Hello World!!!!!!!!!!!!!'

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

'''
returnedResults: 10
searchResult: Array(10)
0:
category: "Single Use Batteries"
condition: "New"
expeditedShipping: "false"
galleryURL: "https://thumbs4.ebaystatic.com/m/mb4mCl0Ma9MyIeZONOX-B_Q/140.jpg"
location: "Granada Hills,CA,USA"
price: "21.99"
returnsAccepted: "true"
shippingServiceCost: "0.0"
title: "Energizer Alkaline AA & AAA Max Batteries(24+24-Pack) New Exp.12/2029"
topRatedListing: "true"
viewItemURL: "https://www.ebay.com/itm/Energizer-Alkaline-AA-AAA-
...
totalResults: 3278910
'''
# helper function
def parse(response):

    response_json = response.json()
    return_dict = {}
    # print(response_json["findItemsAdvancedResponse"][0])
    totalResults = 0
    try:
        totalResults = int(response_json["findItemsAdvancedResponse"][0]["paginationOutput"][0]["totalEntries"][0])
    except Exception as e:
        print(e)
    return_dict['totalResults'] = totalResults
    return_dict['searchResult'] = []  # list
    count = 0  # returned results to front end
    i = 0  # received results from ebay
    print(size, totalResults)
    while count < 10 and count < size and count < totalResults and i < size and i < totalResults:
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
            item_dict["returnsAccepted"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["returnsAccepted"][0]
            item_dict["price"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["sellingStatus"][0]["convertedCurrentPrice"][0]["__value__"]
            item_dict["shippingServiceCost"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["shippingInfo"][0]["shippingServiceCost"][0]["__value__"]
            item_dict["expeditedShipping"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["shippingInfo"][0]["expeditedShipping"][0]
            item_dict["location"] = response_json["findItemsAdvancedResponse"][0]["searchResult"][0]["item"][i]["location"][0]
            return_dict['searchResult'].append(item_dict)
            i += 1
            count += 1

        except KeyError:
            i += 1
        except Exception as e:
            print('Exception:',e)
            break
        
    return_dict['returnedResults'] = count  # Should be usually 10
    return jsonify(return_dict)
    # print(type(response))
    # print(type(json))
    # dict = []
    # print(json["findItemsAdvancedResponse"]["ack"])
    # return json

# https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value(0)=New&itemFilter(3).value(1)=3000

# https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=a

# http://127.0.0.1:8080/query?keywords=harry%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100
if __name__ == '__main__':
    # This is used when running locally only. When deploying to Google App
    # Engine, a webserver process such as Gunicorn will serve the app. This
    # can be configured by adding an `entrypoint` to app.yaml.
    app.run(host='127.0.0.1', port=8080, debug=True)
# [END gae_python37_app]

