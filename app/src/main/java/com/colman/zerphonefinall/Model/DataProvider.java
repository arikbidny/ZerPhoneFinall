package com.colman.zerphonefinall.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arikbi on 06/06/2016.
 */
public class DataProvider {
    public static List<Store> storeList = new ArrayList<>();
    public static Map<String, Store> storeMap = new HashMap<>();

    static {

        saveLocation("Jerusalem",
                "Shatner St 3, Jerusalem",
                "Sunday 8:00 AM to 10:00 PM | Monday 8:00 AM to 10:00 PM | Tuesday 8:00 AM to 10:00 PM | Wednesday 8:00 AM to 10:00 PM | Thursday 8:00 AM to 10:00 PM | Friday 8:00 AM to 10:00 PM",
                "london_photo",
                "Shatner St 3, Jerusalem");

        saveLocation("Tel-Aviv",
                "Ibn Gabirol St 60, Tel Aviv-Yafo ",
                "Sunday 8:00 AM to 10:00 PM | Monday 8:00 AM to 10:00 PM | Tuesday 8:00 AM to 10:00 PM | Wednesday 8:00 AM to 10:00 PM | Thursday 8:00 AM to 10:00 PM | Friday 8:00 AM to 10:00 PM",
                "hongkong_photo",
                "Ibn Gabirol St 60, Tel Aviv-Yafo ");

        saveLocation("Bat-Yam",
                "Ibn Gabirol St 60, Tel Aviv-Yafo ",
                "Sunday 8:00 AM to 10:00 PM | Monday 8:00 AM to 10:00 PM | Tuesday 8:00 AM to 10:00 PM | Wednesday 8:00 AM to 10:00 PM | Thursday 8:00 AM to 10:00 PM | Friday 8:00 AM to 10:00 PM",
                "sf_photo",
                "Ibn Gabirol St 60, Tel Aviv-Yafo ");

        saveLocation("Ashdod",
                "Menachem Begin Blvd 19, Ashdod",
                "Sunday 8:00 AM to 10:00 PM | Monday 8:00 AM to 10:00 PM | Tuesday 8:00 AM to 10:00 PM | Wednesday 8:00 AM to 10:00 PM | Thursday 8:00 AM to 10:00 PM | Friday 8:00 AM to 10:00 PM",
                "paris_photo",
                "Menachem Begin Blvd 19, Ashdod, 77641"
        );

    }

    private static void saveLocation(String city, String neighborhood, String description, String image, String address) {
        Store store = new Store(city, neighborhood, description, image, address);
        storeList.add(store);
        storeMap.put(city, store);
    }

}
