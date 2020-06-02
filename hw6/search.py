# https://console.cloud.google.com/

import requests

def getURL():
    YourAppID = "MingyuCu-CS571-PRD-12eae7200-3078afa9"
    keywords = "TODO iphone"
    entriesPerPage = '100'
    sortOrder = 'TODO BestMatch'

    minPriceDict = {
        'name': 'MinPrice',
        'value': 'TODO 25',
        'paramName': 'Currency',
        'paramValue': 'USD'
    }
    maxPriceDict = {
        'name': 'MaxPrice',
        'value': 'TODO 25',
        'paramName': 'Currency',
        'paramValue': 'USD'
    }

    returnAcceptedDict = {
        'name': 'ReturnsAcceptedOnly',
        'value': 'true'
    }

    freeShippingDict = {
        'name': 'FreeShippingOnly',
        'value': 'true'
    }

    expeditedShippingDict = {
        'name': 'ExpeditedShippingType',
        'value': 'Expedited'
    } 

    conditionDict = {
        'name': 'Condition'
        # TODO
    }        # &itemFilter(X).value(0)=1000&itemFilter(X).value(1)=3000&itemFilter(X).value(2)=4000
    
    payload = {
        'OPERATION-NAME': 'findItemsAdvanced', 
        'SERVICE-VERSION': '1.0.0',
        'SECURITY-APPNAME': YourAppID,
        'RESPONSE-DATA-FORMAT': 'JSON',
        'REST-PAYLOAD': 'true',
        # 'keywords': keywords,
        # 'paginationInput.entriesPerPage': entriesPerPage,
        # 'sortOrder': sortOrder,
    }

# https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MingyuCu-CS571-PRD-12eae7200-3078afa9&RESPONSE-DATA-FORMAT=JSON&keywords=harry%20potter&sortOrder=PricePlusShippingLowest&itemFilter(0).name=ReturnsAcceptedOnly&itemFilter(0).value=true&itemFilter(1).name=MinPrice&itemFilter(1).value=1&itemFilter(2).name=MaxPrice&itemFilter(2).value=100&itemFilter(3).name=Condition&itemFilter(3).value=1000


    r = requests.get('https://svcs.ebay.com/services/search/FindingService/v1', params=payload)
    print(r.url)

def main():
    getURL()


if __name__ == '__main__':
    main()