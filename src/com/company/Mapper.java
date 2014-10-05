package com.company;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by vbaum on 20/09/14.
 */
public class Mapper {

    public static JSONObject Map(TaskCreated e) {

        JSONObject obj = new JSONObject();

        try {

            obj.put("type", "TaskCreated");
            obj.put("id", e.id);
            obj.put("title", e.title);
            obj.put("time", Calendar.getInstance().getTime().getTime());
            obj.put("version", e.version);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return obj;
    }

    public static JSONObject Map(TaskRenamed e) {

        JSONObject obj = new JSONObject();

        try {

            obj.put("type", "TaskRenamed");
            obj.put("id", e.id);
            obj.put("title", e.title);
            obj.put("time", Calendar.getInstance().getTime().getTime());
            obj.put("version", e.version);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return obj;
    }

    public static JSONObject Map(TaskCompleted e) {

        JSONObject obj = new JSONObject();

        try {

            obj.put("type", "TaskCompleted");
            obj.put("id", e.id);
            obj.put("time", Calendar.getInstance().getTime().getTime());
            obj.put("version", e.version);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return obj;
    }


    public static JSONObject Map(Event e) {

        if (e.getClass() == TaskCreated.class)
            return Map((TaskCreated) e);

        if (e.getClass() == TaskRenamed.class)
            return Map((TaskRenamed) e);

        if (e.getClass() == TaskCompleted.class)
            return Map((TaskCompleted) e);

        return null;
    }

    public static Event Map(JSONObject obj) {

        String type = null;
        try {
            type = obj.get("type").toString();

            if (TaskCreated.class.toString().contains(type))
                return MapTaskCreated(obj);

            if (TaskRenamed.class.toString().contains(type))
                return MapTaskRenamed(obj);

            if (TaskCompleted.class.toString().contains(type))
                return MapTaskCompleted(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static TaskCompleted MapTaskCompleted(JSONObject obj) {

        try {

            UUID id = UUID.fromString(obj.get("id").toString());

            TaskCompleted e = new TaskCompleted(id);
            e.version = obj.getInt("version");

            return e;


        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        return null;

    }

    private static TaskRenamed MapTaskRenamed(JSONObject obj) {
        try {

            UUID id = UUID.fromString(obj.get("id").toString());
            String title = obj.getString("title");

            TaskRenamed e = new TaskRenamed(id, title);
            e.version = obj.getInt("version");

            return e;

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        return null;
    }

    private static TaskCreated MapTaskCreated(JSONObject obj) {
        try {

            UUID id = UUID.fromString(obj.get("id").toString());
            String title = obj.get("title").toString();

            TaskCreated e = new TaskCreated(id, title);
            e.version = obj.getInt("version");

            return e;


        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        return null;
    }
}
