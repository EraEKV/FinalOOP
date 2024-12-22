package Research;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;


public class ResearchJournal implements Serializable {
    private String name;
    private Vector<Subscriber> subscribers;
    private Vector<ResearchPaper> researchPapers;
    {
        subscribers = new Vector<>();
        researchPapers = new Vector<>();
    }
    public ResearchJournal() {

    }
    public ResearchJournal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Vector<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public Vector<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public void setResearchPapers(Vector<ResearchPaper> researchPapers) {
        this.researchPapers = researchPapers;
    }

    public void subscribe(Subscriber s) {
        if (s != null && !subscribers.contains(s)) {
            subscribers.add(s);
        }
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResearchJournal that = (ResearchJournal) o;
        return Objects.equals(name, that.name) && Objects.equals(subscribers, that.subscribers) && Objects.equals(researchPapers, that.researchPapers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subscribers, researchPapers);
    }

    @Override
    public String toString() {
        return "ResearchJournal{" +
                "name='" + name + '\'' +
                ", subscribers=" + subscribers +
                ", researchPapers=" + researchPapers +
                '}';
    }
}
