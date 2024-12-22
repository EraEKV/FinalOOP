package Research;
import Research.ResearchJournalsName;
import Research.ResearchPaper;
import Research.Subscriber;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ResearchJournal implements Serializable {
    private static final long serialVersionUID = 1L;

    private ResearchJournalsName researchJournalsName;
    private final Set<Subscriber> subscribers = new HashSet<>();
    private final Set<ResearchPaper> researchPapers = new HashSet<>();

    public ResearchJournal() {}

    public ResearchJournal(ResearchJournalsName researchJournalsName) {
        this.researchJournalsName = researchJournalsName;
    }

    public ResearchJournalsName getName() {
        return researchJournalsName;
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public Set<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public void subscribe(Subscriber subscriber) {
        if (subscriber != null) {
            subscribers.add(subscriber);
        }
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers() {
        subscribers.forEach(Subscriber::update);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchJournal that = (ResearchJournal) o;
        return Objects.equals(researchJournalsName, that.researchJournalsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(researchJournalsName);
    }

    @Override
    public String toString() {
        return "ResearchJournal{" +
                "researchJournalsName=" + researchJournalsName +
                ", subscribers=" + subscribers.size() +
                ", researchPapers=" + researchPapers.size() +
                '}';
    }
}
