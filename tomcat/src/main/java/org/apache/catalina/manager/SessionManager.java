package org.apache.catalina.manager;

import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.http11.Session;

public class SessionManager implements Manager {
    // static!
    private static final Map<String, Session> SESSIONS = new HashMap<>();

    public SessionManager() {
    }

    @Override
    public void add(final Session session) {
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public Session findSession(final String id) {
        return SESSIONS.get(id);
    }

    @Override
    public void remove(Session session) {
        SESSIONS.remove(session.getId());
    }

    public boolean isSessionExist(String id) {
        return SESSIONS.containsKey(id);
    }
}
