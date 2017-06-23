package http.client;

import org.zafu.entity.CityWeather;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import rx.Observable;

public interface WeatherConnector {

    @GET
    @Path("/data/2.5/weather")
    Observable<CityWeather> getWeather(@QueryParam("id") String id,
                                       @QueryParam("appid") String appId);
}
