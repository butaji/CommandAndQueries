package com.company;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class FileEventStore extends EventStore {

    private final String _path = "/Users/vbaum/Projects/EventStore/Storage/";

    @Override
    protected List<Event> get(UUID id) {

        ArrayList<Event> events = new ArrayList<Event>();

        String path = _path + id + "/";

        File dir = new File(path);

        if (!dir.exists())
            return events;

        String[] files = dir.list();

        if (files != null)

            for (String filePath : files) {
                try {
                    events.add(ParseEvent(path + filePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        return events;
    }

    private Event ParseEvent(String filePath) throws FileNotFoundException, JSONException {
        File file = new File(filePath);

        String output = new Scanner(file).useDelimiter("\\Z").next();

        JSONObject obj = new JSONObject(output);

        return Mapper.Map(obj);
    }

    @Override
    protected void put(UUID id, Event e) {

        java.util.Date date = new java.util.Date();
        String dirPath = _path + id + "/";
        File dir = new File(dirPath);

        if (!dir.exists())
            dir.mkdir();

        String path = dirPath + new Timestamp(date.getTime()).getTime() + ".json";

        File file = new File(path);

        if (!file.exists())
            try {

                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        try {
            FileWriter fw = new FileWriter(path);

            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(Mapper.Map(e).toString());
            writer.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
