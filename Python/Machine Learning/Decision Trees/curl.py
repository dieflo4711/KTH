import requests

data = '{"state": "success", "description": "desc", "context": "test2", "target_url": "http://dleon.johvh.se"}'

response = requests.post('https://api.github.com/repos/dieflo4711/test/statuses/1b1ee2e9624e3ebbb636ee536d7a238c0fa5e21c', data=data, auth=('dieflo4711', 'Diego1639!'))
