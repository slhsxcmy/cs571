from flask import Flask, request
import requests

app = Flask(__name__)


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
        'paginationInput.entriesPerPage': 1,
        # 'sortOrder': sortOrder,
    }
    # request.args is a dict
    payload.update(request.args) 
    
    # Call eBay API
    response = requests.get('https://svcs.ebay.com/services/search/FindingService/v1', params=payload)
    
    # return eBay JSON
    # print(response)
    # print(response.json())

    return response.json()

# https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=harry%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100
# http://127.0.0.1:8080/query?keywords=harry%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100
if __name__ == '__main__':
    # This is used when running locally only. When deploying to Google App
    # Engine, a webserver process such as Gunicorn will serve the app. This
    # can be configured by adding an `entrypoint` to app.yaml.
    app.run(host='127.0.0.1', port=8080, debug=True)
# [END gae_python37_app]
