import requests

def getURL():
    YourAppID = "TODO ebay key"
    keywords = "TODO iphone"
    entriesPerPage = '20'
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
        'keywords': keywords,
        'paginationInput.entriesPerPage': entriesPerPage,
        'sortOrder': sortOrder,
    }




    r = requests.get('https://svcs.ebay.com/services/search/FindingService/v1', params=payload)
    print(r.url)

def main():
    getURL()


if __name__ == '__main__':
    main()