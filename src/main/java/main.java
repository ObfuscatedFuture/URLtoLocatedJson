import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Subdivision;


import java.io.IOException;
import java.net.InetAddress;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main
{

    public static void main(String []args) throws IOException, GeoIp2Exception {
        final File CAM_LINKS = new File("C:\\Users\\Chase\\IdeaProjects\\ProjectWovenEyes\\src\\main\\java\\verifiedCamList.txt");
        final File IPS_ONLY = new File("C:\\Users\\Chase\\IdeaProjects\\ProjectWovenEyes\\src\\main\\java\\CamIP_only.txt");
        final File JSON_FINAL = new File("C:\\Users\\Chase\\IdeaProjects\\WovenEye_Organizing\\src\\JSON_Map_Values.ldjson");

        final Scanner CAM_READER = new Scanner(CAM_LINKS);
        final Scanner IPS_READER = new Scanner(IPS_ONLY);
        
        final JsonMapper mapper = new JsonMapper();

        WebServiceClient client = new WebServiceClient.Builder(${AccountID}, ${LicenseKey}.host("geolite.info").build();

        InetAddress ipAddress = null;
        // Do the lookup
        CityResponse response = null;

        String countryName = null;

        String stateName = null;

        City city = null;
        String cityName = null;

        Location location = null;
        Double latitude = null;
        Double longitude = null;
        Integer accuracy = null;
        String timezone = null;

        String userType = null;
        SequenceWriter seq = mapper.writer().withRootValueSeparator(System.lineSeparator()).writeValues(JSON_FINAL);

        final File outputFile = new File("data.ldjson");
        int skipLines = 0;
        while (skipLines < 600)
        {
            CAM_READER.nextLine();
            IPS_READER.nextLine();
            skipLines++;
        }

        while (true) {

            String link = null;
            String ip = null;
            if (CAM_READER.hasNext()) {
                link = CAM_READER.nextLine();
            }
            if (IPS_READER.hasNext()) {
                ip = IPS_READER.nextLine();
                ipAddress = InetAddress.getByName(ip);
                // Do the lookup
                response = client.city(ipAddress);
                Country country = response.getCountry();
                countryName = country.getName();/**/
                Subdivision subdivision = response.getMostSpecificSubdivision();
                stateName = subdivision.getName();/**/
                city = response.getCity();
                cityName = city.getName();/**/
                location = response.getLocation();
                latitude = location.getLatitude();/**/
                longitude = location.getLongitude();/**/
                accuracy = location.getAccuracyRadius();/**/
                timezone = location.getTimeZone();/**/
                userType = response.getTraits().getUserType();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("link", link);
            map.put("ip", ip);
            map.put("country", countryName);
            map.put("state", stateName);
            map.put("city", cityName);
            map.put("latitude", latitude);
            map.put("longitude", longitude);
            map.put("accuracy", accuracy);
            map.put("timezone", timezone);

            map.put("userType", userType); //Almost always null

            seq.write(map);
            System.out.println(ip);
        }

    }
}
