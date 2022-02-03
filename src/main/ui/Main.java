package ui;

import model.event.Event;
import model.event.EventLog;

import java.util.Iterator;

public class Main {
    private static GUI gui;
    public static final EventLog eventLog = EventLog.getInstance();

    public static void main(String[] args) {
        gui = new GUI();
    }

    public static void printLog() {
        Iterator<Event> events = eventLog.iterator();

        while (events.hasNext()) {
            System.out.println(events.next().getDescription());
        }
    }
}
