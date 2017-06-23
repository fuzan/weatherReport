package integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zafu.entity.CityWeather;

import java.util.function.Consumer;

import feign.gson.GsonDecoder;
import feign.hystrix.HystrixFeign;
import feign.jaxrs.JAXRSContract;
import http.client.WeatherConnector;
import rx.Observable;
import rx.Subscriber;

public class WeatherFeignConnector {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherFeignConnector.class.getName());

    private final String apiKey = "94c5c51d465a04739a5c35c062be3e14";
    private final String endpoint = "http://api.openweathermap.org";

    private Observable<CityWeather> getWeatherByCityId(String id, Consumer<CityWeather> consumer) {
        WeatherConnector weatherConnector = HystrixFeign.builder()
                .contract(new JAXRSContract())
                .decoder(new GsonDecoder())
                .target(WeatherConnector.class, endpoint);

        Observable<CityWeather> weather = weatherConnector.getWeather(id, apiKey);

        weather.subscribe(new Subscriber<CityWeather>() {
            @Override
            public void onCompleted() {
                LOG.info("Success !");
            }

            @Override
            public void onError(Throwable e) {
                LOG.error(e.getMessage(), e);
            }

            @Override
            public void onNext(CityWeather cityWeather) {
                consumer.accept(cityWeather);
            }
        });

        return weather;
    }

    private static void printWeather(CityWeather cityWeather) {
        System.out.println(cityWeather.getClouds().getAll());
        System.out.println(cityWeather.getName());
        System.out.println(cityWeather.getWeather().get(0).getMain());
        System.out.println(cityWeather.getWeather().get(0).getDescription());
    }

    public static void main(String[] args) {
        final WeatherFeignConnector weatherFeignConnector = new WeatherFeignConnector();
        weatherFeignConnector.getWeatherByCityId("5809844", WeatherFeignConnector::printWeather);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
