# api-test

This is a docker API service for querying addresses based on their Eircode or UK postcode.

**Eircode endpoint:**
http:\\[hostname]:[port]\api\public\eircode\{eircode}

**UK postcode endpoint:**
http:\\[hostname]:[port]\api\public\ukcode\{ukcode}


Default profile build will send requests to a 3rd party API.

Build with profile _development_ will send requests to a mock service inside the same application.

Mock data that will send a response:

**eircode**:1234

**ukcode**:NR147PZ
