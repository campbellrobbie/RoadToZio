$version: "2.0"

namespace com.weather.service.http

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
    version: "1.0",
    operations: [GetLocation, PutLocation, DeleteLocation]
}

@http(method: "GET", uri: "/location/{lat}/{lon}", code: 200)
operation GetLocation {
    input: GetLocationRequest
    output: GetLocationResponse
}

@http(method: "PUT", uri: "/location/{lat}/{lon}", code: 200)
operation PutLocation {
    input: PutLocationRequest
    output: PutLocationResponse
}

@http(method: "DELETE", uri: "/location/{lat}/{lon}", code: 200)
operation DeleteLocation {
    input: DeleteLocationRequest
    output: DeleteLocationResponse
}