function getCurrentWeather(lat, lon) {
	var apiKey = $jsapi.context().injector.weatherApiKey;
	var response = $http.get("https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_key}&units=${units}&lang=${lang}", {
            timeout: 10000,
            query:{
                API_key: apiKey,
                lat: lat,
                lon: lon,
                units: "metric",
                lang: "ru"
            }
        });
    log(toPrettyString(response));
	if (!response.isOk || !response.data) {
		return false;
	}
	var weather = {};
	weather.temp = response.data.main.temp;
	weather.description = response.data.weather[0].description;
	return weather;
    // return response;
}
