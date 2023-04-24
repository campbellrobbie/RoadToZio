$version: "2.0"

namespace com.weather.service.http

structure GetLocationRequest {
    @required @httpLabel lat: Double
    @required @httpLabel lon: Double
}

structure GetLocationResponse {
    @httpResponseCode responseCode: Integer = 200
}

structure PutLocationRequest {
    @required @httpLabel name: String
    @required @httpLabel lat: Double
    @required @httpLabel lon: Double
}

structure PutLocationResponse {
    @httpResponseCode responseCode: Integer = 200
}

structure DeleteLocationRequest {
    @required @httpLabel name: String
    @required @httpLabel lat: Double
    @required @httpLabel lon: Double
}

structure DeleteLocationResponse {
    @httpResponseCode responseCode: Integer = 200
}
